package android.example.harmanproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    GridFragment gridFragment;
    FragmentTransaction gridTrans;

    RecyclerFragment recyclerFragment;
    FragmentTransaction recyclerTrans;

    PageFragment pageFragment;
    FragmentTransaction pageTrans;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        recyclerFragment = new RecyclerFragment();
        pageFragment = new PageFragment();
        gridFragment = new GridFragment();

        /* Гридвью появляется в контейнере, при создании активити
        (то есть отображается по умолчанию до выбора другого вида отображения в меню
        (вызова метода onOptionsItemSelected))
         */
        gridTrans = getSupportFragmentManager().beginTransaction();
        gridTrans.add(R.id.container, gridFragment);
        gridTrans.commit();

        // альтернативный вариант - recyclerView  по умолчанию
//        recyclerTrans = getSupportFragmentManager().beginTransaction();
//        recyclerTrans.add(R.id.container, recyclerFragment);
//        recyclerTrans.commit();

    }

    // implementing option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.recycle) {
            recyclerTrans = getSupportFragmentManager().beginTransaction();
            recyclerTrans.replace(R.id.container, recyclerFragment);
            recyclerTrans.commit();
        }
        if (item.getItemId() == R.id.page) {
            pageTrans = getSupportFragmentManager().beginTransaction();
            pageTrans.replace(R.id.container, pageFragment);
            pageTrans.commit();
        }
        if (item.getItemId() == R.id.grid) {
            gridTrans = getSupportFragmentManager().beginTransaction();
            gridTrans.replace(R.id.container, gridFragment);
            gridTrans.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    //наполняем все виды вьюшек одинаковым контентом
    public static ArrayList<String> generateValues() {
        ArrayList<String> elements = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            elements.add(i + " element");
        }
        return elements;
    }
}
