package android.example.harmanproject.View;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.Fragment1ViewModel;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    public ImageView mImageView;
    private Fragment1ViewModel mViewModel;

    public Fragment1() {
        mViewModel = new Fragment1ViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        mImageView = view.findViewById(R.id.gun_image);
        return view;
    }




}