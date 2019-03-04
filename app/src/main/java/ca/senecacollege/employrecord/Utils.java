package ca.senecacollege.employrecord;

import android.text.TextUtils;
import android.util.Log;
//import com.google.android.gms.plus.PlusShare;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<String> fetchJobData(String requestUrl) {
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(createUrl(requestUrl));
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return extractFeatureFromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
            return null;
        }
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the job JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                output.append(line);
            }
        }
        return output.toString();
    }

    private static List<String> extractFeatureFromJson(String jobJSON) {
        JSONException e;
        if (TextUtils.isEmpty(jobJSON)) {
            return null;
        }
        ArrayList jobList = new ArrayList();
        try {
            /*JSONObject baseJsonResponse = new JSONObject(jobJSON);
            JSONArray jobArray = baseJsonResponse.getJSONArray("features");*/
            JSONArray jobArray = new JSONArray(jobJSON);
            int i = 0;
            int i2 = 0;
            while (i2 < jobArray.length()) {
                //JSONObject currentJob = jobArray.getJSONObject(i2);
                //JSONObject properties = currentJob.getJSONObject("properties");
                JSONObject currentJob = jobArray.getJSONObject(i2);
                String jobTitle = currentJob.getString("title");
                String jobFullTime = currentJob.getString("type");
                /*String jobCreationDate = properties.getString("created_at");
                String jobURL = properties.getString("url");
                JSONArray coord = currentJob.getJSONObject("geometry").getJSONArray("coordinates");
                String lat = coord.getString(i);
                String lng = coord.getString(1);*/
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(jobTitle);
                JSONArray jobArray2 = jobArray;
                stringBuilder.append("@@");
                stringBuilder.append(jobFullTime);
                /*stringBuilder.append("@@");
                stringBuilder.append(jobFullTime);
                stringBuilder.append("@@");
                stringBuilder.append(lat);
                stringBuilder.append("@@");
                stringBuilder.append(lng);
                stringBuilder.append("@@");
                stringBuilder.append(quakeMag);*/
                jobList.add(stringBuilder.toString());
                i2++;
                jobArray = jobArray2;
                i = 0;
            }
            return jobList;
        } catch (JSONException e2) {
            e = e2;
            Log.e(LOG_TAG, "Problem parsing the job JSON results", e);
            return null;
        }
    }
}