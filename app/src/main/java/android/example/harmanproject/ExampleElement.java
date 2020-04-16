package android.example.harmanproject;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ExampleElement implements Parcelable {
    private Uri mImageUri;
    private String mText;
    public String mPath = "/storage/emulated/0/Pictures/Instagram/";


    public ExampleElement(Uri uri, String text) {
        mImageUri = uri;
        mText = text;
    }

    protected ExampleElement(Parcel in) {
        mImageUri = in.readParcelable(Uri.class.getClassLoader());
        mText = in.readString();
        mPath = in.readString();
    }

    public static final Creator<ExampleElement> CREATOR = new Creator<ExampleElement>() {
        @Override
        public ExampleElement createFromParcel(Parcel in) {
            return new ExampleElement(in);
        }

        @Override
        public ExampleElement[] newArray(int size) {
            return new ExampleElement[size];
        }
    };

    public Uri getImageResource() {
        return mImageUri;
    }

    public String getText() {
        return mText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mImageUri, flags);
        dest.writeString(mText);
        dest.writeString(mPath);
    }
}
