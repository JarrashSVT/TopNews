package com.jarrash.mahmoud.topnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private static final String  LOG_TAG = MainActivity.class.getSimpleName();
    //Intent variables
    public static final String TITLE = "TITLE";
    public static final String AUTHOR = "AUTHOR";
    public static final String DESC = "DESC";
    public static final String URL = "URL";
    public static final String URLTOIMAGE = "URLTOIMAGE";
    public static final String PUBLISHEDAT = "PUBLISHEDAT";

    private Spinner sourcesSpinner;
    ListView headlinesListView;
    private String[] sources;
    private String[] titles;
    private String source;
    private Headlines h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        source = intent.getStringExtra("source");
        Log.i(LOG_TAG, "onCreate -> intent-> source=" + source);
        sourcesSpinner = (Spinner) findViewById(R.id.sourceSpinner);
        if(source == null) {



            int index = sourcesSpinner.getSelectedItemPosition();
            Log.i(LOG_TAG, "onCreate -> index=" + index);
            //source = sources[index].toLowerCase();
            source = String.valueOf(sourcesSpinner.getSelectedItem());
            Log.i(LOG_TAG, "onCreate -> source=" + source);
            // final Headline[] headlines = null;
        }
        else
        {
            sourcesSpinner.setSelection(intent.getIntExtra("index", -1));
        }


        h = new Headlines();
        h.delegate = this;
        h.execute(source + "-news");


    }

    @Override
    public void processFinish(final Headline[] headlines)
    {
        int i = 0;
       /* for(i = 0 ; i < 10 ; i++)
        {
            if(headlines[i] != null)
            {
                Log.d(LOG_TAG, "FUNCTION:processFinish: Headline (" + i + ")"+ headlines[i].getDescription());
            }
        }*/

        while (headlines[i] != null && i < headlines.length)
        {
            Log.d(LOG_TAG, "FUNCTION:processFinish: Headline (" + i + "): "+ headlines[i].getDescription());
            i++;
        }

        titles =  getTitles(headlines, i);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,titles);
        headlinesListView = (ListView) findViewById(R.id.headlinesList);

        headlinesListView.setAdapter(arrayAdapter);

        sourcesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                sources = getResources().getStringArray(R.array.sources);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                Log.i(LOG_TAG, "FUNCTION:processFinish -> onItemSelected: " + sources[position].toLowerCase());
                intent.putExtra("source",sources[position].toLowerCase());
                intent.putExtra("index", position);
                startActivity(intent);
                finish();
                //recreate();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        headlinesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), HeadlineActivity.class);
                intent.putExtra("position", position);
                intent.putExtra(TITLE, headlines[position].getTitle());
                intent.putExtra(AUTHOR, headlines[position].getAuthor());
                intent.putExtra(DESC, headlines[position].getDescription());
                intent.putExtra(URL, headlines[position].getUrl());
                intent.putExtra(URLTOIMAGE, headlines[position].getUrlToImage());
                intent.putExtra(PUBLISHEDAT, headlines[position].getPublishedAt());
                startActivity(intent);
            }
        });



    }


    public String[] getTitles(Headline[] headlines, int numOfHeadlines)
    {
        Log.d(LOG_TAG, "getTitles ");
        Log.d(LOG_TAG, "FUNCTION:getTitles -> numOfHeadlines = " + numOfHeadlines);
        String[] titles = new String[numOfHeadlines];
        for(int i = 0 ; i < numOfHeadlines ; i++)
        {

            Log.d(LOG_TAG, "FUNCTION:getTitles -> Title " + i + ": " + headlines[i].getTitle());
            titles[i] = headlines[i].getTitle();

        }

        return titles;
    }
}
