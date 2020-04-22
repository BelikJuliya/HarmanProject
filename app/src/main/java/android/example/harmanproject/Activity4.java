package android.example.harmanproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class Activity4 extends AppCompatActivity  {
    private static final long DEFAULT_MAX_WAIT_TIME = 5000;
    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 3000;
    private MapView mapView;
    private Mapbox mapbox;
    private MapboxMap mapboxMap;
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

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap -> {


            // Map is set up and the style has loaded. Now you can add data or make other map adjustments
            mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
                Intent intent = getIntent();
                ExampleElement exampleElement = intent.getParcelableExtra("Example element");
                Bitmap icon = BitmapFactory.decodeFile(exampleElement.getPath());
                style.addImage("my-marker-image", icon);

                SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                symbolLayer.setProperties(PropertyFactory.iconImage("my-marker-image"),
                        iconOffset(new Float[] {(float) -77.03613, (float) 38.90992}));

                style.addLayer(symbolLayer);
            });

        });

    }


}