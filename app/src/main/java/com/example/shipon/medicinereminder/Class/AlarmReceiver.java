package com.example.shipon.medicinereminder.Class;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.shipon.medicinereminder.R;

/**
 * Created by Shipon on 8/29/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Intent x = new Intent(context, Alert.class);
        //  x.putExtra(context.getString(R.string.titttle), Title);
        // x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //  context.startActivity(x);
        // intent = new Intent(context, MyService.class);
        //  context.startService(intent);
        String Title = "title";
        Uri alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone ringtoneAlarm = RingtoneManager.getRingtone(context, alarmTone);
        ringtoneAlarm.play();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        String msg = "Time to take a medicine";
        builder.setMessage(msg).setCancelable(
                false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ringtoneAlarm.stop();
                        // ringtoneAlarm.stop();
                        //    onDestroy();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }

    private void showNotification(Context context) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("Notification!")
                .setContentText("Alarm Received")
                .setSound(soundUri)
                .setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
