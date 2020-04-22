package android.example.harmanproject;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity4 extends AppCompatActivity {
    private MapView mapView;
    private MapboxMap map;
    private Button startButton;
    private LocationEngine locationEngine;
    private LocationComponent locationComponent;
    private Location originLocation;
    private NavigationMapRoute route;
    private final String TAG = "Activity 4";
    private Point originPosition;
    private Point destinationPosition;
    private PermissionsManager permissionsManager;
    private Marker destinationMarker;

    Point origin = Point.fromLngLat(-77.03613, 38.90992);
    Point destination = Point.fromLngLat(-77.0365, 38.8977);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_4);


        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.MAPBOX_STREETS,
                style -> {

                    // Map is set up and the style has loaded. Now you can add data or make other map adjustments


                }));

    }

//    @Override
//    public void onStyleLoaded(@NonNull Style style) {
//        locationComponent.enableLocationComponent();
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }

    //public void onMapClick

//    @Override
//    @SuppressWarnings("MissingPermission")
//    public void onConnected(){
//        LocationEngine.re
//    }

    private void getRoute(Point origin, Point destination){
        NavigationRoute.builder(getApplicationContext())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() != null){
                            Log.e(TAG, "No routes found, check right user and access token");
                            return;
                        } else if (response.body().routes().size() == 0){
                            Log.e(TAG, "No routes found");
                        }
                        DirectionsRoute currentRoad = response.body().routes().get(0);

                        if (route != null){
                            route.removeRoute();
                        } else {
                            route = new NavigationMapRoute(null, mapView, map);
                        }
                        route.addRoute(currentRoad);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Log.e(TAG, "Error: " + t.getMessage());
                    }
                });
    }
}
