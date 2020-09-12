package com.ycexample.ycapp.RunnableData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 16486 on 2020/9/4.
 */

public class ScanCodeRunnable extends Thread {

    Handler handler  =  new Handler();

    FileDescriptor fp;
    BufferedReader br;
  //  boolean isStartReadData=true;

    boolean isRead =true;

    public ScanCodeRunnable(FileDescriptor fp ,Handler handler) {
        this.handler = handler;
        this.fp=fp;
        isRead=true;

      br  = new BufferedReader( new InputStreamReader(  new FileInputStream( fp)));
    }


   public  void  setIsRead(boolean isRead){
       this.isRead=isRead;
   }


    public ScanCodeRunnable() {
    }

//
//    public void setStartReadData(boolean startReadData) {
//        isStartReadData = startReadData;
//    }

  /*  @Override
    public void run() {

     BufferedReader br = new BufferedReader( new InputStreamReader(  new FileInputStream( fp)));


           try {
               while (isRead) {
                  String result="";
               if(!"".equals(result = br.readLine())){
                   Message msg = handler.obtainMessage();
                   Bundle bundle = new Bundle();
                   bundle.putBoolean("isStartData",isStartReadData);
                   bundle.putString("scanCode",result);
                   Log.d("readData", "run: "+result);
                   if(isStartReadData){
                       isStartReadData=false;
                   }
                   msg.setData(bundle);
                   handler.sendMessage(msg);
               }
               }
           } catch (IOException e) {
               e.printStackTrace();
           }


    }
*/

    //无意义
    private final Object lock = new Object();

    //标志线程阻塞情况
    private boolean pause = false;

    /**
     * 设置线程是否阻塞
     */
    public void pauseThread() {
        this.pause = true;
    }

    /**
     * 调用该方法实现恢复线程的运行
     */
    public void resumeThread() {


        this.pause = false;
    /*    synchronized (lock) {
            //唤醒线程
            lock.notify();
        }*/
    }

    /**
     * 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     */
    void onPause() {
        synchronized (lock) {
            try {
                //线程 等待/阻塞
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        //标志线程开启
    //    isWait = true;
        //一直循环
        while (isRead) {
          /*  if (pause) {

                //线程 阻塞/等待
                onPause();
            }*/
            try {
                 //程序每50毫秒执行一次 值可更改
            //    Thread.sleep(50);
                //这里写你的代码 你的代码  你的代码  重要的事情说三遍
                String result="";
                if(!"".equals(result = br.readLine())) {
                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                 //   bundle.putBoolean("isStartData", isStartReadData);
                    bundle.putString("scanCode", result);
                    Log.d("readData", "run: " + result);
                  /*  if (isStartReadData) {
                        isStartReadData = false;
                    }*/
                    msg.setData(bundle);
                   if(!pause)
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }


}
