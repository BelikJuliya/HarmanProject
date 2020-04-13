package android.example.harmanproject;

import android.widget.ImageView;

import java.io.File;

public class ExampleElement {
    private int mImageResource;
    private ImageView mSrc;
    private String mText;

//    public ExampleElement(int imageResource, String text) {
//        mImageResource = imageResource;
//        mText = text;
//    }
//
//    public int getImageResource() {
//        return mImageResource;
//    }
//
//    public String getText() {
//        return mText;
//    }

    public ExampleElement(ImageView src, String text) {
        mSrc = src;
        mText = text;
    }

    public ImageView getImageResource() {
        return mSrc;
    }

    public String getText() {
        return mText;
    }

}
