package com.example.netflop.services.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {
    private ConnectivityManager.NetworkCallback networkCallback;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
//                Toast.makeText(context, "Your internet connection was restored.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLost(Network network) {
//                Toast.makeText(context, "No internet connection, please try to connect Internet connection", Toast.LENGTH_SHORT).show();
            }
        };

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }
}