package com.example.playandroid.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
    //内部静态类构造
    private static class ThreadPoolHolder{
        private static final ExecutorService threadPool = new ThreadPoolExecutor(6,
                10,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public  ThreadPoolUtil(){

    }

    public static ExecutorService getInstance(){
        return ThreadPoolHolder.threadPool;
    }

}
