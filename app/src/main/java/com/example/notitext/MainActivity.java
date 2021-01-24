package com.example.notitext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edit;
    Button btn,cancel;
    public String msg;
    public static final int FLAG_ALWAYS_SHOW_TICKER = 0x1000000;
    public static final int FLAG_ONLY_UPDATE_TICKER = 0x2000000;
    public NotificationManagerCompat NotiMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotiMgr = NotificationManagerCompat.from(this);
        edit=findViewById(R.id.edit);
        btn=findViewById(R.id.btn);
        cancel=findViewById(R.id.cancel);
        createNotificationChannel("0","NotiChannel",0);
        btn.setOnClickListener(new mClick());
        cancel.setOnClickListener(new mClick());
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
    class mClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn:
                    sendNoti();
                    toast();
                    Log.d("GO","OK");
                    break;
                case R.id.cancel:
                    msg=edit.getText().toString();
                    NotiMgr.cancel(0);
                    Log.d("Noti","取消了一条Noti："+msg);
                    break;
            }}
    }
    void toast(){
        msg=edit.getText().toString();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    void sendNoti(){
        msg=edit.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, "0")
                .setContentTitle("通知")
                .setContentText(msg)
                .setContentIntent(pendingIntent)
                .setTicker(msg)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            notification.extras.putInt("ticker_icon", R.drawable.ic_launcher_foreground);
            notification.extras.putBoolean("ticker_icon_switch", false); }
        notification.flags = notification.flags|(FLAG_ALWAYS_SHOW_TICKER);
        notification.flags = notification.flags|(FLAG_ONLY_UPDATE_TICKER);
        NotiMgr.notify(0, notification);
        Log.d("Noti","发送了一条Noti："+msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotiMgr.cancel(0);
        Log.d("Noti","我走啦，拜拜~还有这条ticker："+msg+"，我也顺带给你带走了喔~");
    }
}