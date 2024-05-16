package com.example.netflop.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static   AppExecutors instance;
    public static AppExecutors getInstance(){
        if(instance==null){
            instance=new AppExecutors();
        }
        return  instance;
    }
    private  final ScheduledExecutorService networkIO= Executors.newScheduledThreadPool(3);
    public ScheduledExecutorService getNetworkIO(){
        return networkIO;
    }
//    private static final int THREAD_COUNT = 3;
//
//    private final Executor diskIO;
//
//    private final Executor networkIO;
//
//    private final Executor mainThread;
//
//    public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
//        this.diskIO = diskIO;
//        this.networkIO = networkIO;
//        this.mainThread = mainThread;
//    }

}
