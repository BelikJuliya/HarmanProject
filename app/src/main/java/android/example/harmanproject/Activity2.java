package android.example.harmanproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Activity2 extends AppCompatActivity {

    public static ArrayList<ExampleElement> exampleList;
    private GridFragment mGridFragment;
    private FragmentTransaction mGridTrans;
    private RecyclerFragment mRecyclerFragment;
    private PageFragment mPageFragment;
    static ArrayList<Path> images = new ArrayList<>();
    //String mDirectory = "/storage/self/primary/Download";
    @SuppressLint("SdCardPath")
    String mDirectory = "/sdcard/Download";
    //String directory = "/storage/emulated/0/Pictures/7Fon/";
    //String directory = "/mnt/sdcard/Download";
    //String directory = "/storage/emulated/0/VK";

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

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(Activity2.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(Activity2.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();

            }
        };

        TedPermission.with(Activity2.this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            File folder = new File(mDirectory);
            if (folder.isDirectory() && folder.exists()) {
                boolean boo = folder.canExecute();
                boolean boo2 = folder.canRead();
                ArrayList<File> imagesFromDirectory = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.listFiles()))); //list with files to show in imageView
                ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.list()))); //list with names to show in textView
                if (!(imagesFromDirectory.isEmpty())) {
                    for (int i = 0; i < imagesFromDirectory.size(); i++) {
                        Uri imageURI = Uri.fromFile(imagesFromDirectory.get(i));
                        if (imagesFromDirectory.get(i).getName().endsWith(".jpg") || imagesFromDirectory.get(i).getName().endsWith(".png") || imagesFromDirectory.get(i).getName().endsWith(".jpeg")) {
                            exampleList.add(new ExampleElement(imageURI, fileNames.get(i), imagesFromDirectory.get(i).getAbsolutePath()));
                        } else Log.e("Files", "The file found is not an image");
                    }
                } else {
                    Toast.makeText(Activity2.this, "There is no images in this folder", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Activity2.this, "There is no such directory", Toast.LENGTH_SHORT).show();
            }
        }

//        try {
//            File folder = new File(directory);
//            if (folder.isDirectory() && folder.exists()) {
//                FileVisitor fileVisitor = new FileVisitor();
//                Files.walkFileTree(Paths.get(directory), fileVisitor);
//            } else {
//                Toast.makeText(this, "There is no such directory", Toast.LENGTH_SHORT).show();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (!(images.isEmpty())) {
//            for (int i = 0; i < images.size(); i++) {
//                Uri imageURI = Uri.fromFile(images.get(i).toFile());
//                exampleList.add(new ExampleElement(imageURI, images.get(i).getFileName().toString(), images.get(i).toAbsolutePath().toString()));
//            }
//        } else {
//            Toast.makeText(this, "There is no images in this folder", Toast.LENGTH_SHORT).show();
//        }


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

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static class FileVisitor extends SimpleFileVisitor<Path> {
//
//
//        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//           if (Files.isRegularFile(file)) {
//                if (file.getFileName().endsWith(".jpg") || file.getFileName().endsWith(".png")) {
//                    images.add(file);
//                }
//            }
//            return FileVisitResult.CONTINUE;
//        }
    //}
}


