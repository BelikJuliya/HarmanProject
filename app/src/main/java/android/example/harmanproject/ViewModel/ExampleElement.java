package android.example.harmanproject.ViewModel;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ExampleElement implements Parcelable {
    private Uri mImageUri;
    private String mText;
    private String mPath;


     ExampleElement(Uri uri, String text, String path) {
        mImageUri = uri;
        mText = text;
        mPath = path;
    }

     ExampleElement(Parcel in) {
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


    public Uri getImageUri() {
        return mImageUri;
    }

    public String getText() {
        return mText;
    }

    public String getPath() {
        return mPath;
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
