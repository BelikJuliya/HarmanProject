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
    FragmentTransaction gridTrans1;

    RecyclerFragment recyclerFragment;
    FragmentTransaction recyclerTrans;

    PageFragment pageFragment;
    FragmentTransaction pageTrans;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        gridFragment = new GridFragment();
        gridTrans1 = getSupportFragmentManager().beginTransaction();
        gridTrans1.add(R.id.container, gridFragment);
        gridTrans1.commit();

        recyclerFragment = new RecyclerFragment();
        pageFragment = new PageFragment();

    }


    // implementing option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.recycle){
            recyclerTrans = getSupportFragmentManager().beginTransaction();
            recyclerTrans.replace(R.id.container, recyclerFragment);
            recyclerTrans.commit();
        }
        if (item.getItemId() == R.id.page){
            pageTrans = getSupportFragmentManager().beginTransaction();
            pageTrans.replace(R.id.container, pageFragment);
            pageTrans.commit();
        }
        if (item.getItemId() == R.id.grid){
            gridTrans1 = getSupportFragmentManager().beginTransaction();
            gridTrans1.replace(R.id.container, gridFragment);
            gridTrans1.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    //наполняем все виды вьюшек одинаковым контентом
    public static ArrayList<String> generateValues() {
        ArrayList <String> elements = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            elements.add(i + " element");
        }
        return elements;
    }


}
