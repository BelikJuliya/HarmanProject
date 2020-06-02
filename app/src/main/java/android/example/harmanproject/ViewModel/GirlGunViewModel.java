package android.example.harmanproject.ViewModel;

import android.example.harmanproject.View.GirlGunFragment;

public class GirlGunViewModel {
    private GirlGunFragment fragment1;

    public GirlGunViewModel(GirlGunFragment fragment1) {
        this.fragment1 = fragment1;
    }

    private int angle = 0;

    public void rotatePicture() {
        this.angle += +90;
        fragment1.mImageView.animate().rotation(angle);
    }
}
