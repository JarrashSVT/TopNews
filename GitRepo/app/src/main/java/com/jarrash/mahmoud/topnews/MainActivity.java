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
    public void processFinish(Headline[] headlines)
    {
        for(int i = 0 ; i < 10 ; i++)
        {
            Log.d(LOG_TAG, "processFinish: " + headlines[i].getDescription());
        }

        titles =  getTitles(headlines);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,titles);
        headlinesListView = (ListView) findViewById(R.id.headlinesList);

        headlinesListView.setAdapter(arrayAdapter);

        sourcesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                sources = getResources().getStringArray(R.array.sources);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                Log.i(LOG_TAG, "processFinish -> onItemSelected: " + sources[position].toLowerCase());
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



    }


    public String[] getTitles(Headline[] headlines)
    {
        Log.d(LOG_TAG, "getTitles ");
        String[] titles = new String[10];
        for(int i = 0 ; i < 10 ; i++)
        {
            Log.d(LOG_TAG, "Title " + i +": "+ headlines[i].getTitle());
            titles[i] = headlines[i].getTitle();
        }

        return titles;
    }
}
