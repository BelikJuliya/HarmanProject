package android.example.harmanproject.View;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.WifiViewModel;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WifiInfo extends AppCompatActivity {
    private WifiViewModel mViewModel;

    public WifiInfo(){
        mViewModel = new WifiViewModel(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_info);

        TextView textView = findViewById(R.id.wifi_info);
        textView.setText(mViewModel.findWifiInfo());



    }
}
