package com.test;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Broadcast extends Activity {

    private int intLevel;
    private int intScale;
    private Button mButton01;
    private String TAG = "Broadcast";

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mButton01 = (Button) findViewById(R.id.myButton1);
        mButton01.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //注册一个系统 BroadcastReceiver，作为访问电池计量之用
                registerReceiver(batInfoReceiver, new IntentFilter(
                        Intent.ACTION_BATTERY_CHANGED));
            }
        });
    }

    private BroadcastReceiver batInfoReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            //如果捕捉到的action是ACTION_BATTERY_CHANGED，
//            //就运行onBatteryInfoReceiver()
//            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
//                intLevel = intent.getIntExtra("level", 0);
//                intScale = intent.getIntExtra("scale", 100);
//                Log.v(TAG,"intLevel"+intLevel);
//                Log.v(TAG,"intScale"+intScale);
//                onBatteryInfoReceiver(intLevel, intScale);
//            }
//        }
            String action = intent.getAction();
            Log.v(TAG, "onReceive");
            int status = intent.getIntExtra("status", 0);
            int health = intent.getIntExtra("health", 0);
            boolean present = intent.getBooleanExtra("present", false);
            int level = intent.getIntExtra("level", 0);
            int scale = intent.getIntExtra("scale", 0);
            int icon_small = intent.getIntExtra("icon-small", 0);
            int plugged = intent.getIntExtra("plugged", 0);
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = intent.getIntExtra("temperature", 0);
            String technology = intent.getStringExtra("technology");
            String statusString = "";
            switch (status) {
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusString = "unknown";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusString = "charging";

                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusString = "discharging";

                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    statusString = "not charging";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    statusString = "full";
                    break;
            }
            String healthString = "";
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    healthString = "unknown";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthString = "good";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthString = "overheat";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthString = "dead";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthString = "voltage";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthString = "unspecified failure";
                    break;
            }
            String acString = "";
            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    acString = "plugged ac";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    acString = "plugged usb";
                    break;
            }
            Log.i("cat", statusString);
            Log.i("cat", healthString);
            Log.i("cat", String.valueOf(present));
            Log.i("cat", String.valueOf(level));
            Log.i("cat", String.valueOf(scale));
            Log.i("cat", String.valueOf(icon_small));
            Log.i("cat", acString);
            Log.i("cat", String.valueOf(voltage));
            Log.i("cat", String.valueOf(temperature));
            Log.i("cat", technology);
            //要看看是不是我们要处理的消息
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                //电池电量，数字
                Log.d("Battery", "" + intent.getIntExtra("level", 0));
                //电池最大容量
                Log.d("Battery", "" + intent.getIntExtra("scale", 0));
                //电池伏数
                Log.d("Battery", "" + intent.getIntExtra("voltage", 0));
                //电池温度
                Log.d("Battery", "" + intent.getIntExtra("temperature", 0));
                //电池状态，返回是一个数字
                // BatteryManager.BATTERY_STATUS_CHARGING 表示是充电状态
                // BatteryManager.BATTERY_STATUS_DISCHARGING 放电中
                // BatteryManager.BATTERY_STATUS_NOT_CHARGING 未充电
                // BatteryManager.BATTERY_STATUS_FULL 电池满
                Log.d("Battery", "ss" + intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_CHARGING));
                //充电类型 BatteryManager.BATTERY_PLUGGED_AC 表示是充电器，不是这个值，表示是 USB
                Log.d("Battery", "" + intent.getIntExtra("plugged", 0));
                //电池健康情况，返回也是一个数字
                //BatteryManager.BATTERY_HEALTH_GOOD 良好
                //BatteryManager.BATTERY_HEALTH_OVERHEAT 过热
                //BatteryManager.BATTERY_HEALTH_DEAD 没电
                //BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE 过电压
                //BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE 未知错误
                Log.d("Battery", "" + intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN));
            }
        }

    };

        //捕捉到ACTION_BATTERY_CHANGED时要运行的method
        public void onBatteryInfoReceiver(int intLevel, int intScale) {
            //create跳出的对话窗口
            final Dialog d = new Dialog(Broadcast.this);
            d.setTitle(R.string.str_dialog_title);
            d.setContentView(R.layout.mydialog);

            //创建一个背景模糊的Window，且将对话窗口放在前景
            Window window = d.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                    WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

            //将取得的电池计量显示于Dialog中
            TextView mTextView02 = (TextView) d.findViewById(R.id.myTextView2);
            mTextView02.setText(getResources().getText(R.string.str_dialog_body)
                    + String.valueOf(intLevel * 100 / intScale) + "%");

            //设置返回主画面的按钮
            Button mButton02 = (Button) d.findViewById(R.id.myButton2);
            mButton02.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    unregisterReceiver(batInfoReceiver);
                    d.dismiss();
                }
            });
            d.show();
        }
    }

