package com.example.mobilesporta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.StadiumModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemStadiumForMatchAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<StadiumModel> listStadium;

    public ItemStadiumForMatchAdapter(Context context, int layout, List<StadiumModel> listStadium) {
        this.context = context;
        this.layout = layout;
        this.listStadium = listStadium;
    }

    @Override
    public int getCount() {
        return listStadium.size();
    }

    @Override
    public Object getItem(int position) {
        return listStadium.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        ImageView imageStadium = convertView.findViewById(R.id.img_stadium_for_match);
        TextView nameStadium = convertView.findViewById(R.id.tv_name_stadium_for_match);
        TextView addressStadium = convertView.findViewById(R.id.tv_name_stadium_for_match);
        TextView openingTime = convertView.findViewById(R.id.tv_name_stadium_for_match);

        StadiumModel stadium = listStadium.get(position);

        Picasso.get().load(stadium.getImage()).into(imageStadium);
        nameStadium.setText(stadium.getStadium_name());
        addressStadium.setText(stadium.getAddress());
        openingTime.setText(stadium.getTime_open());
        return convertView;
    }
}
