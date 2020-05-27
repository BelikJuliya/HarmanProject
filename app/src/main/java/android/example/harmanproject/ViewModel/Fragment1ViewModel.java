package android.example.harmanproject.ViewModel;

import android.example.harmanproject.View.Fragment1;

public class Fragment1ViewModel {
    private Fragment1 fragment1;

    public Fragment1ViewModel(Fragment1 fragment1) {
        this.fragment1 = fragment1;
    }

    private int angle = 0;

    public void rotatePicture() {
        this.angle += +90;
        fragment1.mImageView.animate().rotation(angle);
    }
}
