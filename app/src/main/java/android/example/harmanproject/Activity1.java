package android.example.harmanproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class Activity1 extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);

        Fragment1 frag1 = new Fragment1();

        //At the moment of activity creation put fragment 1 to the upper container
        FragmentTransaction fTrans1 = getSupportFragmentManager().beginTransaction();
        fTrans1.add(R.id.higher_frame, frag1);
        fTrans1.commit();

        Button rotateButton = findViewById(R.id.rotate_btn);
        rotateButton.setOnClickListener((v) -> frag1.rotatePicture());
    }


    public void clickReplaceFragments(View v) {

        Fragment2 frag2 = new Fragment2();

        // Put fragment 2 to the lower container
        FragmentTransaction fTrans2 = getSupportFragmentManager().beginTransaction();
        fTrans2.replace(R.id.lower_frame, frag2);
        fTrans2.commit();

    }
}

