package com.example.shipon.medicinereminder.Class;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.shipon.medicinereminder.R;


public class Alert extends Activity {
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

    }

    @Override

    public void onDestroy() {

        super.onDestroy();

        mp.release();

    }

}
