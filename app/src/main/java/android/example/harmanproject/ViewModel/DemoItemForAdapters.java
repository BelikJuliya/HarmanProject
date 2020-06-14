package android.example.harmanproject.ViewModel;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class DemoItemForAdapters implements Parcelable {
    private Uri mImageUri;
    private String mText;
    private String mPath;


     DemoItemForAdapters(Uri uri, String text, String path) {
        mImageUri = uri;
        mText = text;
        mPath = path;
    }

     DemoItemForAdapters(Parcel in) {
        mImageUri = in.readParcelable(Uri.class.getClassLoader());
        mText = in.readString();
        mPath = in.readString();
    }

    public static final Creator<DemoItemForAdapters> CREATOR = new Creator<DemoItemForAdapters>() {
        @Override
        public DemoItemForAdapters createFromParcel(Parcel in) {
            return new DemoItemForAdapters(in);
        }

        @Override
        public DemoItemForAdapters[] newArray(int size) {
            return new DemoItemForAdapters[size];
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
