package android.example.harmanproject;

import android.annotation.SuppressLint;
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

public class Activity3 extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
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

        try {
            File currentImage = new File(exampleElement.getPath());
            Metadata imageMetadata = ImageMetadataReader.readMetadata(currentImage);

            ExifInterface exif = new ExifInterface(currentImage.getAbsolutePath());
            String latitude = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String longitude = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            GeoDegree geoDegree = new GeoDegree(exif);

            TextView metadataTextView = findViewById(R.id.meta_data);
            metadataTextView.setText(geoDegree.toString());
           // metadataTextView.setText(latitude + ", " + longitude);



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

    public static class GeoDegree {
        private boolean valid = false;
        Float Latitude, Longitude;

        GeoDegree(ExifInterface exif) {
            String attrLATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String attrLATITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String attrLONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String attrLONGITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

            if ((attrLATITUDE != null)
                    && (attrLATITUDE_REF != null)
                    && (attrLONGITUDE != null)
                    && (attrLONGITUDE_REF != null)) {
                valid = true;

                if (attrLATITUDE_REF.equals("N")) {
                    Latitude = convertToDegree(attrLATITUDE);
                } else {
                    Latitude = 0 - convertToDegree(attrLATITUDE);
                }

                if (attrLONGITUDE_REF.equals("E")) {
                    Longitude = convertToDegree(attrLONGITUDE);
                } else {
                    Longitude = 0 - convertToDegree(attrLONGITUDE);
                }

            }
        }

        ;

        private Float convertToDegree(String stringDMS) {
            Float result = null;
            String[] DMS = stringDMS.split(",", 3);

            String[] stringD = DMS[0].split("/", 2);
            Double D0 = Double.valueOf(stringD[0]);
            Double D1 = Double.valueOf(stringD[1]);
            double FloatD = D0 / D1;

            String[] stringM = DMS[1].split("/", 2);
            Double M0 = Double.valueOf(stringM[0]);
            Double M1 = Double.valueOf(stringM[1]);
            double FloatM = M0 / M1;

            String[] stringS = DMS[2].split("/", 2);
            Double S0 = Double.valueOf(stringS[0]);
            Double S1 = Double.valueOf(stringS[1]);
            double FloatS = S0 / S1;

            result = (float) (FloatD + (FloatM / 60) + (FloatS / 3600));

            return result;


        }

        ;

        public boolean isValid() {
            return valid;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return (String.valueOf(Latitude)
                    + ", "
                    + String.valueOf(Longitude));
        }

        public int getLatitudeE6() {
            return (int) (Latitude * 1000000);
        }

        public int getLongitudeE6() {
            return (int) (Longitude * 1000000);
        }
    }
}
