package com.jarrash.mahmoud.topnews;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mahmoud on 11/18/17.
 */

public class NetworkUtils
{
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String API_URL = "https://newsapi.org/v2/top-headlines?";
    private static final String SOURCE = "sources";
    //private static final String SORT_BY = "sortBy";
    private static final String KEY = "apiKey";


    static String getHeadlines(String source)
    {
        String headlineJSON = null;
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        StringBuffer buffer = new StringBuffer();
        String line;

        try
        {
            Uri apiUri = Uri.parse(API_URL).buildUpon()
                    .appendQueryParameter(SOURCE, source)
                    .appendQueryParameter(KEY, "9de224d271f3467280686c9a54791815")
                    .build();

            Log.d(LOG_TAG, "getHeadlines: " + apiUri.toString());
            URL requestUrl = new URL(apiUri.toString());

            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // read the response using inputStream and StringBuffer

            InputStream inputStream = urlConnection.getInputStream();

            if(inputStream == null)
            {
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = bufferedReader.readLine()) != null)
            {
                buffer.append(line + "\n");

            }

            if(buffer.length() == 0)
            {
                return null;
            }

            headlineJSON = buffer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }

            if(bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();

                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }

        }
        Log.d(LOG_TAG, headlineJSON);
        return headlineJSON;
    }
}
