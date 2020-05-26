package android.example.harmanproject.View;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.Activity3ViewModel;
import android.example.harmanproject.ViewModel.Activity4ViewModel;
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


public class Activity4 extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {
    public MapView mMapView;
    public MapboxMap mMapboxMap;
    public PermissionsManager mPermissionsManager;
    public LocationComponent mLocationComponent;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute mRoute;
    public DirectionsRoute mCurrentRoad;
    private Point mOriginPoint;
    private Point mDestinationPoint;
    private Button mStartButton;
    private Activity4ViewModel viewModel;

    public Activity4 (){
        viewModel = new Activity4ViewModel(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN));
        setContentView(R.layout.activity_4);

        mMapView = findViewById(R.id.mapView);
        mStartButton = findViewById(R.id.start_btn);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mMapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                viewModel.enableLocationComponent(style);
                assert mLocationComponent.getLastKnownLocation() != null;
                mOriginPoint = Point.fromLngLat(mLocationComponent.getLastKnownLocation().getLongitude(),
                        mLocationComponent.getLastKnownLocation().getLatitude());
                mDestinationPoint = Point.fromLngLat(Activity3ViewModel.mLongitude, Activity3ViewModel.mLatitude);
                viewModel.getRoute(mOriginPoint, mDestinationPoint);

                mStartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(mCurrentRoad)
                                .shouldSimulateRoute(true)
                                .build();

                        NavigationLauncher.startNavigation(Activity4.this, options);
                    }
                });
            }
        });

    }

//    private void getRoute(Point origin, Point destination) {
//        assert Mapbox.getAccessToken() != null;
//        NavigationRoute.builder(getApplicationContext())
//                .accessToken(Mapbox.getAccessToken())
//                .origin(origin)
//                .destination(destination)
//                .build()
//                .getRoute(new Callback<DirectionsResponse>() {
//                    @Override
//                    public void onResponse(@NotNull Call<DirectionsResponse> call, @NotNull Response<DirectionsResponse> response) {
//                        if (response.body() == null) {
//                            Timber.e("No routes found, check right user and access token");
//                            return;
//                        } else if (response.body().routes().size() == 0) {
//                            Timber.e("No routes found");
//                            Toast.makeText(Activity4.this, "There is no routes to this place", Toast.LENGTH_SHORT).show();
//                        }
//                        mCurrentRoad = response.body().routes().get(0);
//
//                        if (mRoute != null) {
//                            mRoute.removeRoute();
//                        } else {
//                            mRoute = new NavigationMapRoute(null, mMapView, mMapboxMap);
//                        }
//                        mRoute.addRoute(mCurrentRoad);
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<DirectionsResponse> call, Throwable t) {
//                        Timber.e("Error: %s", t.getMessage());
//                    }
//                });
//    }


//    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
//        if (PermissionsManager.areLocationPermissionsGranted(this)) {
//            mLocationComponent = mMapboxMap.getLocationComponent();
//            mLocationComponent.activateLocationComponent(this, loadedMapStyle);
//            mLocationComponent.setLocationComponentEnabled(true);
//            mLocationComponent.setCameraMode(CameraMode.TRACKING);
//        } else {
//            mPermissionsManager = new PermissionsManager(this);
//            mPermissionsManager.requestLocationPermissions(this);
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
            viewModel.enableLocationComponent(Objects.requireNonNull(mMapboxMap.getStyle()));
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

    public void showToast(int textOfToast){
        Toast.makeText(Activity4.this, textOfToast, Toast.LENGTH_SHORT).show();

    }
}
