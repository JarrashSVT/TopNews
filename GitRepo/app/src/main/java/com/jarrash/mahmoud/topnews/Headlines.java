package com.jarrash.mahmoud.topnews;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mahmoud on 11/18/17.
 */

public class Headlines extends AsyncTask<String ,Void, String>
{
    private static final String  LOG_TAG = Headlines.class.getSimpleName();
    private Headline[] headlines;
    public AsyncResponse delegate = null;

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        headlines =  getHeadlinesArr(s);
        delegate.processFinish(headlines);

    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getHeadlines(strings[0]);
    }

    public Headline[] getHeadlinesArr(String json)
    {
        Headline[] headlines = new Headline[0];

        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            headlines = new Headline[jsonArray.length()+1];

            for(int i = 0 ; i < jsonArray.length() ; i++)
            {
                JSONObject articleJSON = jsonArray.getJSONObject(i);
                String author = null;
                String title = null;
                String description;
                String url;
                String urlToImage;
                String publishedAt;

                Headline headline = new Headline();
                try
                {
                    author = articleJSON.getString("author");
                    title = articleJSON.getString("title");
                    description = articleJSON.getString("description");
                    url = articleJSON.getString("url");
                    urlToImage = articleJSON.getString("urlToImage");
                    publishedAt = articleJSON.getString("publishedAt");

                    headline.setAuthor(author);
                    headline.setTitle(title);
                    headline.setDescription(description);
                    headline.setUrl(url);
                    headline.setUrlToImage(urlToImage);
                    headline.setPublishedAt(publishedAt);

                    Log.d(LOG_TAG, "------>AUTHOR: " + headline.getAuthor());
                    Log.d(LOG_TAG, "------>TITLE: " + headline.getTitle());

                    // add the fetched headline to the headlines array
                    headlines[i] = headline;
                }
                catch (JSONException je)
                {
                    je.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return headlines;
    }
}
