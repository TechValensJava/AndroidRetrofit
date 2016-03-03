package com.tv.androidretrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkAvailablity {

    public static boolean checkNetworkStatus(Context context) {
        boolean HaveConnectedWifi = false;
        boolean HaveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Network[] networks = cm.getAllNetworks();
                    NetworkInfo networkInfo;
                    Network network;
                    for (int i = 0; i < networks.length; i++) {
                        network = networks[i];
                        networkInfo = cm.getNetworkInfo(network);
                        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI &&
                                networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                            HaveConnectedWifi = true;
                        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                            HaveConnectedMobile = true;
                        }
                    }
                }else{
                    //noinspection deprecation
                    NetworkInfo[] info = cm.getAllNetworkInfo();
                    for (NetworkInfo ni : info) {
                        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                            if (ni.isConnected())
                                HaveConnectedWifi = true;
                        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                            if (ni.isConnected())
                                HaveConnectedMobile = true;
                    }
                }
            }
        } catch (Exception ex) {

        }
        return HaveConnectedWifi || HaveConnectedMobile;
    }
}