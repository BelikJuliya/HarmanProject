package android.example.harmanproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Activity2 extends AppCompatActivity {

    public static ArrayList<ExampleElement> exampleList;
    private GridFragment mGridFragment;
    private FragmentTransaction mGridTrans;
    private RecyclerFragment mRecyclerFragment;
    private PageFragment mPageFragment;
    ImageView mImageView;

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


        // file directory: "/storage/emulated/0/Pictures/Instagram/"
        int imageResource = 0;
        exampleList = new ArrayList<>();
        mImageView = findViewById(R.id.example_image);

        //filling out the list with content which will be shown in textViews
        File folder = new File("/storage/emulated/0/Pictures/Instagram/");
        ArrayList <String> fileNames = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.list())));
        ArrayList <File> imagesFromDirectory = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.listFiles())));

        for (int i = 0; i < fileNames.size(); i++) {
            Drawable drawable = Drawable.createFromPath(imagesFromDirectory.get(i).getAbsolutePath());
            //int id = getResources().getIdentifier(imagesFromGallery.get(i).getAbsolutePath(), null, null);
            Picasso.with(getApplicationContext()).load(imagesFromDirectory.get(i)).into(mImageView);
            exampleList.add(new ExampleElement(mImageView, fileNames.get(i)));
        }
    }

    public void loadImage(View view) {
        mImageView = findViewById(R.id.example_image);
        Picasso.with(getApplicationContext()).load("/storage/emulated/0/Pictures/Instagram").into(mImageView);
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



