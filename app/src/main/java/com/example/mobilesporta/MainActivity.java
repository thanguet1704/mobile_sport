package com.example.mobilesporta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 1;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Button fakeLoginButton, loginLocalButton, dangky, fakeSigninButton;
    private EditText edtEmail, edtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().signOut();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findId();
        mAuth = FirebaseAuth.getInstance();

        clickDangky();

        //login local
        loginLocalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLocal();
            }
        });

        fakeLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        //login facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        loginFacebook();

        //sign in google
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signinGoogle();
    }

    private void findId(){
        loginButton = findViewById(R.id.login_button); // button facebook login
        signInButton = findViewById(R.id.sign_in_button); // dang nhap google
        loginLocalButton = findViewById(R.id.loginLocal); // dn tai khoan local
        dangky = findViewById(R.id.register); // nut dang ky
        fakeLoginButton = findViewById(R.id.fake_login_button); // nut gia login facebook
        fakeSigninButton = findViewById(R.id.fake_signin_button);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPass);
    }

    private void loginFacebook(){
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // lay thong tin nguoi dung
//                Picasso.get().load(imageURL).into(logo);

//                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        try {
//                            String id = object.getString("id");
//                            String name = object.getString("name");
//                            String imageURL = "http://graph.facebook.com/"+ id +"/picture?type=large";
//                            Intent intent = new Intent(MainActivity.this, Home.class);
//                            startActivity(intent);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id, name");
//                graphRequest.setParameters(parameters);
//                graphRequest.executeAsync();

                handleFacebookAccessToken(loginResult.getAccessToken());

            }
            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void signinGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        fakeSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fake_signin_button:
                        signIn();
                        break;
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult (signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.d("Error", e.toString());
            }
        }

    }

    private void clickDangky(){
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void loginLocal(){

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        if (email.length() == 0 || password.length() == 0){
            Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else{
            if (password.length() < 6){
                Toast.makeText(MainActivity.this, "Mật khẩu phải lớn hơn 6 ký tự", Toast.LENGTH_SHORT).show();
            }else{
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("log", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                        } else {

                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        //lỗi do chưa connect firebase
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                        } else {
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null){
            String personName = account.getDisplayName();
            String email = account.getEmail();
            String id = account.getId();
            Toast.makeText(MainActivity.this, "name: " + personName + "email: " + email, Toast.LENGTH_SHORT).show();
        }
    }
}
