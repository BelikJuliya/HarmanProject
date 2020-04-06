package android.example.harmanproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    private ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        mImageView = view.findViewById(R.id.gun_image);
        return view;
    }

    private int angle = 0;

    void rotatePicture() {
        this.angle += +90;
        mImageView.animate().rotation(angle);
    }
}