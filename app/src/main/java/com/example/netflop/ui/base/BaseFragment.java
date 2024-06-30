package com.example.netflop.ui.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.netflop.data.models.network.NetworkChangeEvent;
import com.example.netflop.services.network.NetworkManagerHelper;
import com.example.netflop.services.receivers.NetworkReceiver;




public class BaseFragment extends Fragment {


//    private NetworkReceiver networkReceiver = new NetworkReceiver();
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        getActivity().registerReceiver(networkReceiver, filter);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        getActivity().unregisterReceiver(networkReceiver);
//    }
    private NetworkManagerHelper networkManagerHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        networkManagerHelper = new NetworkManagerHelper();
//        networkManagerHelper.registerNetworkCallback(requireContext());
        networkManagerHelper = new NetworkManagerHelper();
        networkManagerHelper.registerNetworkCallback(requireContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        networkManagerHelper.unregisterNetworkCallback(requireContext());
        networkManagerHelper.unregisterNetworkCallback(requireContext());
    }

}