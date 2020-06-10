package com.example.mobilesporta.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mobilesporta.R;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.example.mobilesporta.model.StadiumModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class    ItemFootballMatchAdapter extends BaseAdapter {

    private Activity activity;
    private List<MatchModel> FootballMatchList;
    Map<String, ClubModel> mapClubs = new HashMap<>();
    TextView txtStadium, txtMatchDate, txtMatchTime, txtAwayClubName, txtHomeClubName, txtWait;
    ImageView imgHomeClub, imgAwayClub;

    public ItemFootballMatchAdapter(Activity activity, List<MatchModel> footballMatchList, Map<String, ClubModel> mapClubs) {
        this.activity = activity;
        FootballMatchList = footballMatchList;
        this.mapClubs = mapClubs;
    }

    public ClubModel getClubById(String clubId) {
        ClubModel clubModel = new ClubModel();
        for (Map.Entry<String, ClubModel> entry : mapClubs.entrySet()) {
            if(entry.getKey().equals(clubId)) {
                clubModel = entry.getValue();
            }
        }
        return  clubModel;
    }

    @Override
    public int getCount() {
        return FootballMatchList.size();
    }

    @Override
    public Object getItem(int position) {
        return FootballMatchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // tung itemt
        // goi layoutInflater ra để bắt đầu ánh xạ view và data
        LayoutInflater inflater = activity.getLayoutInflater();

        // Đổ dữ liệu vào convertView, chính là những gì nằm trong item_name/xml
        convertView = inflater.inflate(R.layout.item_football_match, null);

        // đặt chữ cho từng view trong danh sách
        imgHomeClub = convertView.findViewById(R.id.imgItemFootballMatch_HomeClubAvt);
        txtHomeClubName = convertView.findViewById(R.id.txtItemFootballMatch_HomeClubName);

        txtWait = convertView.findViewById(R.id.tv_wait);
        imgAwayClub = convertView.findViewById(R.id.imgItemFootballMatch_AwayClubAvt);
        txtAwayClubName = convertView.findViewById(R.id.txtItemFootballMatch_AwayClubName);
        txtMatchTime = convertView.findViewById(R.id.txtItemFootballMatch_Time);
        txtMatchDate = convertView.findViewById(R.id.txtItemFootballMatch_Date);
        txtStadium = convertView.findViewById(R.id.txtItemFootballMatch_Stadium);

        MatchModel match = FootballMatchList.get(position);

        txtHomeClubName.setText(match.getClub_away_id());
        txtAwayClubName.setText(match.getClub_away_id());
        txtMatchTime.setText(match.getTime());
        txtMatchDate.setText(match.getDate());

        if (match.getStatus().equals("N")){
            txtWait.setText("Đang chờ đối thủ");
        }else if (match.getStatus().equals("C")){
            txtWait.setText("Chờ xác nhận");
        }else{
            txtWait.setText("Đã kết thúc");
        }

        renderNameStdium(match.getStadium_id());

        ClubModel homeClub = getClubById(match.getClub_home_id());
        txtHomeClubName.setText(homeClub.getClub_name());
        Picasso.get().load(homeClub.getImage()).into(imgHomeClub);


        if( !match.getClub_away_id().equals("")) {
            ClubModel awayClub = getClubById(match.getClub_away_id());
            txtAwayClubName.setText(awayClub.getClub_name());
            Picasso.get().load(awayClub.getImage()).into(imgAwayClub);
        }
        else {
            txtAwayClubName.setText("");
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/mobilesporta-5bb33.appspot.com/o/image_club%2Fd63e35b31954db7a83f772702a348eb6.png?alt=media&token=964db416-1304-484c-88d0-c05aee101a1a").into(imgAwayClub);
        }


        return convertView;
    }

    private void renderNameStdium(String stadium_id) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("stadiums");
        database.orderByKey().equalTo(stadium_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        StadiumModel stadiumModel = snapshot.getValue(StadiumModel.class);
                        txtStadium.setText(stadiumModel.getStadium_name());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
