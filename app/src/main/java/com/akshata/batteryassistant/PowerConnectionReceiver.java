package com.akshata.batteryassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import org.apache.log4j.jmx.LoggerDynamicMBean;

/**
 * Created by IISU36 on 20-04-2018.
 */

public class PowerConnectionReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
        status == BatteryManager.BATTERY_STATUS_FULL;

    int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//    boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
//    boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
    if (isCharging) {
      int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
      int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
      float batteryPct = level / (float) scale;
      Log.d(PowerConnectionReceiver.class.getSimpleName(), "onReceive: batteryPct =" + batteryPct);
    }
  }
}
