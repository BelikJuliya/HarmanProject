package android.example.harmanproject.ViewModel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.example.harmanproject.R;
import android.example.harmanproject.View.Gps;
import android.example.harmanproject.View.MapBoxActivity;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

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
        if (PermissionsManager.areLocationPermissionsGranted(mView)) {
            //goToSettings();

            LocationManager locationManager = (LocationManager) mView.getSystemService(LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(mView.getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
                mView.mLocationComponent = mView.mMapBoxMap.getLocationComponent();
                mView.mLocationComponent.activateLocationComponent(mView, loadedMapStyle);
                mView.mLocationComponent.setLocationComponentEnabled(true);
                mView.mLocationComponent.setCameraMode(CameraMode.TRACKING);
            } else {
                Toast.makeText(mView.getApplicationContext(), "Turn on gps", Toast.LENGTH_LONG).show();
            }
            //checkForGps();
            //turnGPSOn();
//            mView.mLocationComponent = mView.mMapBoxMap.getLocationComponent();
//            mView.mLocationComponent.activateLocationComponent(mView, loadedMapStyle);
//            mView.mLocationComponent.setLocationComponentEnabled(true);
//            mView.mLocationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            mView.mPermissionsManager = new PermissionsManager(mView);
            mView.mPermissionsManager.requestLocationPermissions(mView);
        }
    }

    private void turnGPSOn() {
        String provider = Settings.Secure.getString(mView.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            mView.sendBroadcast(poke);
        }
    }

    private void goToSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mView.startActivity(intent);
    }

    public Location getCurrentLocation(Context context) {
        Gps gps = new Gps(context.getApplicationContext());
        Location currentLocation = gps.getLocation();
        return currentLocation;
    }

    private void checkForGps() {
        final LocationManager manager = (LocationManager) mView.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mView);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        mView.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

//    private void enableLoc() {
//
//        if (googleApiClient == null) {
//            googleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                        @Override                        public void onConnected(Bundle bundle) {
//
//                        }
//
//                        @Override                        public void onConnectionSuspended(int i) {
//                            googleApiClient.connect();
//                        }
//                    })
//                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
//                        @Override                        public void onConnectionFailed(ConnectionResult connectionResult) {
//
//                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
//                        }
//                    }).build();
//            googleApiClient.connect();
//
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(30 * 1000);
//            locationRequest.setFastestInterval(5 * 1000);
//            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                    .addLocationRequest(locationRequest);
//
//            builder.setAlwaysShow(true);
//
//            PendingResult<LocationSettingsResult> result =
//                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//                @Override                public void onResult(LocationSettingsResult result) {
//                    final Status status = result.getStatus();
//                    switch (status.getStatusCode()) {
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                            try {
//                                // Show the dialog by calling startResolutionForResult(),                                // and check the result in onActivityResult().                                status.startResolutionForResult(MainActivity.this, REQUEST_LOCATION);
//                            } catch (IntentSender.SendIntentException e) {
//                                // Ignore the error.                            }
//                                break;
//                            }
//                    }
//                });
//            }
//
//        }
}

