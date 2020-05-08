package android.example.harmanproject;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import timber.log.Timber;


public class Activity2 extends AppCompatActivity {

    public static ArrayList<ExampleElement> exampleList;
    private GridFragment mGridFragment;
    private FragmentTransaction mGridTrans;
    private RecyclerFragment mRecyclerFragment;
    private PageFragment mPageFragment;
    static ArrayList<Path> images = new ArrayList<>();
    @SuppressLint("SdCardPath")
    String mDirectory = "/sdcard/Download";

    @RequiresApi(api = Build.VERSION_CODES.O)
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


        exampleList = new ArrayList<>();
        File folder = new File(mDirectory);
        if (folder.isDirectory() && folder.exists()) {
            ArrayList<File> imagesFromDirectory = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.listFiles()))); //list with files to show in imageView
            ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.list()))); //list with names to show in textView
            if (!(imagesFromDirectory.isEmpty())) {
                for (int i = 0; i < imagesFromDirectory.size(); i++) {
                    Uri imageURI = Uri.fromFile(imagesFromDirectory.get(i));
                    if (imagesFromDirectory.get(i).getName().toLowerCase().endsWith(".jpg") || imagesFromDirectory.get(i).getName().toLowerCase().endsWith(".png") || imagesFromDirectory.get(i).getName().toLowerCase().endsWith(".jpeg")) {
                        exampleList.add(new ExampleElement(imageURI, fileNames.get(i), imagesFromDirectory.get(i).getAbsolutePath()));
                    } else Timber.e("The file found is not an image");
                }
            } else {
                Toast.makeText(Activity2.this, "There is no images in this folder", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Activity2.this, "There is no such directory", Toast.LENGTH_SHORT).show();
        }
    }


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


