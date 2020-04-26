package android.example.harmanproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Activity4 extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {
    private MapView mapView;
    private MapboxMap mapboxMap;
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;


    // variables for calculating and drawing a route
    private static final String TAG = "DirectionsActivity";
    // variables needed to initialize navigation
    private NavigationMapRoute route;
    private DirectionsRoute currentRoad;
    private Point originPoint;
    private Point destinationPoint;
    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN));
        mapView = findViewById(R.id.mapView);
        startButton = findViewById(R.id.start_btn);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
                originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                        locationComponent.getLastKnownLocation().getLatitude());
                destinationPoint = Point.fromLngLat(Activity3.longitude, Activity3.latitude);
                getRoute(originPoint, destinationPoint);

                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoad)
                                .shouldSimulateRoute(true)
                                .build();

                        NavigationLauncher.startNavigation(Activity4.this, options);
                    }
                });

            }
        });
    }



            private void getRoute(Point origin, Point destination) {
                NavigationRoute.builder(getApplicationContext())
                        .accessToken(Mapbox.getAccessToken())
                        .origin(origin)
                        .destination(destination)
                        .build()
                        .getRoute(new Callback<DirectionsResponse>() {
                            @Override
                            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                                if (response.body() == null) {
                                    Log.e(TAG, "No routes found, check right user and access token");
                                    return;
                                } else if (response.body().routes().size() == 0) {
                                    Log.e(TAG, "No routes found");
                                }
                                currentRoad = response.body().routes().get(0);

                                if (route != null) {
                                    route.removeRoute();
                                } else {
                                    route = new NavigationMapRoute(null, mapView, mapboxMap);
                                }
                                route.addRoute(currentRoad);
                            }

                            @Override
                            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                                Log.e(TAG, "Error: " + t.getMessage());
                            }
                        });
            }


            private void enableLocationComponent(@NonNull Style loadedMapStyle) {
                // Check if permissions are enabled and if not request
                if (PermissionsManager.areLocationPermissionsGranted(this)) {
                    // Activate the MapboxMap LocationComponent to show user location
                    // Adding in LocationComponentOptions is also an optional parameter
                    locationComponent = mapboxMap.getLocationComponent();
                    locationComponent.activateLocationComponent(this, loadedMapStyle);
                    locationComponent.setLocationComponentEnabled(true);
                    // Set the component's camera mode
                    locationComponent.setCameraMode(CameraMode.TRACKING);
                } else {
                    permissionsManager = new PermissionsManager(this);
                    permissionsManager.requestLocationPermissions(this);
                }
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }

            @Override
            public void onExplanationNeeded(List<String> permissionsToExplain) {
                Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionResult(boolean granted) {
                if (granted) {
                    enableLocationComponent(Objects.requireNonNull(mapboxMap.getStyle()));
                } else {
                    Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public boolean onMapClick(@NonNull LatLng point) {
                return false;
            }
        }
