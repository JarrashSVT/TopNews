package com.jarrash.mahmoud.topnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private static final String  LOG_TAG = MainActivity.class.getSimpleName();
    private Spinner sourcesSpinner;
    private String[] sources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sources = getResources().getStringArray(R.array.sources);

        sourcesSpinner = (Spinner) findViewById(R.id.sourceSpinner);
        int index = sourcesSpinner.getSelectedItemPosition();
        String source = sources[index].toLowerCase();
        Headline[] headlines = null;
        
        Headlines h = new Headlines();
        h.delegate = this;
        h.execute(source + "-news");

       // ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, , headlines);
    }

    @Override
    public void processFinish(Headline[] headlines)
    {
        for(int i = 0 ; i < 5 ; i++)
        {
            Log.d(LOG_TAG, headlines[i].getDescription());
        }


    }
}
