package android.example.harmanproject;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.drew.metadata.Metadata;

public class ExampleElement implements Parcelable {
    private Uri mImageUri;
    private String mText;
    private Metadata mImageMetaData;
    private String mPath = "/storage/emulated/0/Pictures/Instagram/";



    public ExampleElement(Uri uri, String text, Metadata metadata) {
        mImageUri = uri;
        mText = text;
        mImageMetaData = metadata;
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

    public Metadata getImageMetaData (){
        return mImageMetaData;
    }
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

//    protected ExampleElement(Parcel in) {
//        mImageUri = in.readParcelable(Uri.class.getClassLoader());
//        mText = in.readString();
//        mPath = in.readString();
//    }

//    public static final Creator<ExampleElement> CREATOR = new Creator<ExampleElement>() {
//        @Override
//        public ExampleElement createFromParcel(Parcel in) {
//            return new ExampleElement(in);
//        }
//
//        @Override
//        public ExampleElement[] newArray(int size) {
//            return new ExampleElement[size];
//        }
//    };



//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeParcelable(mImageUri, flags);
//        dest.writeString(mText);
//        dest.writeString(mPath);
//    }
}
