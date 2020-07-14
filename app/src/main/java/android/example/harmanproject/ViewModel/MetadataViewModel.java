package android.example.harmanproject.ViewModel;

import android.content.Intent;
import android.content.IntentSender;
import android.example.harmanproject.View.MetadataActivity;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import timber.log.Timber;

public class MetadataViewModel {
    public static Double mLatitude;
    public static Double mLongitude;
    private static String metadata;
    private MetadataActivity mView;
    public boolean areCoordinatesExist = true;
    private LocationSettingsRequest.Builder builder;
    private final int REQUEST_CHECK_CODE = 94;

    public MetadataViewModel(MetadataActivity view){
        mView = view;
    }

    public String extractMetadata() {
        try {
            File currentImage = new File(mView.demoItemForAdapters.getPath());
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

            String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
            String imageWidth = exif.getAttribute("ImageWidth");
            String exposureProgram = exif.getAttribute("ExposureProgram");
            String GPSLongitude = exif.getAttribute("GPSLongitude");
            String GPSVersionID = exif.getAttribute("GPSVersionID");

            String dateTime = exif.getAttribute(ExifInterface.TAG_DATETIME);
            int orientation = exif.getRotationDegrees();

            if (coordinates != null) {
                GeoDegree geoDegree = new GeoDegree(exif);
                mLatitude = Double.valueOf(geoDegree.toString().split(", ")[0]);
                mLongitude = Double.valueOf(geoDegree.toString().split(", ")[1]);
            } else {
                Log.i("Metadata", "There is no gps coordinates in this image");
                Toast.makeText(mView.getApplicationContext(), "There is no gps coordinates in this image", Toast.LENGTH_SHORT).show();
                areCoordinatesExist = false;
            }

            metadata = Arrays.toString(coordinates) + "\n ImageWidth is: " + imageWidth + ",\n ExposureProgram is: " + exposureProgram + ",\n Orientation is: " + orientation + ",\n GPSVersionID is: " + GPSVersionID;

        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
        return metadata;
    }

    public void enableGPS() {
        LocationRequest request = new LocationRequest()
                .setFastestInterval(1500)
                .setInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(mView).checkLocationSettings(builder.build());

        result.addOnCompleteListener(task -> {
            try {
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes
                            .RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(mView, REQUEST_CHECK_CODE);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        } catch (ClassCastException ex) {}
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    {
                        break;
                    }
                }
            }
        });
    }

    public void goToSettingsToTurnOnGps() {
        Log.i("Repeating action", "I am going to settings");
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mView.startActivity(intent);
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
