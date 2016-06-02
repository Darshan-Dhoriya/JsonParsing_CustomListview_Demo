package com.demo.darshandhoriya.jsonparsing_listview_demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darshan Dhoriya on 30-05-2016.
 */
public class Movie_Adapter extends ArrayAdapter {

    Context context;
    int rowId;
    List<Movie_ModelClass> list_Movie_ModelClass;
    TextView header, tagline, year, duration, director, story, cast;
    RatingBar rating;
    ImageView img_movie;



    public Movie_Adapter(Context context, int rowId, List<Movie_ModelClass> list_Movie_ModelClass) {
        super(context, rowId, list_Movie_ModelClass);

        this.context = context;
        this.rowId = rowId;
        this.list_Movie_ModelClass = list_Movie_ModelClass;

    }


    @Override
    public int getCount() {
        return this.list_Movie_ModelClass.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(rowId, parent, false);
        }


        header = (TextView) convertView.findViewById(R.id.header);
        tagline = (TextView) convertView.findViewById(R.id.tagline);
        year = (TextView) convertView.findViewById(R.id.year);
        duration = (TextView) convertView.findViewById(R.id.duration);
        director = (TextView) convertView.findViewById(R.id.director);
        cast = (TextView) convertView.findViewById(R.id.cast);
        story = (TextView) convertView.findViewById(R.id.story);
        img_movie = (ImageView) convertView.findViewById(R.id.img_movie);
        rating = (RatingBar) convertView.findViewById(R.id.rating);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

        // Code for display image through Universal Image loader
        ImageLoader.getInstance().displayImage(list_Movie_ModelClass.get(position).getImage(), img_movie, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.GONE);
            }
        });


        header.setText(list_Movie_ModelClass.get(position).getMovie());
        tagline.setText(" " + list_Movie_ModelClass.get(position).getTagline());
        year.setText("Year : " + list_Movie_ModelClass.get(position).getYear());
        duration.setText("Duration : " + list_Movie_ModelClass.get(position).getDuration());
        director.setText("Director : " + list_Movie_ModelClass.get(position).getDirector());
        rating.setRating((list_Movie_ModelClass.get(position).getRating()) / 2);


        StringBuffer stringBuffer_Cast = new StringBuffer();
        for (Movie_ModelClass.Cast cast : list_Movie_ModelClass.get(position).getCastList()) {
            stringBuffer_Cast.append(cast.getName() + " , ");
        }
        cast.setText("Cast : " + stringBuffer_Cast);
        story.setText("Story : " + list_Movie_ModelClass.get(position).getStory());


        return convertView;

    }
}




