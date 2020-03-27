package android.example.harmanproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    GridFragment gridFragment;
    FragmentTransaction gridTrans1;
    //FragmentTransaction

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        gridFragment = new GridFragment();
        gridTrans1 = getSupportFragmentManager().beginTransaction();
        gridTrans1.add(R.id.container, gridFragment);
        gridTrans1.commit();
    }

    public static ArrayList<String> generateValues() {
        ArrayList <String> elements = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            elements.add(i + " element");
        }
        return elements;
    }


}
