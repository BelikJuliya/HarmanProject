package android.example.harmanproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        imageView = view.findViewById(R.id.gun_image);
        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        imageView = (ImageView) getView().findViewById(R.id.gun_image);
//    }

    private int angle = 0;

    public void rotatePicture() {
        this.angle += +90;
        imageView.animate().rotation(angle);
    }
}