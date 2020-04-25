package com.example.mobilesporta.menu;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilesporta.R;

public class News extends AppCompatActivity {

    int[] IMAGES={R.drawable.news,R.drawable.stadium,R.drawable.game,R.drawable.club};
    String [] DESCRIPTIONS={"new_2","new_2","new_2","new_2"};
    private final String TAG= getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ListView listView=(ListView)findViewById(R.id.listView);
        NewsAdapter newsAdapter= new NewsAdapter();
        listView.setAdapter(newsAdapter);
    }
    class NewsAdapter extends BaseAdapter{

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
