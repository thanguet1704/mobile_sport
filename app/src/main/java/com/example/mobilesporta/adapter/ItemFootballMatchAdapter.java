package com.example.mobilesporta.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.model.MatchModel;

import java.util.List;

public class ItemFootballMatchAdapter extends BaseAdapter {

    private Activity activity;
    private List<MatchModel> FootballMatchList;

    public ItemFootballMatchAdapter(Activity activity, List<MatchModel> FootballMatchList) {
        this.activity = activity;
        this.FootballMatchList = FootballMatchList;
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
        ImageView imgHomeClub = convertView.findViewById(R.id.imgItemFootballMatch_HomeClubAvt);
        TextView txtHomeClubName = convertView.findViewById(R.id.txtItemFootballMatch_HomeClubName);

        ImageView imgAwayClub = convertView.findViewById(R.id.imgItemFootballMatch_AwayClubAvt);
        TextView txtAwayClubName = convertView.findViewById(R.id.txtItemFootballMatch_AwayClubName);

        TextView txtMatchTime = convertView.findViewById(R.id.txtItemFootballMatch_Time);
        TextView txtMatchDate = convertView.findViewById(R.id.txtItemFootballMatch_Date);
        TextView txtStadium = convertView.findViewById(R.id.txtItemFootballMatch_Stadium);

        MatchModel match = FootballMatchList.get(position);

        txtHomeClubName.setText(match.getClub_away_id());
        txtAwayClubName.setText(match.getClub_away_id());
        txtMatchTime.setText(match.getTime());
        txtMatchDate.setText(match.getDate());
        txtStadium.setText(match.getStadium_id());


        return convertView;
    }
}
