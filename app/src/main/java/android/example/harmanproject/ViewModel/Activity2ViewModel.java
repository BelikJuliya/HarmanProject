package android.example.harmanproject.ViewModel;

import android.annotation.SuppressLint;
import android.example.harmanproject.R;
import android.example.harmanproject.View.Activity2;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import timber.log.Timber;

public class Activity2ViewModel {
    @SuppressLint("SdCardPath")
    private static String mDirectory = "/sdcard/Download";
    public static ArrayList <ExampleElement> exampleList;
    private Activity2 mView;

    public Activity2ViewModel (Activity2 view){
        this.mView = view;
    }

    public void uploadPicturesToScreen() {
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
                mView.showToast(R.string.no_images);
            }
        } else {
            mView.showToast(R.string.no_directory);
        }
    }
}
