package com.example.mobilesporta.fragment.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;

public class NewsFragment extends Fragment {
    int[] IMAGES={R.drawable.news,R.drawable.stadium,R.drawable.game,R.drawable.club};
    String [] DESCRIPTIONS={"new_2","new_2","new_2","new_2"};
    private final String TAG= getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ListView listView=(ListView)view.findViewById(R.id.listView);
        NewsAdapter newsAdapter= new NewsAdapter();
        listView.setAdapter(newsAdapter);
        return view;
    }

    class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.news_listview,null);
            ImageView imageView_img =(ImageView)view.findViewById(R.id.imageView_img);
            TextView textView_description =(TextView)view.findViewById(R.id.textView_description);
            imageView_img.setImageResource(IMAGES[i]);
            textView_description.setText(DESCRIPTIONS[i]);
            return view;
        }
    }

}
