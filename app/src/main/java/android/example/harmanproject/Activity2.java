package android.example.harmanproject;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Activity2 extends AppCompatActivity {

    public static ArrayList<ExampleElement> exampleList;
    private GridFragment mGridFragment;
    private FragmentTransaction mGridTrans;
    private RecyclerFragment mRecyclerFragment;
    private PageFragment mPageFragment;
    private ArrayList <Iterable> mMetadata = new ArrayList();

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
        String directory = "/storage/emulated/0/Pictures/7Fon/";
        File folder = new File(directory);
        if (folder.isDirectory() && folder.exists()) {
            ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.list()))); //list with names to show in textView
            ArrayList<File> imagesFromDirectory = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.listFiles()))); //list with files to show in imageView
            if (!(imagesFromDirectory.isEmpty())) {
                for (int i = 0; i < fileNames.size(); i++) {
                    Uri imageURI = Uri.fromFile(imagesFromDirectory.get(i));
                    if (imagesFromDirectory.get(i).getName().endsWith(".jpg") || imagesFromDirectory.get(i).getName().endsWith(".png") || imagesFromDirectory.get(i).getName().endsWith(".jpeg")) {
                        try {
                            Metadata imageMetadata = ImageMetadataReader.readMetadata(imagesFromDirectory.get(i));
                            mMetadata.add(imageMetadata.getDirectories());
                            for (int j = 0; j < mMetadata.size(); j++) {

                            }
                            exampleList.add(new ExampleElement(imageURI, fileNames.get(i), imageMetadata));
                        } catch (ImageProcessingException | IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            } else {
                Toast.makeText(this, "There is no images in this folder", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "There is no such directory", Toast.LENGTH_SHORT).show();
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


