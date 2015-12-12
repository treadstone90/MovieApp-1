package com.example.karthik.movieapp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageSwitcher;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by karthik on 12/2/15.
 */
public class SwipeableImageSwitcher extends ImageSwitcher implements Target {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    private GestureDetectorCompat mDetector;
    private int currentImage = 0;

    private List<String> imageUris;

    private Context mContext;

    public SwipeableImageSwitcher(Context context) {
        super(context);
        this.mContext = context;
        mDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());
    }

    public SwipeableImageSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());
    }

    public void setImageList(List<String> imageUris) {
        this.imageUris = imageUris;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("view", "ontouch: " + event.toString());
        this.mDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        this.setImageDrawable(new BitmapDrawable(bitmap));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        Log.e("SwipableImageSwitcher" , "Loading failed");
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        Log.e("Loading image", "Loading image");
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {


            Log.d("---onFling---", e1.toString() + e2.toString() + "");

            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onLeftFling();

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                onRightFling();
                }

            } catch (Exception e) {
                // nothing
            }
            return false;

        }
    }

    private void onRightFling() {
        String imageUri = imageUris.get(0);
        Picasso.with(mContext).load(imageUri).into(this);
    }

    private String getImageUriToLoad() {
        return null;
    }

    private void onLeftFling() {
        String imageUri = imageUris.get(0);
        Picasso.with(mContext).load(imageUri).into(this);
    }
}
