package com.example.mobilesporta.activity.game;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.club.ClubProfile;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.example.mobilesporta.model.StadiumModel;
import com.example.mobilesporta.notifications.APIService;
import com.example.mobilesporta.notifications.Client;
import com.example.mobilesporta.notifications.Data;
import com.example.mobilesporta.notifications.Response;
import com.example.mobilesporta.notifications.Sender;
import com.example.mobilesporta.notifications.Token;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class FootballMatchInfo extends AppCompatActivity {

    TextView txtHomeClubName;
    ImageView imgHomeClubName;
    TextView txtAwayClubName;
    ImageView imgAwayClubName;
    TextView tvDate, tvStadiumName, tvAddress, tvPhoneNumber, tvDescription, tvAmountsClub;
    Button btnSelectClub, btnRequest, btnBackToMatch, btnDeleteMatch;
    String stadiumName, stadiumAddress;
    LinearLayout llHomeClub, llAwayClub;

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();

    MatchService matchService = new MatchService();
    Map<String, MatchModel> mapMatchs = matchService.getMapMatchs();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String clubHomeId, clubAwayId;
    String user_create_id, mUID;
    APIService apiService;
    boolean notify = false;
    public String match_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_match_info);

        Intent intent = getIntent();
        match_id = intent.getStringExtra("match_id");
        mUID = user.getUid();

        connectView();
        // update token
        updateToken(FirebaseInstanceId.getInstance().getToken());

        // hiển thị nút chọn đối nếu là người tạo match, tham gia nếu là người bắt kèo
        displayButtonSelectClub(match_id);
        //hiển thị thông tin kèo
        showMatchInfo(match_id);

        backToMatch();
