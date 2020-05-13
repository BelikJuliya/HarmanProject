package android.example.harmanproject;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity5 extends AppCompatActivity {
    WifiManager wifiManager;
    WifiInfo connection;
    String display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        TextView textView = findViewById(R.id.wifi_info);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        connection = wifiManager.getConnectionInfo();

        display = "SSID: " + connection.getSSID() +"\nRSSi: " + connection.getRssi() + "\nMac Address: " + connection.getMacAddress();
        textView.setText(display);
    }
}
