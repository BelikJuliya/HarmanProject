package android.example.harmanproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Activity3 extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Intent intent = getIntent();
        ExampleElement exampleElement = intent.getParcelableExtra("Example element");

        assert exampleElement != null;
        Uri imageRes = exampleElement.getImageResource();
        String textRes = exampleElement.getText();

        ImageView imageView = findViewById(R.id.image_activity_3);
        imageView.setImageURI(imageRes);

        TextView nameOfImage = findViewById(R.id.text_activity_3);
        nameOfImage.setText(textRes);

        ArrayList<Iterable> metaData = new ArrayList<>();
        try {
            File currentImage = new File(exampleElement.getPath());
            Metadata imageMetadata = ImageMetadataReader.readMetadata(currentImage);

            ExifInterface exif = new ExifInterface(currentImage.getAbsolutePath());
            String latitude = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String longitude = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            //metaData.add(imageMetadata.getDirectories());
            TextView metadataTextView = findViewById(R.id.meta_data);
            metadataTextView.setText(imageMetadata.toString());
            
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }


        //metadata.setText(exampleElement.getImageMetaData().toString());


        //exampleElement.getImageMetaData();
//
//        try {
//            Metadata imageMetadata = ImageMetadataReader.readMetadata(imagesFromDirectory.get(i));
//            GpsDirectory gpsDirectory = (GpsDirectory) imageMetadata.getDirectories();
//            GeoLocation exifLocation = gpsDirectory.getGeoLocation();
//        } catch (ImageProcessingException | IOException e) {
//            System.out.println("Oops, something goes wrong with metadata");
//            e.printStackTrace();
//        }
        //extracting metadata
//        javaxt.io.Image image = new javaxt.io.Image("D:\\codeTest\\arun.jpg");
//        double[] gps = image.getGPSCoordinate();
    }
}
