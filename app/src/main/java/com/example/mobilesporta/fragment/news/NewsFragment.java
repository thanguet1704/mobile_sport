package com.example.mobilesporta.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.news.NewsInfo;
import com.example.mobilesporta.adapter.ItemNewsAdapter;
import com.example.mobilesporta.model.NewsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    ListView listView;
    SearchView edtSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_news, container, false);

        listView = view.findViewById(R.id.lv_item_news);
        edtSearch = view.findViewById(R.id.search_news);

        showListNews();

        return view;
    }

    private void showListNews() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("news");
        databaseReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    final ArrayList<NewsModel> listNews = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        NewsModel newsModel = snapshot.getValue(NewsModel.class);
                        listNews.add(newsModel);
                    }

                    ItemNewsAdapter itemNewsAdapter = new ItemNewsAdapter(getContext(), R.layout.news_listview, listNews);
                    listView.setAdapter(itemNewsAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(), NewsInfo.class);
                            intent.putExtra("url", listNews.get(position).getUrl());
                            startActivity(intent);
                        }
                    });

                    itemNewsAdapter.notifyDataSetChanged();

                    edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            final String key = newText.toLowerCase();

                            final ArrayList<NewsModel> listNewsSearch = new ArrayList<>();
                            for (int i = 0 ; i < listNews.size() ; i++){
                                if (listNews.get(i).getTitle().toLowerCase().contains(key)){
                                    listNewsSearch.add(listNews.get(i));
                                }
                            }
                            ItemNewsAdapter itemNewsAdapter = new ItemNewsAdapter(getContext(), R.layout.news_listview, listNewsSearch);
                            listView.setAdapter(itemNewsAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getContext(), NewsInfo.class);
                                    intent.putExtra("url", listNewsSearch.get(position).getUrl());
                                    startActivity(intent);
                                }
                            });

                            itemNewsAdapter.notifyDataSetChanged();
                            return false;
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