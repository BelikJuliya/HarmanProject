package android.example.harmanproject.View;

import android.example.harmanproject.ViewModel.GirlGunViewModel;
import android.example.harmanproject.databinding.GirlGunBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class GirlGun extends Fragment {
    public ImageView mImageView;

    public GirlGun() {
        new GirlGunViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GirlGunBinding binding = GirlGunBinding.inflate(inflater, container, false);
        mImageView = binding.gunImage;
        return binding.getRoot();
    }


}