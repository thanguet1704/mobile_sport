package com.example.mobilesporta.fragment.stadium;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.MainActivity;
import com.example.mobilesporta.R;
import com.example.mobilesporta.model.StadiumModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class StadiumView extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    TextView textTitle;
    Button findWay,backStadiumView, contactStadium;
    ImageView imageStadiumView;
    TextView description, stadiumTime, stadiumAddress, costStadium;
    Query dataStadium;
    StadiumModel stadiumModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stadium_view);

        textTitle = findViewById(R.id.stadiumViewName);
        stadiumAddress = findViewById(R.id.stadiumAddress);
        imageStadiumView = findViewById(R.id.imageStadiumView);
        description = findViewById(R.id.desciption);
        stadiumTime = findViewById(R.id.stadiumTime);
        costStadium = findViewById(R.id.costStadium);
        contactStadium = findViewById(R.id.contactStadium);

        Intent i = getIntent();
        final String text = i.getStringExtra("title");
        findWay = findViewById(R.id.findWay);
        backStadiumView = findViewById(R.id.backStadiumView);
        dataStadium = FirebaseDatabase.getInstance().getReference("stadiums").orderByChild("address").equalTo(text);
        dataStadium.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    stadiumModel = snapshot.getValue(StadiumModel.class);
                }
                textTitle.setText(stadiumModel.getStadium_name());
                Picasso.get().load(stadiumModel.getImage()).into(imageStadiumView);
                String s="Mô tả:\n";
                String[] string = stadiumModel.getDescription().split("\\s\\s+");
                for(int i=0; i < string.length; i++){
                    s = s+"-"+string[i]+"\n";
                }
                description.setText(s);
                stadiumTime.setText("Thời gian: "+stadiumModel.getTime_open()+" - "+stadiumModel.getTime_close());
                stadiumAddress.setText("Địa chỉ: "+stadiumModel.getAddress());
                costStadium.setText("Giá sân: "+stadiumModel.getCost());
                contactStadium.setText("Liên hệ: "+stadiumModel.getPhone_number());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        contactStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber(stadiumModel.getPhone_number());
            }
        });

        findWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),StadiumMap.class);
                i.putExtra("title",text);
                startActivity(i);
            }
        });

        backStadiumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Home.class);
                i.putExtra("main","true");
                startActivity(i);
            }
        });
    }

    private void callPhoneNumber(String x){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else{
            String dial = "tel:"+ x;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            } else {
                Toast.makeText(this,"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}