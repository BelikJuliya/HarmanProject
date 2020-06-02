package android.example.harmanproject.View;

import android.example.harmanproject.ViewModel.WifiViewModel;
import android.example.harmanproject.databinding.WifiInfoBinding;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WifiInfoActivity extends AppCompatActivity {
    private WifiViewModel mViewModel;

    public WifiInfoActivity() {
        mViewModel = new WifiViewModel(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WifiInfoBinding binding = WifiInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setTitle("Wifi info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.wifiInfoText.setText(mViewModel.findWifiInfo());
    }
}
