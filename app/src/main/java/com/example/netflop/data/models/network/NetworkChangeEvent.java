package com.example.netflop.data.models.network;

public class NetworkChangeEvent {
    private boolean isConnected;

    public NetworkChangeEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
