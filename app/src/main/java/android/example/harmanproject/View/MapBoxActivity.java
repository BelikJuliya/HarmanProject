package android.example.harmanproject.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.GpsUnits;
import android.example.harmanproject.ViewModel.MapBoxViewModel;
import android.example.harmanproject.ViewModel.MetadataViewModel;
import android.example.harmanproject.databinding.MapboxBinding;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;


public class MapBoxActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {
    public MapView mMapView;
    public MapboxMap mMapBoxMap;
    public PermissionsManager mPermissionsManager;
    public LocationComponent mLocationComponent;
    public DirectionsRoute mCurrentRoad;
    private Point mOriginPoint;
    private Point mDestinationPoint;
    private Button mStartButton;
    public NavigationMapRoute mRoute;
    private MapBoxViewModel mViewModel;
    private LocationEngine locationEngine;
    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback(this);
    public boolean isGPS;

    public MapBoxActivity() {
        mViewModel = new MapBoxViewModel(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN));
        MapboxBinding binding = MapboxBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setTitle("MapBox");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMapView = binding.mapView;

        mStartButton = binding.startBtn;

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mMapBoxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                //mViewModel.enableLocationComponent(style);
                enableLocationComponent(style);
            }
        });
    }

    public void passLatLong(double latitude, double longitude) {
        mOriginPoint = Point.fromLngLat(longitude, latitude);
        mDestinationPoint = Point.fromLngLat(MetadataViewModel.mLongitude, MetadataViewModel.mLatitude);
        mViewModel.getRoute(mOriginPoint, mDestinationPoint);
        mStartButton.setOnClickListener(v -> {

            NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                    .directionsRoute(mCurrentRoad)
                    .shouldSimulateRoute(true)
                    .build();

            NavigationLauncher.startNavigation(MapBoxActivity.this, options);
        });
    }

    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = mMapBoxMap.getLocationComponent();

            // Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

            // Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();
        } else {
            mPermissionsManager = new PermissionsManager(this);
            mPermissionsManager.requestLocationPermissions(this);
        }
    }

    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);


    }

    private static class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {
        public double originLatitude;
        public double originLongitude;

        private final WeakReference<MapBoxActivity> activityWeakReference;


        LocationChangeListeningActivityLocationCallback(MapBoxActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            MapBoxActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

                // Create a Toast which displays the new location's coordinates
                Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
                        String.valueOf(result.getLastLocation().getLatitude()),
                        String.valueOf(result.getLastLocation().getLongitude())),
                        Toast.LENGTH_SHORT).show();

                originLongitude = result.getLastLocation().getLongitude();
                originLatitude = result.getLastLocation().getLatitude();

                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mMapBoxMap != null && result.getLastLocation() != null) {
                    activity.mMapBoxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }

                activityWeakReference.get().passLatLong(originLatitude, originLongitude);
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can't be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            MapBoxActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GpsUnits.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }

    public void turnOnGpsTumbler() {
        Log.i("1234", "I opened turnOnGpsTumbler method");
        new GpsUnits(this).turnGPSOn(new GpsUnits.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mMapBoxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    public void onPermissionResult(boolean granted) {
//        if (granted) {
//            mViewModel.enableLocationComponent(Objects.requireNonNull(mMapBoxMap.getStyle()));
//        } else {
//            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
//            finish();
//        }
//    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    public void showToast(int textOfToast) {
        Toast.makeText(MapBoxActivity.this, textOfToast, Toast.LENGTH_SHORT).show();

    }
}


