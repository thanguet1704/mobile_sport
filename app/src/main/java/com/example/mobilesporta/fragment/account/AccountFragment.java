package com.example.mobilesporta.fragment.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.MainActivity;
import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.account.EditAccount;
import com.example.mobilesporta.activity.account.PravicyPolicy;
import com.example.mobilesporta.activity.account.Recommend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {

    Button logout, editAccount, recommend, security, btnAddImage;
    TextView username;
    ImageView avatar;
    String uriAvatar, strName = "NoName";
    final int IMAGE_PICK_CODE = 1000, RESULT_OK = -1;
    Uri imageURI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);;

        avatar = view.findViewById(R.id.avatar);
        username = view.findViewById(R.id.text_email);
        logout = view.findViewById(R.id.logout);
        editAccount = view.findViewById(R.id.edit_account);
        recommend = view.findViewById(R.id.gt);
        security = view.findViewById(R.id.security_policy);
        btnAddImage = view.findViewById(R.id.add_profile_image);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditAccount.class);
                startActivity(intent);
            }
        });

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Recommend.class);
                startActivity(intent);
            }
        });

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PravicyPolicy.class);
                startActivity(intent);
            }
        });

        return view;

    }

    private void updateUI(FirebaseUser currentUser){
        if (currentUser.getDisplayName().length() > 0)
            strName = currentUser.getDisplayName();

        username.setText(strName);

        if (currentUser.getPhotoUrl() == null){
            uriAvatar = "https://firebasestorage.googleapis.com/v0/b/mobile-1054b.appspot.com/o/account.png?alt=media&token=2e8acf2d-ab0d-4b15-bfc7-b007e2ca991c";
        }else{
            uriAvatar = currentUser.getPhotoUrl().toString();
        }
        Picasso.get().load(uriAvatar).into(avatar);
    }

    private void addImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            updateAvatar(data.getData());
        }
    }

    private void updateAvatar(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Log.d("image", user.getPhotoUrl().toString());

//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setPhotoUri(uri)
//                .build();
//
//        user.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("success", "User profile updated.");
//                        }
//                    }
//                });
    }
}
