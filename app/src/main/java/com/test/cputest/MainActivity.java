package com.test.cputest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    long start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        try {
            threadTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void startTime() {
        start = System.currentTimeMillis();
    }

    void test() {

        int number = 3;
//        long count = 1000000000;
        long count = 4000;
        int c2 = 0;
        int c3 = 0;
        while(count > 0){

            boolean b = false;
            for(int j = 2; j<number; j++) {
                if(number%j == 0) {
                    b = true;
                }
            }
            if(!b) {
//                Log.i("!!!", "" + number);
                count --;
                c2++;
                if(c2 == 500) {
                    c2 = 0;
                    c3++;
//                    Log.i("!!!", "block " + c3);
                }
            }
            number ++;
        }
    }

    String getTime() {
        long end = System.currentTimeMillis();
        long dif = end-start;
        dif /= 10;
        String d = dif+"";
        String result = d.substring(0, d.length()-2) + "." + d.substring(d.length()-2);
        return result;
    }

    public void threadTest() throws Exception {

        startTime();

        class MyRunnable implements Runnable {
            @Override
            public void run() {
                Log.i("!!!", Thread.currentThread().getName() + " start");
                test();
                Log.i("!!!", Thread.currentThread().getName() + " finish: " + getTime());
            }
        }

        final int NUMBER_OF_THREADS = 20;
        List<Thread> threadList = new ArrayList<>(NUMBER_OF_THREADS);
        for (int i=1; i<=NUMBER_OF_THREADS; i++) {
            Thread t = new Thread(new MyRunnable());

            t.setPriority(Thread.MIN_PRIORITY);
            t.setName("T-MIN");
            //
            threadList.add(t);
        }

        for (Thread t : threadList) {
            t.start();
        }

        for (Thread t : threadList) {
            t.join();
        }
    }
}
