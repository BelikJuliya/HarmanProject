package android.example.harmanproject.View;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.MapBoxViewModel;
import android.example.harmanproject.ViewModel.MetadataViewModel;
import android.example.harmanproject.databinding.MapboxBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;

import org.jetbrains.annotations.NotNull;

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
                mViewModel.enableLocationComponent(style);

                mOriginPoint = Point.fromLngLat(mLocationComponent.getLastKnownLocation().getLongitude(),
                        mLocationComponent.getLastKnownLocation().getLatitude());
//                mOriginPoint = Point.fromLngLat(mViewModel.getCurrentLocation(getApplicationContext()).getLongitude(), mViewModel.getCurrentLocation(getApplicationContext()).getLatitude());
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
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == GpsUtils.GPS_REQUEST) {
//                isGPS = true; // flag maintain before get location
//            }
//        }
//    }


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
            mViewModel.enableLocationComponent(Objects.requireNonNull(mMapBoxMap.getStyle()));
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

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
