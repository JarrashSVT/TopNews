package com.jarrash.mahmoud.topnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final String  LOG_TAG = MainActivity.class.getSimpleName();
    private Spinner sourcesSpinner;
    private Spinner sortBySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourcesSpinner = (Spinner) findViewById(R.id.sourceSpinner);
        sortBySpinner = (Spinner) findViewById(R.id.sortBySpinner);

        new FetchHeadline().execute("bbc-news", "top");
        //
    }
}
