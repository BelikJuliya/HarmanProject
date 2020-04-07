package android.example.harmanproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    private GridFragment mGridFragment;
    private FragmentTransaction mGridTrans;
    private RecyclerFragment mRecyclerFragment;
    private PageFragment mPageFragment;
    static ArrayList<String> mElements;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        mRecyclerFragment = new RecyclerFragment();
        mPageFragment = new PageFragment();
        mGridFragment = new GridFragment();

        /*
         GridView appears in the container at the moment of activity creation
        (i.e. it is displayed by default before choosing another type of display in the menu
        (calling the onOptionsItemSelected method))
         */
        mGridTrans = getSupportFragmentManager().beginTransaction();
        mGridTrans.add(R.id.container, mGridFragment);
        mGridTrans.commit();

        mElements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String plural = this.getResources().getQuantityString(R.plurals.pluralsForList, i, i);
            mElements.add(plural);
        }
    }

    // implementing option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recycle:
                FragmentTransaction recyclerTrans = getSupportFragmentManager().beginTransaction();
                recyclerTrans.replace(R.id.container, mRecyclerFragment);
                recyclerTrans.commit();
                break;

            case R.id.page:
                FragmentTransaction pageTrans = getSupportFragmentManager().beginTransaction();
                pageTrans.replace(R.id.container, mPageFragment);
                pageTrans.commit();
                break;

            case R.id.grid_view:
                mGridTrans = getSupportFragmentManager().beginTransaction();
                mGridTrans.replace(R.id.container, mGridFragment);
                mGridTrans.commit();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}



