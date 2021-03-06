package com.lengjiye.code;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.code.lengjiye.mvp.BasicMvpActivity;
import com.code.lengjiye.mvp.presenter.MvpPresenter;
import com.lengjiye.code.activity.AutoTestActivity;
import com.lengjiye.code.activity.DeviceInformationActivity;
import com.lengjiye.code.activity.NotificationActivity;
import com.lengjiye.code.activity.RxJava2TestActivity;
import com.lengjiye.code.activity.WebpAnimationActivity;
import com.lengjiye.code.mvptest.MVPTestActivity;

/**
 * @author liuzhuo
 */
public class MainActivity extends BasicMvpActivity {

    @Override
    public MvpPresenter createPresenter() {
        return null;
    }

    @Override
    public boolean isAlived() {
        return false;
    }

    @Override
    public int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setListener() {
        super.setListener();
        setOnClickListener(findViewById(R.id.button), findViewById(R.id.button1),
                findViewById(R.id.button2), findViewById(R.id.button3),
                findViewById(R.id.button4), findViewById(R.id.button5));
        setNotificationChannel();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(MainActivity.this, DeviceInformationActivity.class));
                break;

            case R.id.button1:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                break;

            case R.id.button2:
                startActivity(new Intent(MainActivity.this, AutoTestActivity.class));
                break;

            case R.id.button3:
                startActivity(new Intent(MainActivity.this, WebpAnimationActivity.class));
                break;

            case R.id.button4:
                startActivity(new Intent(MainActivity.this, MVPTestActivity.class));
                break;

            case R.id.button5:
                startActivity(new Intent(MainActivity.this, RxJava2TestActivity.class));
                break;

            default:
                break;
        }

    }

    /**
     * 设置通知渠道分类
     */
    private void setNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}
