package android.example.harmanproject;

public class ExampleElement {
    private int mImageResource;
    private String mText;

    public ExampleElement(int imageResource, String text) {
        mImageResource = imageResource;
        mText = text;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText() {
        return mText;
    }
}
