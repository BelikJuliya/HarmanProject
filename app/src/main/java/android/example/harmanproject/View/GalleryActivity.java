package android.example.harmanproject.View;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.GalleryViewModel;
import android.example.harmanproject.databinding.GalleryBinding;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


public class GalleryActivity extends AppCompatActivity {
    private GridViewFragment mGridFragment;
    private FragmentTransaction mGridTrans;
    private RecyclerViewFragment mRecyclerFragment;
    private PageViewFragment mPageFragment;
    private GalleryViewModel mViewModel;

    public GalleryActivity(){
        mViewModel = new GalleryViewModel(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GalleryBinding binding = GalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerFragment = new RecyclerViewFragment();
        mPageFragment = new PageViewFragment();
        mGridFragment = new GridViewFragment();

        /*
         GridView appears in the container at the moment of activity creation
        (i.e. it is displayed by default before choosing another type of display in the menu
        (calling the onOptionsItemSelected method))
         */
        mGridTrans = getSupportFragmentManager().beginTransaction();
        //TODO replace with binding
        mGridTrans.add(R.id.container, mGridFragment);
        mGridTrans.commit();

        mViewModel.uploadPicturesToScreen();
    }


    public void showToast(int text){
        Toast.makeText(GalleryActivity.this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO replace with binding
        getMenuInflater().inflate(R.menu.option_menu, menu);
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


