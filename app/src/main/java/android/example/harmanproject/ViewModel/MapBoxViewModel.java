package android.example.harmanproject.ViewModel;

import android.example.harmanproject.R;
import android.example.harmanproject.View.MapBoxActivity;

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

public class MapBoxViewModel {
    private MapBoxActivity mView;


    public MapBoxViewModel(MapBoxActivity view) {
        mView = view;
    }

//    public LocationEngine mLocationEngine;
//    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
//    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
//    private LocationEngineCallback callback = new LocationEngineCallback() {
//        @Override
//        public void onSuccess(Object result) {
//
//        }
//
//        @Override
//        public void onFailure(@NonNull Exception exception) {
//
//        }
//    };

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
        if (PermissionsManager.areLocationPermissionsGranted(mView)) {
            if (PermissionsManager.areLocationPermissionsGranted(mView)) {
                mView.mLocationComponent = mView.mMapBoxMap.getLocationComponent();
                mView.mLocationComponent.activateLocationComponent(mView, loadedMapStyle);
                mView.mLocationComponent.setLocationComponentEnabled(true);
                mView.mLocationComponent.setCameraMode(CameraMode.TRACKING);


// Get an instance of the component
//            LocationComponent locationComponent = mView.mMapboxMap.getLocationComponent();
//
//            // Activate with options
//            locationComponent.activateLocationComponent(
//                    LocationComponentActivationOptions.builder(mView, loadedMapStyle).build());
//
//            // Enable to make component visible
//            locationComponent.setLocationComponentEnabled(true);
//
//            // Set the component's camera mode
//            locationComponent.setCameraMode(CameraMode.TRACKING);
//
//            // Set the component's render mode
//            locationComponent.setRenderMode(RenderMode.COMPASS);
//            mView.mPermissionsManager = new PermissionsManager(mView);
//            mView.mPermissionsManager.requestLocationPermissions(mView);
            }
        }

//    private void initLocationEngine() {
//
//
//
//
//
//        mLocationEngine = LocationEngineProvider.getBestLocationEngine(mView);
//        mLocationEngine.getLastLocation(callback);
//        mLocationEngine = LocationEngineProvider.getBestLocationEngine(mView);
//
//        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
//                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
//                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();
//
//        mLocationEngine.requestLocationUpdates(request, callback, getMainLooper());
//        mLocationEngine.getLastLocation(callback);
//    }

    }
}
