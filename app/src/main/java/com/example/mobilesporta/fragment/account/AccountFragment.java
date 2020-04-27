package com.example.mobilesporta.fragment.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.MainActivity;
import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.account.EditAccount;
import com.example.mobilesporta.activity.account.PravicyPolicy;
import com.example.mobilesporta.activity.account.Recommend;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    FirebaseAuth mAuth;
    Button logout, editAccount, recommend, security;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);;

        mAuth = FirebaseAuth.getInstance();
        logout = view.findViewById(R.id.logout);
        editAccount = view.findViewById(R.id.edit_account);
        recommend = view.findViewById(R.id.gt);
        security = view.findViewById(R.id.security_policy);

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

}
