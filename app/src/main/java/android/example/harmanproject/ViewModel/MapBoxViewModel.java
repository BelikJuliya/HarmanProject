package android.example.harmanproject.ViewModel;

import android.content.Intent;
import android.example.harmanproject.R;

import android.example.harmanproject.View.MapBoxActivity;

import android.location.LocationManager;

import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.content.Context.LOCATION_SERVICE;

public class MapBoxViewModel {
    private MapBoxActivity mView;
    private boolean isGpsOn = false;

    public MapBoxViewModel(MapBoxActivity view) {
        mView = view;
    }

    public void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(mView.getApplicationContext())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<DirectionsResponse> call, @NotNull Response<DirectionsResponse> response) {
                        if (response.body() == null) {
                            Timber.e("No routes found, check right user and access token");
                            return;
                        } else if (response.body().routes().size() == 0) {
                            Timber.e("No routes found");
                            mView.showToast(R.string.no_routes);
                        }
                        mView.mCurrentRoad = response.body().routes().get(0);

                        if (mView.mRoute != null) {
                            mView.mRoute.removeRoute();
                        } else {
                            mView.mRoute = new NavigationMapRoute(null, mView.mMapView, mView.mMapBoxMap);
                        }
                        mView.mRoute.addRoute(mView.mCurrentRoad);
                    }

                    @Override
                    public void onFailure(@NotNull Call<DirectionsResponse> call, Throwable t) {
                        Timber.e("Error: %s", t.getMessage());
                    }
                });
    }


    public void enableLocationComponent(@NonNull Style loadedMapStyle) {
        Log.i("Repeating action", "I am enabling location");
        if (PermissionsManager.areLocationPermissionsGranted(mView)) {
            LocationManager locationManager = (LocationManager) mView.getSystemService(LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(mView.getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
                mView.mLocationComponent = mView.mMapBoxMap.getLocationComponent();
                mView.mLocationComponent.activateLocationComponent(mView, loadedMapStyle);
                mView.mLocationComponent.setLocationComponentEnabled(true);
                mView.mLocationComponent.setCameraMode(CameraMode.TRACKING);
            } else {
                Toast.makeText(mView.getApplicationContext(), "Turn on gps", Toast.LENGTH_LONG).show();
                goToSettings();
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    isGpsOn = true;
                }
                if (isGpsOn) {
                    enableLocationComponent(loadedMapStyle);
                }
            }
        } else {
            mView.mPermissionsManager = new PermissionsManager(mView);
            mView.mPermissionsManager.requestLocationPermissions(mView);
        }
    }

    private void goToSettings() {
        Log.i("Repeating action", "I am going to settings");
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mView.startActivity(intent);
    }

//    public Location getCurrentLocation(Context context) {
//        Gps gps = new Gps(context.getApplicationContext());
//        Location currentLocation = gps.getLocation();
//        return currentLocation;
//    }


}

