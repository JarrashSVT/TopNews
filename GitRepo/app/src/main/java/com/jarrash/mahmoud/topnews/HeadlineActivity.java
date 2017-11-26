package com.jarrash.mahmoud.topnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class HeadlineActivity extends AppCompatActivity {
    private static final String  LOG_TAG = HeadlineActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline);

        Intent i = getIntent();
        int position = i.getIntExtra("position",-1);

        String title = i.getStringExtra(MainActivity.TITLE);
        String author = i.getStringExtra(MainActivity.AUTHOR);
        String description = i.getStringExtra(MainActivity.DESC);
        String url = i.getStringExtra(MainActivity.URL);
        String urlToImage = i.getStringExtra(MainActivity.URLTOIMAGE);
        String publishedAt = i.getStringExtra(MainActivity.PUBLISHEDAT);

        Log.d(LOG_TAG, "FUNCTION:onCreate ->  position = " + position);
        Log.d(LOG_TAG, "FUNCTION:onCreate ->  title = " + title);
        Log.d(LOG_TAG, "FUNCTION:onCreate ->  author = " + author);
        Log.d(LOG_TAG, "FUNCTION:onCreate ->  description = " + description);
        Log.d(LOG_TAG, "FUNCTION:onCreate ->  url = " + url);
        Log.d(LOG_TAG, "FUNCTION:onCreate ->  urlToImage = " + urlToImage);
        Log.d(LOG_TAG, "FUNCTION:onCreate ->  publishedAt = " + publishedAt);


        TextView titleTxt = (TextView) findViewById(R.id.titleTxt);
        TextView authorTxt = (TextView) findViewById(R.id.authorTxt);
        TextView descriptionTxt = (TextView) findViewById(R.id.descriptionTxt);
        TextView urlTxt = (TextView) findViewById(R.id.urlTxt);
        ImageView img = (ImageView) findViewById(R.id.imageView2);
        TextView publishedAtTxt = (TextView) findViewById(R.id.publishedAtTxt);

        titleTxt.setText(title);
        authorTxt.setText("By " + author);
        descriptionTxt.setText(description);
        urlTxt.setText(url);
        publishedAtTxt.setText("Published At: "  + publishedAt);

        if(urlToImage.equals("null")) {
            Log.i(LOG_TAG, "FUNCTION: onCreate -> urlToImage is  null");
            Log.i(LOG_TAG, "FUNCTION: onCreate -> loading the default image");
            Glide.with(this).load( "https://i2.wp.com/huffington-global.com/wp-content/uploads/2017/10/97176213_breaking_news_bigger-45.jpg").into(img);
           // img.setImageResource(R.drawable.news);
        }
        else
        {
            Log.i(LOG_TAG, "FUNCTION: onCreate -> urlToImage is NOT null");
            Glide.with(this).load(urlToImage).into(img);
        }
    }
}
