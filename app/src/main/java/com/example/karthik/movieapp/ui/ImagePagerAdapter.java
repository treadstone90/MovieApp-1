package com.example.karthik.movieapp.ui;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by karthik on 12/6/15.
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {
    private Context mContext;
    private @LayoutRes int mResource;
    List<String> imageUris;

    public ImagePagerAdapter(Context c, @LayoutRes int resource) {
        super();
        this.imageUris = new ArrayList<>();
        this.mContext = c;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        Log.i("Image imageAdapter", "Loadng view at position" + position);
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = (ImageView) LayoutInflater.from(mContext).inflate(mResource, container, false);
        }

        String movie = imageUris.get(position);
        Log.i("Image imageAdapter", "With url" + movie);
        Picasso.with(mContext).load(movie).into(view);
        return view;
    }

    public void addAll(Collection<String> uri) {
        imageUris.addAll(uri);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageUris.size();
    }
}
