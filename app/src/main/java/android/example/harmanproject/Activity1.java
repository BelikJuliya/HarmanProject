package android.example.harmanproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class Activity1 extends FragmentActivity {

    Fragment1 frag1;
    Fragment2 frag2;
    FragmentTransaction fTrans1;
    FragmentTransaction fTrans2;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);

        frag1 = new Fragment1();
        frag2 = new Fragment2();

        // При создании активити в верхний контейнер кладем фрагмент 1
        fTrans1 = getSupportFragmentManager().beginTransaction();
        fTrans1.add(R.id.higher_frame, frag1);
        fTrans1.commit();

//        fTrans2 = getSupportFragmentManager().beginTransaction();
//        fTrans2.replace(R.id.lower_frame, frag2);
//        fTrans2.commit();

            button = findViewById(R.id.rotation);
            button.setOnClickListener((v) -> frag1.rotatePicture());
    }




    public void onClicker(View v) {

        // Кладем в нижний контейнер фрагмент 2
        // раскомментить
        fTrans2 = getSupportFragmentManager().beginTransaction();
        fTrans2.replace(R.id.lower_frame, frag2);
        fTrans2.commit();

        //Кладем в верхний контейнер фрагмент 2
        //закомментить
//        fTrans1 = getSupportFragmentManager().beginTransaction();
//        fTrans1.replace(R.id.higher_frame, frag2);
//        fTrans1.commit();
    }
}

