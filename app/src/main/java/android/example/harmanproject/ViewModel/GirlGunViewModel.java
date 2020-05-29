package android.example.harmanproject.ViewModel;

import android.example.harmanproject.View.GirlGun;

public class GirlGunViewModel {
    private GirlGun fragment1;

    public GirlGunViewModel(GirlGun fragment1) {
        this.fragment1 = fragment1;
    }

    private int angle = 0;

    public void rotatePicture() {
        this.angle += +90;
        fragment1.mImageView.animate().rotation(angle);
    }
}
