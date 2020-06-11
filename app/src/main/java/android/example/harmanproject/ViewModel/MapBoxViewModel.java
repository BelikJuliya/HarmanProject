package android.example.harmanproject.ViewModel;

import android.example.harmanproject.R;
import android.example.harmanproject.View.MapBoxActivity;
import android.util.Log;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MapBoxViewModel {
    private MapBoxActivity mView;
    private final String TAG = "GPS Tumbler";

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
                            Log.i("MapBox", "No routes found");
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

}






