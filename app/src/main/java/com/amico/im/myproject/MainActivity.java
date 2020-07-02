package com.amico.im.myproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity extends Activity {
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    TextView button;
    TextView testTv;
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_list);
        testTv = findViewById(R.id.test_tv);
        button = findViewById(R.id.button);
        mainView = LayoutInflater.from(this).inflate(R.layout.layout_header_view, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(1, 0);
        recyclerView.setViewCacheExtension(new MyViewCacheExtension());
        recyclerView.setAdapter(mainAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testHandler();
            }
        });

        new MyAsyncTask("jack").execute("");

        testHandler();
    }

    public void testHandler() {
        Toast.makeText(this,getPackageName(),Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(this, MyIntentService.class);
//        intent.putExtra("task", "播放音乐");
//        startService(intent);

//        Intent intent2 = new Intent(this,MyIntentService.class);
//        intent.putExtra("task", "播放音乐2");
//        startService(intent);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {

//                try {
//                    Thread.sleep(3000);
//                    testTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

//                Looper.prepare();
//                Toast.makeText(MainActivity.this, "不会崩溃啦！", Toast.LENGTH_SHORT).show();
//                Looper.loop();

//                Looper.prepare();
//                Handler handler=new Handler(){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        String test=(String)msg.obj;
//                        testTv.setText(test+":"+Thread.currentThread()+":"+(Looper.myLooper() == Looper.getMainLooper()));
//                        Log.e("currentThread",Thread.currentThread()+"");
//
//                        testTv.setText("HelloJack");
//                        testTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                        Toast.makeText(getApplicationContext(), "handler-msg", Toast.LENGTH_LONG).show();
//                    }
//                };
//                Message message=Message.obtain();
//                message.obj="helloJack";
//                handler.sendMessage(message);
//                Looper.loop();
//
//            }
//        }).start();
    }

    public void test() {
//        ThreadTest threadTest=new ThreadTest();
//        new Thread(threadTest).start();
        ThreadTest ctt = new ThreadTest();
        FutureTask<Integer> ft = new FutureTask<Integer>(ctt) {
            @Override
            protected void done() {
                try {
                    int a = get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                super.done();
            }
        };
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量i的值" + i);
            if (i == 20) {
                new Thread(ft, "有返回值的线程").start();
            }
        }
        try {


            System.out.println("子线程的返回值：" + ft.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //实现自定义缓存ViewCacheExtension
    class MyViewCacheExtension extends RecyclerView.ViewCacheExtension {
        @Nullable
        @Override
        public View getViewForPositionAndType(@NonNull RecyclerView.Recycler recycler, int position, int viewType) {
            Log.e("getViewForPosition", position + ":" + viewType);
            if (viewType == 1) {
                return mainAdapter.cacheView.get(position);
            } else {
                return null;
            }
        }
    }

}

class MyAsyncTask extends AsyncTask<String, Integer, String> {
    private String mName = "AsyncTask";

    public MyAsyncTask(String name) {
        super();
        mName = name;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mName;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.e("test", result + "execute finish at " + df.format(new Date()));
    }


    public String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}


