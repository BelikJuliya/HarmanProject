package android.example.harmanproject.ViewModel;

import android.example.harmanproject.View.TestMapBox;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineResult;

import java.lang.ref.WeakReference;

public class LocationListeningCallback implements LocationEngineCallback<LocationEngineResult> {
    private final WeakReference<TestMapBox> activityWeakReference;

    public LocationListeningCallback(TestMapBox activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onSuccess(LocationEngineResult result) {
        // The LocationEngineCallback interface's method which fires when the device's location has changed.
        Location lastLocation = result.getLastLocation();
        Log.i("Location", "I get the current location: " + lastLocation);
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        // The LocationEngineCallback interface's method which fires when the device's location can not be captured
        Log.i("Location", "I failed to get the last known location");
    }
}

