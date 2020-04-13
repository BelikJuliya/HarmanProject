package android.example.harmanproject;

import android.net.Uri;

import java.io.Serializable;

public class ExampleElement implements Serializable {
    private Uri mImageUri;
    public String mPath = "/storage/emulated/0/Pictures/Instagram/";
    private String mText;

    public ExampleElement(Uri uri, String text) {
        mImageUri = uri;
        mText = text;
    }

    public Uri getImageResource() {
        return mImageUri;
    }

    public String getText() {
        return mText;
    }
}
