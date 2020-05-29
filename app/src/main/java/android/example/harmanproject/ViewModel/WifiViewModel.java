package android.example.harmanproject.ViewModel;

import android.content.Context;
import android.example.harmanproject.View.WifiInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

public class WifiViewModel {
    private WifiInfo mView;

    public WifiViewModel(WifiInfo view) {
        mView = view;
    }
    public String findWifiInfo() {

        WifiManager wifiManager = (WifiManager) mView.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        android.net.wifi.WifiInfo connection = wifiManager.getConnectionInfo();

        String display = "SSID: " + connection.getSSID() + "\nRSSi: " + connection.getRssi() + "\nMac Address: " + connection.getMacAddress() + "\nIP: " + connection.getIpAddress();



        List<WifiConfiguration> configuredList = wifiManager.getConfiguredNetworks();
        List<String> ssidList = new ArrayList<>();

        for (
                WifiConfiguration config : configuredList) {
            ssidList.add(config.SSID);
        }
        return display;
    }
}
