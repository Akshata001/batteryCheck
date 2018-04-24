package com.akshata.batteryassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.akshata.batteryassistant.TextToSpeechHandler.TTSCompletionListener;
import org.apache.log4j.jmx.LoggerDynamicMBean;

/**
 * Created by IISU36 on 20-04-2018.
 */

public class PowerConnectionReceiver extends BroadcastReceiver {

  private final Context mContext;
  private TextToSpeechHandler textToSpeechHandler;

  public PowerConnectionReceiver(Context mContext) {
    this.mContext = mContext;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
        status == BatteryManager.BATTERY_STATUS_FULL;

    textToSpeechHandler = new TextToSpeechHandler(mContext);

    if (isCharging) {
      int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
      Log.d(PowerConnectionReceiver.class.getSimpleName(),
          "onReceive: batteryPct =" + String.valueOf(level) + "%");

      if (level == 94) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Hey, Akshata")
            .setContentText("Please Remove your charger")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

// notificationId is a unique int for each notification that you must define
        int notificationId = 99999;
        notificationManager.notify(notificationId, mBuilder.build());
        textToSpeechHandler
            .playMessage("Your phone battery is fully charged", new TTSCompletionListener() {
              @Override
              public void onTTSPlatCompleted() {
              }
            });
      }
    }
  }
}
