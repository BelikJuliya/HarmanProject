package android.example.harmanproject.ViewModel;

import android.example.harmanproject.View.Activity3;

import androidx.exifinterface.media.ExifInterface;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import timber.log.Timber;

public class Activity3ViewModel {
    public static Double mLatitude;
    public static Double mLongitude;
    private static String metadata;
    public static String extractMetadata() {
        try {
            File currentImage = new File(Activity3.exampleElement.getPath());
            Metadata imageMetadata = ImageMetadataReader.readMetadata(currentImage);
            for (Directory obj : imageMetadata.getDirectories()) {
                Timber.e(obj.getName());
                for (Tag tag : obj.getTags()) {
                    Timber.e(tag.getDescription());
                    Timber.e(tag.getTagName());
                    if (tag.getTagName().equals("GPS Latitude")) {
                        System.out.println("here " + tag.getDescription());
                    }
                }
            }

            ExifInterface exif = new ExifInterface(currentImage.getAbsolutePath());
            double[] coordinates = exif.getLatLong();
            String ImageWidth = exif.getAttribute("ImageWidth");
            String ExposureProgram = exif.getAttribute("ExposureProgram");
            String GPSLongitude = exif.getAttribute("GPSLongitude");
            String GPSVersionID = exif.getAttribute("GPSVersionID");

            String dateTime = exif.getAttribute(ExifInterface.TAG_DATETIME);
            int orientation = exif.getRotationDegrees();

            GeoDegree geoDegree = new GeoDegree(exif);
            mLatitude = Double.valueOf(geoDegree.toString().split(", ")[0]);
            mLongitude = Double.valueOf(geoDegree.toString().split(", ")[1]);

            metadata = Arrays.toString(coordinates) + "\n ImageWidth is: " + ImageWidth + ",\n ExposureProgram is: " + ExposureProgram + ",\n Orientation is: " + orientation + ",\n GPSVersionID is: " + GPSVersionID;


        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
        return metadata;
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
