package com.ycexample.ycapp.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ycexample.ycapp.R;
import com.ycexample.ycapp.RunnableData.ScanCodeRunnable;
import com.ycexample.ycapp.adapter.ScanCodeAdapter;
import com.ycexample.ycapp.entry.ScanCode;
import com.ycexample.ycapp.tools.AlertDialogCallBack;
import com.ycexample.ycapp.tools.DialogUtils;
import com.ychmi.sdk.YcApi;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 16486 on 2020/9/2.
 */

public class ScanCodeActivity extends Activity {

    @BindView(R.id.et_current_scan_code)
    TextView etCurrentScanCode;                 //当前扫描的条码
    @BindView(R.id.iv_current_status_color)
    ImageView ivCurrentStatusColor;             //当前操作的状态
    @BindView(R.id.tv_scan_code_count)
    TextView tvScanCodeCount;                   //当前扫描的总数
    @BindView(R.id.lv_scan_code_list)
    ListView lvScanCodeList;                    //当前扫描数据的集合
    @BindView(R.id.iv_serial_port_status)
    ImageView ivSerialPortStatus;               //当前232串口连接状态
    @BindView(R.id.btn_stop_fail)
    Button btnStopFail;                         //异常报警停止按钮
    @BindView(R.id.btn_clear_data)
    Button btnClearData;                        //清除数据按钮

    YcApi ycApi = new YcApi();

    FileDescriptor fp = null;


    private String currentSuccessStr;           //记录第一次扫描到的条码

    ScanCodeRunnable runnable;


    boolean isStop = true;

    ScanCodeAdapter adapter;

    List<ScanCode> list = new ArrayList<>();

    //判断硬件led是亮红色还是绿色  true为绿色  false为红色
    boolean isErrorStatus=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);

        if (Build.MODEL.equals("rk3288")) {
            fp = ycApi.openCom("dev/ttyS3", 115200, 8, 0, 1);
        } else if (Build.MODEL.equals("AOSP on real_v1_2")) {
            fp = ycApi.openCom("dev/ttyAMA3", 115200, 8, 0, 1);
        }


        initUI();

        if (fp != null) {
            currentSuccessStr="";
            ivSerialPortStatus.setBackgroundColor(ContextCompat.getColor(this,R.color.success));
            runnable = new ScanCodeRunnable(fp, handler);
            runnable.start();
        } else {
            ivSerialPortStatus.setBackgroundColor(ContextCompat.getColor(this,R.color.fail));
        }

        gpioStatushandler.postDelayed(gpioStatusRandler,0);
    }

    private void initUI() {
        adapter = new ScanCodeAdapter(this, list);
        lvScanCodeList.setAdapter(adapter);


    }


    /***
     *
     * @param isStopOrClear true:停止报警  false:停止报警并且清除数据
     */
    public void stopOrClear(boolean isStopOrClear) {
        setUpdateResult(true,"");
        if (!isStopOrClear) {
            list.clear();
            etCurrentScanCode.setText("");
            adapter.notifyDataSetChanged();
            updateCurrentCountTitle();
        }
    }

    public void updateCurrentCountTitle(){

        tvScanCodeCount.setText(list.size()+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(runnable!=null){
            runnable.setIsRead(false);
        }

    }

    Handler  handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String scanCode = bundle.getString("scanCode");

            boolean isEsit = true;
                for (ScanCode scans : list) {
                    if (!scanCode.equals(scans.getCode())) {
                        isEsit = false;

                        break;
                    }
                }
            if (isEsit && !scanCode.trim().equals("NG") && isStop) {
                ScanCode scan = new ScanCode((list.size()+1), scanCode);
                list.add(scan);
                adapter.notifyDataSetChanged();
              updateCurrentCountTitle();
                if (list.size()==1) {
                    currentSuccessStr = scanCode;
                    etCurrentScanCode.setText(currentSuccessStr);
                }
                setUpdateResult(true,"");
            } else {


                setUpdateResult(false,scanCode);
            }


        }
    };

    //更新当前的报警状态

    /***
     *
     * @param result true;停止报警  false开启报警
     */
    public void setUpdateResult(boolean result,String resultCode) {

    //    isStop = result;
        if (!result && isStop) {
        //    runnable.setIsRead(false);
            runnable.pauseThread();
            isStop = result;
            ivCurrentStatusColor.setBackgroundColor(ContextCompat.getColor(this,R.color.fail));
                DialogUtils.showAlertDialog(this, new AlertDialogCallBack() {
                    @Override
                    public void alertDialogFunction() {
                        isStop=true;
                        runnable.resumeThread();
                    }
                },null,resultCode.trim().equals("NG")?"未扫描到条码":"扫描到异常标签:"+resultCode,null,null,true,false);


            isErrorStatus=false;
            gpioStatushandler.postDelayed(gpioStatusRandler,0);
            stopHandler.postDelayed(stopRunnable, 0);
        } else {
            if(result) {
                isStop=true;
                isErrorStatus = true;
                ivCurrentStatusColor.setBackgroundColor(ContextCompat.getColor(this, R.color.success));
                gpioStatushandler.postDelayed(gpioStatusRandler,0);
            }
        }

    }

    Handler gpioStatushandler = new Handler();
    //开灯和灭灯
    Runnable gpioStatusRandler  = new Runnable() {
        @Override
        public void run() {
            if(isErrorStatus )  {
                //GPIO 1亮灯  GPIO 2灭灯 亮绿灯
                ycApi.SetIO(0,1);
                ycApi.SetIO(1,2);
            }else
            {
                //GPIO 1灭灯  GPIO 2亮灯 亮红灯
                ycApi.SetIO(1,1);
                ycApi.SetIO(0,2);
            }
        }
    };

    Handler stopHandler = new Handler();
    int i = 0;
    Runnable stopRunnable = new Runnable() {
        @Override
        public void run() {
            if (i < 6 && !isStop) {
                if (i % 2 == 0) {
                    ycApi.SetIO(1, 0);
                } else {
                    ycApi.SetIO(0, 0);
                }
                i++;
                stopHandler.postDelayed(this, 500);
            } else {
                isStop=true;
                i = 0;
                ycApi.SetIO(1, 0);
            }
        }
    };


    @OnClick({R.id.btn_stop_fail, R.id.btn_clear_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_stop_fail:

                stopOrClear(true);
                break;
            case R.id.btn_clear_data:

                stopOrClear(false);
                break;
        }
    }
}
