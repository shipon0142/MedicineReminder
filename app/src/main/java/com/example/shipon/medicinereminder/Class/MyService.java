package com.example.shipon.medicinereminder.Class;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.example.shipon.medicinereminder.activity.MainActivity;
import com.example.shipon.medicinereminder.activity.SplashActivity;

/**
 * Created by Shipon on 8/29/2018.
 */

public class MyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        YourTask(intent, flags);

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //    Intent iuu = new Intent(this, MainActivity.class);
        //   startActivity(iuu);

        Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);


    }

    private void YourTask(final Intent intent, final int ff) {
        //showNotification();


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
