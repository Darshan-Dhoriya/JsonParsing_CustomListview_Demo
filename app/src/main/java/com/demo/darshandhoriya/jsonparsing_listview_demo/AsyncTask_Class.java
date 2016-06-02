package com.demo.darshandhoriya.jsonparsing_listview_demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.util.Progress;
import com.demo.darshandhoriya.jsonparsing_listview_demo.Movie_Adapter;
import com.demo.darshandhoriya.jsonparsing_listview_demo.Movie_ModelClass;
import com.demo.darshandhoriya.jsonparsing_listview_demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class AsyncTask_Class extends AsyncTask<String, String, List<Movie_ModelClass>> {

    public ProgressDialog progressDialog;
    public final Context mcontext;
    public final Activity mactivity;
    public ListView lv_main;


    public AsyncTask_Class(final Context context, final Activity activity) {
        mcontext = context;
        mactivity = activity;
    }


    @Override

    protected void onPreExecute() {

        super.onPreExecute();
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading..Please wait");
        progressDialog.show();
    }


    StringBuffer strBuffer;

    @Override
    protected List<Movie_ModelClass> doInBackground(String... params) {


        try {
            URL url = new URL(params[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream ipStream = connection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(ipStream));
            strBuffer = new StringBuffer();

            String strTemp = " ";

            while ((strTemp = bReader.readLine()) != null) {
                strBuffer.append(strTemp);
            }

            String finalDataString = strBuffer.toString();

            // Print Log
            Log.i("TAG RESULT", "Result Data : " + finalDataString);

            JSONObject jObject_main = new JSONObject(finalDataString);
            JSONArray jArray_main = jObject_main.getJSONArray("movies");


            List<Movie_ModelClass> list_ModelClass = new ArrayList<>();


            for (int i = 0; i < jArray_main.length(); i++) {

                JSONObject jObject_Temp = jArray_main.getJSONObject(i);

                Movie_ModelClass movie_modelClass = new Movie_ModelClass();

                movie_modelClass.setMovie(jObject_Temp.getString("movie"));
                movie_modelClass.setYear(jObject_Temp.getInt("year"));
                movie_modelClass.setRating((float) jObject_Temp.getDouble("rating"));
                movie_modelClass.setDuration(jObject_Temp.getString("duration"));
                movie_modelClass.setDirector(jObject_Temp.getString("director"));
                movie_modelClass.setTagline(jObject_Temp.getString("tagline"));
                movie_modelClass.setImage(jObject_Temp.getString("image"));
                movie_modelClass.setStory(jObject_Temp.getString("story"));


                List<Movie_ModelClass.Cast> castList = new ArrayList<>();

                for (int j = 0; j < jObject_Temp.getJSONArray("cast").length(); j++) {


                    Movie_ModelClass.Cast cast = new Movie_ModelClass().new Cast();
                    cast.setName(jObject_Temp.getJSONArray("cast").getJSONObject(j).getString("name"));
                    castList.add(cast);
                }

                movie_modelClass.setCastList((castList));


                list_ModelClass.add(movie_modelClass);
            }


            return list_ModelClass;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(List<Movie_ModelClass> result) {
        super.onPostExecute(result);

        lv_main = (ListView)mactivity.findViewById(R.id.lv_main);

        if (result != null) {
            Movie_Adapter movieAdapter = new Movie_Adapter(mcontext, R.layout.custom_row, result);
            lv_main.setAdapter(movieAdapter);
        } else {
            Toast.makeText(mcontext, "No Data Found", Toast.LENGTH_LONG);

        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
