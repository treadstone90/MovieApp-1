package com.example.karthik.movieapp.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.karthik.movieapp.moviedb.DiscoverMovieJson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by karthik on 11/26/15.
 */

public class ImageAdapter extends ArrayAdapter<DiscoverMovieJson> {
    private Context mContext;
    private @LayoutRes int mResource;

    public ImageAdapter(Context c, @LayoutRes int resource) {
        super(c, resource);
        this.mContext = c;
        this.mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("Image imageAdapter", "Loadng view at position" + position);
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = (ImageView) LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        DiscoverMovieJson movie = getItem(position);
        Log.i("Image imageAdapter", "With url" + movie.getPoster_path());
        Picasso.with(mContext).load(movie.getPoster_path()).into(view);
        return view;
    }
}