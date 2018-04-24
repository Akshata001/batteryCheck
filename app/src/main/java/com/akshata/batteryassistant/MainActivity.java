package com.akshata.batteryassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

  private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context ctxt, Intent intent) {
      int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
      Log.d(MainActivity.class.getSimpleName(), "onReceive: " + String.valueOf(level) + "%");
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    PowerConnectionReceiver powerConnectionReceiver = new PowerConnectionReceiver(MainActivity.this);

    /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });*/

//    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//    Intent batteryStatus = registerReceiver(null, ifilter);
    this.registerReceiver(powerConnectionReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

   /* int status = 0;
    if (batteryStatus != null) {
      status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
      boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
          status == BatteryManager.BATTERY_STATUS_FULL;
    }
*/

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
