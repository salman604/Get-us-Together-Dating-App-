package com.firedating.chat.Extra;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ServiceClass extends Service {
    private MediaPlayer player;

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
