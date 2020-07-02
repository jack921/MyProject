package com.amico.im.myproject;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("");
    }

    @Override
    public void onCreate() {
        System.out.println("onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        System.out.println("onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("onDestroy");
        super.onDestroy();
    }

    //这个是IntentService的核心方法,它是通过串行来处理任务的,也就是一个一个来处理
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("工作线程是: "+Thread.currentThread().getName());
        String task = intent.getStringExtra("task");
        System.out.println("任务是 :"+task);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
