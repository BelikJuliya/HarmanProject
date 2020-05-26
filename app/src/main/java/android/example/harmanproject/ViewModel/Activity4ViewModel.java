package android.example.harmanproject.ViewModel;

import android.example.harmanproject.R;
import android.example.harmanproject.View.Activity4;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
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

public class Activity4ViewModel{
//    private MapView mMapView;
//    private MapboxMap mMapboxMap;
//    private PermissionsManager mPermissionsManager;
//    private LocationComponent mLocationComponent;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute mRoute;
    private DirectionsRoute mCurrentRoad;
    private Point mOriginPoint;
    private Point mDestinationPoint;
    private Button mStartButton;
    private Activity4 view;


    public Activity4ViewModel(Activity4 view) {
        this.view = view;
    }

    public void getRoute(Point origin, Point destination) {

        assert Mapbox.getAccessToken() != null;
        NavigationRoute.builder(view.getApplicationContext())
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
                            view.showToast(R.string.no_routes);
                        }
                        mCurrentRoad = response.body().routes().get(0);

                        if (mRoute != null) {
                            mRoute.removeRoute();
                        } else {
                            mRoute = new NavigationMapRoute(null, view.mMapView, view.mMapboxMap);
                        }
                        mRoute.addRoute(mCurrentRoad);
                    }

                    @Override
                    public void onFailure(@NotNull Call<DirectionsResponse> call, Throwable t) {
                        Timber.e("Error: %s", t.getMessage());
                    }
                });
    }


    public void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(view)) {
            view.mLocationComponent = view.mMapboxMap.getLocationComponent();
            view.mLocationComponent.activateLocationComponent(view, loadedMapStyle);
            view.mLocationComponent.setLocationComponentEnabled(true);
            view.mLocationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            view.mPermissionsManager = new PermissionsManager(view);
            view.mPermissionsManager.requestLocationPermissions(view);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        mPermissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    public void onExplanationNeeded(List<String> permissionsToExplain) {
//        view.showToast(R.string.user_location_permission_explanation);
//    }
//
//    @Override
//    public void onPermissionResult(boolean granted) {
//        if (granted) {
//            enableLocationComponent(Objects.requireNonNull(mMapboxMap.getStyle()));
//        } else {
//            view.showToast(R.string.user_location_permission_not_granted);
//            finish();
//        }
//    }


}
