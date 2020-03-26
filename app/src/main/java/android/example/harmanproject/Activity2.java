package android.example.harmanproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
    }

    public static ArrayList<String> generateValues() {
        ArrayList <String> elements = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            elements.add(i + " element");
        }
        return elements;
    }


}
