package com.example.netflop.services.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.widget.Toast;

import com.example.netflop.data.data_source.local_data_source.NetworkDataSource;
import com.example.netflop.data.models.network.NetworkChangeEvent;


public class NetworkManagerHelper {
    private ConnectivityManager.NetworkCallback networkCallback;

    public void registerNetworkCallback(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Initialize NetworkDataSource with current network status
        boolean isConnected = isNetworkConnected(context);
        NetworkDataSource networkDataSource = new NetworkDataSource(context);
        networkDataSource.setState(isConnected);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                boolean isConnected = true;
                NetworkDataSource networkDataSource=new NetworkDataSource(context);
                boolean wasConnected=networkDataSource.getState();
                if(wasConnected!=isConnected){
                    networkDataSource.setState(isConnected);
                    showNetworkToast(context,isConnected);
                }


//                Toast.makeText(context, "Your internet connection was restored.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                boolean isConnected = false;
                NetworkDataSource networkDataSource=new NetworkDataSource(context);
                boolean wasConnected=networkDataSource.getState();
                if(wasConnected!=isConnected){
                    networkDataSource.setState(isConnected);
                    showNetworkToast(context,isConnected);
                }

//                Toast.makeText(context, "No internet connection, please try to connect Internet connection", Toast.LENGTH_SHORT).show();
            }
        };

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

    public void unregisterNetworkCallback(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }
    private void showNetworkToast(Context context, boolean isConnected) {
        if (isConnected) {
            Toast.makeText(context, "Your internet connection was restored.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "No internet connection, please try to connect Internet connection", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = cm.getActiveNetwork();
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }
}
