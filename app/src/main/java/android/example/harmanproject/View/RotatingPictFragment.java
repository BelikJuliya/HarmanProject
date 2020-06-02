package android.example.harmanproject.View;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.GirlGunViewModel;
import android.example.harmanproject.databinding.RotatingPictBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class RotatingPictFragment extends FragmentActivity {
    RotatingPictBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = RotatingPictBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        GirlGunFragment frag1 = new GirlGunFragment();
        GirlGunViewModel frag1ViewModel = new GirlGunViewModel(frag1);

        //At the moment of activity creation put fragment 1 to the upper container
        FragmentTransaction fTrans1 = getSupportFragmentManager().beginTransaction();

        //can't do it with binding
        fTrans1.add(R.id.higher_frame, frag1);
        //fTrans1.add(mBinding.higherFrame, frag1);
        fTrans1.commit();

        Button rotateButton = mBinding.rotateBtn;
        rotateButton.setOnClickListener((v) -> frag1ViewModel.rotatePicture());
    }


    public void clickReplaceFragments(View v) {

        EnterMessage frag2 = new EnterMessage();

        // Put fragment 2 to the lower container
        FragmentTransaction fTrans2 = getSupportFragmentManager().beginTransaction();
        fTrans2.replace(R.id.lower_frame, frag2);
        fTrans2.commit();

    }
}

