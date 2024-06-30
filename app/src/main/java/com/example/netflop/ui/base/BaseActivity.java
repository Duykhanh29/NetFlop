package com.example.netflop.ui.base;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.widget.Toast;

import com.example.netflop.R;
import com.example.netflop.services.network.NetworkManagerHelper;
import com.example.netflop.services.receivers.NetworkReceiver;

public class BaseActivity extends AppCompatActivity {


//    private NetworkReceiver networkReceiver = new NetworkReceiver();
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Đăng ký receiver khi Activity ở foreground
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(networkReceiver, filter);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        // Hủy đăng ký receiver khi Activity không ở foreground
//        unregisterReceiver(networkReceiver);
//    }

    private NetworkManagerHelper networkManagerHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkManagerHelper = new NetworkManagerHelper();
        networkManagerHelper.registerNetworkCallback(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkManagerHelper.unregisterNetworkCallback(this);
    }
}