//      create api service
        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);

        deleteMatch(match_id);
        // click nút chọn đối
        clickSelectClub();
        // click nút tham gia
        clickRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Current_USERID", mUID);
        editor.apply();
    }

    private void connectView() {
        txtHomeClubName = findViewById(R.id.txtMatchInfo_HomeClubName);
        txtAwayClubName = findViewById(R.id.tvMatchInfoAct_AwayClubName);
        imgHomeClubName = findViewById(R.id.imgMatchInfoAct_HomeClubAvt);
        imgAwayClubName = findViewById(R.id.imgMatchInfoAct_AwayClubAvt);
        tvDate = findViewById(R.id.tv_match_info_date);
        tvStadiumName = findViewById(R.id.txtMatchInfoAct_Stadium);
        tvAddress = findViewById(R.id.txtMatchInfo_address);
        tvPhoneNumber = findViewById(R.id.txtMatchInfoAct_PhoneNumber);
        tvDescription = findViewById(R.id.txtMatchInfoAct_Description);
        tvAmountsClub = findViewById(R.id.tv_amounts_club);
        btnSelectClub = findViewById(R.id.btnMatchInfoAct_ApplyMatch);
        btnRequest = findViewById(R.id.btn_request);
        btnBackToMatch = findViewById(R.id.btn_match_info_back);
        btnDeleteMatch = findViewById(R.id.btn_delete_match);
        llHomeClub = findViewById(R.id.ll_home_club);
        llAwayClub = findViewById(R.id.ll_away_club);
    }

    private ClubModel getClubById(String clubId, Map<String, ClubModel> map) {
        ClubModel clubModel = new ClubModel();
        for (Map.Entry<String, ClubModel> entry : map.entrySet()) {
            if(entry.getKey().equals(clubId)) {
                clubModel = entry.getValue();
            }
        }
        return  clubModel;
    }

    private void showMatchInfo(String match_id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("matchs");
        databaseReference.orderByKey().equalTo(match_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel matchModel = snapshot.getValue(MatchModel.class);
                        tvDate.setText(matchModel.getDate() + " từ " + matchModel.getTime());
                        renderInfoStadium(matchModel.getStadium_id());
                        tvPhoneNumber.setText(matchModel.getPhone_number());
                        tvDescription.setText(matchModel.getDescription());
                        tvAmountsClub.setText("0" + " đội muốn tham gia trận đấu này");
                        clubHomeId = matchModel.getClub_home_id();
                        clubAwayId = matchModel.getClub_away_id();
                    }
                    // hiển thị thông tin home club
                    renderClubHome(clubHomeId);
                    // hiển thị thông tin away club
                    renderClubAway(clubAwayId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void backToMatch(){
        btnBackToMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballMatchInfo.this, Home.class);
                intent.putExtra("add_match", "true");
                startActivity(intent);
            }
        });
    }

    private void deleteMatch(final String matchId){

        btnDeleteMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchService matchService = new MatchService();
                matchService.deleteMatch(matchId);
                Intent intent = new Intent(FootballMatchInfo.this, Home.class);
                intent.putExtra("add_match", "true");
                startActivity(intent);
            }
        });
    }

    private void displayButtonSelectClub(String match_id){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("matchs");
        database.orderByKey().equalTo(match_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        MatchModel matchModel = snapshot.getValue(MatchModel.class);
                        if (user.getUid().equals(matchModel.getUser_created_id())){
                            btnSelectClub.setVisibility(View.VISIBLE);
                            btnRequest.setVisibility(View.GONE);
                        }else{
                            btnSelectClub.setVisibility(View.GONE);
                            btnRequest.setVisibility(View.VISIBLE);
                            btnDeleteMatch.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void renderClubHome(final String clubHomeId){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("clubs");
        database.orderByKey().equalTo(clubHomeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        ClubModel clubModel = snapshot.getValue(ClubModel.class);
                        Picasso.get().load(clubModel.getImage()).into(imgHomeClubName);
                        txtHomeClubName.setText(clubModel.getClub_name());
                        user_create_id = clubModel.getUser_created_id();
                    }
                    // click hiển thị thông tin club home
                    llHomeClub.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(FootballMatchInfo.this, ClubProfile.class);
                            intent.putExtra("club_id", clubHomeId);
                            intent.putExtra("user_id", user_create_id);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void renderClubAway(final String clubAwayId){
        if (clubAwayId.equals("")){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/mobilesporta-5bb33.appspot.com/o/image_club%2Fd63e35b31954db7a83f772702a348eb6.png?alt=media&token=964db416-1304-484c-88d0-c05aee101a1a").into(imgAwayClubName);
        }else{
            DatabaseReference database = FirebaseDatabase.getInstance().getReference("clubs");
            database.orderByKey().equalTo(clubAwayId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            ClubModel clubModel = snapshot.getValue(ClubModel.class);
                            Picasso.get().load(clubModel.getImage()).into(imgAwayClubName);
                            txtAwayClubName.setText(clubModel.getClub_name());
                            user_create_id = clubModel.getUser_created_id();
                        }
                        // click hiển thị thông tin club home
                        llAwayClub.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(FootballMatchInfo.this, ClubProfile.class);
                                intent.putExtra("club_id", clubAwayId);
                                intent.putExtra("user_id", user_create_id);
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void renderInfoStadium(String stadium_id){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("stadiums");
        database.orderByKey().equalTo(stadium_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        StadiumModel stadiumModel = snapshot.getValue(StadiumModel.class);
                        tvStadiumName.setText(stadiumModel.getStadium_name());
                        tvAddress.setText(stadiumModel.getAddress());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void clickSelectClub(){
        btnSelectClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_select_club, null);
                final Dialog dialog = new Dialog(FootballMatchInfo.this);
                dialog.setContentView(view);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    private void clickRequest(){
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                DatabaseReference database = FirebaseDatabase.getInstance().getReference("matchs");
                database.orderByKey().equalTo(match_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            ArrayList<MatchModel> listMatch = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                MatchModel match = snapshot.getValue(MatchModel.class);
                                listMatch.add(match);
                            }

                            if (notify){
                                sendNotification(listMatch.get(0).getUser_created_id());
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                notify = false;

            }
        });
    }

    private void sendNotification(final String hisUid) {
        DatabaseReference allToken = FirebaseDatabase.getInstance().getReference("tokens");
        Query query = allToken.orderByKey().equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Token token = snapshot.getValue(Token.class);
                        String body = "Một câu lạc bộ mới tham gia trận đấu của bạn. Bắt đối ngay kẻo lỡ!";
                        Data data = new Data(user.getUid(),body , "Có đội muốn đá với đội của bạn", hisUid, match_id, R.mipmap.ic_launcher_foreground);
                        Sender sender = new Sender(data, token.getToken());
                        apiService.sendNotification(sender)
                                .enqueue(new Callback<Response>() {
                                    @Override
                                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                        Toast.makeText(FootballMatchInfo.this, "success", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Response> call, Throwable t) {

                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void updateToken(String token){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tokens");
        Token mToken = new Token(token);
        ref.child(user.getUid()).setValue(mToken);
    }
}
