package com.example.mobilesporta.fragment.stadium;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesporta.Home;
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
    TextView textTitle;
    Button findWay,backStadiumView;
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
}