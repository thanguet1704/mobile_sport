package com.example.mobilesporta.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.mobilesporta.R;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.model.ClubCommentModel;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ItemCommentClubAdapter extends BaseAdapter {
    private Activity activity;
    private List<ClubCommentModel> listClubComment;

    public ItemCommentClubAdapter(Activity activity, List<ClubCommentModel> listClubComment) {
        this.activity = activity;
        this.listClubComment = listClubComment;
    }

    @Override
    public int getCount() {
        return listClubComment.size();
    }

    @Override
    public Object getItem(int position) {
        return listClubComment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_comment_club, null);

        TextView tvNameUser = convertView.findViewById(R.id.tv_name_user);
        TextView tvTime = convertView.findViewById(R.id.tv_time);
        TextView tvComment = convertView.findViewById(R.id.tv_comment_club);
        ImageView imgAvatar = convertView.findViewById(R.id.img_avatar);

        int size = listClubComment.size();
        tvTime.setText(listClubComment.get(size - 1 - position).getDate());
        tvComment.setText(listClubComment.get(size - 1 - position).getContent());
        tvNameUser.setText(listClubComment.get(size - 1 - position).getUser_name());
        Picasso.get().load(listClubComment.get(size - 1 - position).getAvatar()).into(imgAvatar);

        return convertView;
    }
}
