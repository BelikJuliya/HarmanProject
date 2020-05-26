package android.example.harmanproject.ViewModel;

import android.content.Context;
import android.example.harmanproject.View.Activity5;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

public class Activity5ViewModel {
    private  Activity5 view;

    public Activity5ViewModel (Activity5 view) {
        this.view = view;
    }
    public String findWifiInfo() {

        WifiManager wifiManager = (WifiManager) view.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo connection = wifiManager.getConnectionInfo();

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
