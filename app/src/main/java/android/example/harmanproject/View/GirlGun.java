package android.example.harmanproject.View;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.GirlGunViewModel;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class GirlGun extends Fragment {
    public ImageView mImageView;
    private GirlGunViewModel mViewModel;

    public GirlGun() {
        mViewModel = new GirlGunViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.girl_gun, container, false);
        mImageView = view.findViewById(R.id.gun_image);
        return view;
    }




}