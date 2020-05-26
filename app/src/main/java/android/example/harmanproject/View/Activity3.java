package android.example.harmanproject.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.Activity3ViewModel;
import android.example.harmanproject.ViewModel.ExampleElement;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Activity3 extends AppCompatActivity {

    public static ExampleElement exampleElement;


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Intent intent = getIntent();
        exampleElement = intent.getParcelableExtra("Example element");

        assert exampleElement != null;
        Uri imageRes = exampleElement.getImageResource();
        String textRes = exampleElement.getText();

        ImageView imageView = findViewById(R.id.image_activity_3);
        imageView.setImageURI(imageRes);

        TextView nameOfImage = findViewById(R.id.text_activity_3);
        nameOfImage.setText(textRes);

        TextView metadataTextView = findViewById(R.id.meta_data);
        metadataTextView.setText(Activity3ViewModel.extractMetadata());


//        try {
//            File currentImage = new File(exampleElement.getPath());
//            Metadata imageMetadata = ImageMetadataReader.readMetadata(currentImage);
//            for (Directory obj : imageMetadata.getDirectories()) {
//                Timber.e(obj.getName());
//                for (Tag tag : obj.getTags()) {
//                    Timber.e(tag.getDescription());
//                    Timber.e(tag.getTagName());
//                    if (tag.getTagName().equals("GPS Latitude")) {
//                        System.out.println("here " + tag.getDescription());
//                    }
//                }
//            }
//
//            ExifInterface exif = new ExifInterface(currentImage.getAbsolutePath());
//            double[] coordinates = exif.getLatLong();
//            String ImageWidth = exif.getAttribute("ImageWidth");
//            String ExposureProgram = exif.getAttribute("ExposureProgram");
//            String GPSLongitude = exif.getAttribute("GPSLongitude");
//            String GPSVersionID = exif.getAttribute("GPSVersionID");
//
//            String dateTime = exif.getAttribute(ExifInterface.TAG_DATETIME);
//            int orientation = exif.getRotationDegrees();
//
//            GeoDegree geoDegree = new GeoDegree(exif);
//            mLatitude = Double.valueOf(geoDegree.toString().split(", ")[0]);
//            mLongitude = Double.valueOf(geoDegree.toString().split(", ")[1]);
//
//            TextView metadataTextView = findViewById(R.id.meta_data);
//            metadataTextView.setText(Arrays.toString(coordinates) + "\n ImageWidth is: " + ImageWidth + ",\n ExposureProgram is: " + ExposureProgram + ",\n Orientation is: " + orientation + ",\n GPSVersionID is: " + GPSVersionID);
//           // metadataTextView.setText("ImageWidth is: " + ImageWidth);
//
//        } catch (ImageProcessingException | IOException e) {
//            e.printStackTrace();
//        }
    }


    public void openMap(View view) {
        Intent intent = new Intent(Activity3.this, Activity4.class);
        startActivity(intent);
    }
}


