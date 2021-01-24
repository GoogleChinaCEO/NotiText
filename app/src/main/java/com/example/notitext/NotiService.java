package com.example.notitext;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Time;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

public class NotiService extends Service {
    private static final int FLAG_ALWAYS_SHOW_TICKER = 0x1000000;
    private static final int FLAG_ONLY_UPDATE_TICKER = 0x2000000;
    private NotificationManagerCompat NotiMgr;
    private int year,month,day,hour,min,sec;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        NotiMgr = NotificationManagerCompat.from(this);
        createNotificationChannel("noti_channel_srv","NotiChannelSrv",3);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        return super.onStartCommand(intent,flags,startId);
    }
    private String createNotificationChannel(String channelID, String channelNAME, int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
