package android.example.harmanproject.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.MetadataViewModel;
import android.example.harmanproject.databinding.GoogleMapBinding;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback {

    com.google.android.gms.maps.GoogleMap mapAPI;
    SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleMapBinding binding = GoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        mapAPI = googleMap;
        LatLng pictureLocation = new LatLng(MetadataViewModel.mLatitude, MetadataViewModel.mLongitude);
        mapAPI.addMarker(new MarkerOptions().position(pictureLocation).title("Cat"));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(pictureLocation));
    }
}
