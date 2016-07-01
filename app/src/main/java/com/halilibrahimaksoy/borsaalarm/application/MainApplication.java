package com.halilibrahimaksoy.borsaalarm.application;

import android.app.Application;

import com.halilibrahimaksoy.borsaalarm.receiver.AlarmReceiver;

/**
 * Created by haksoy on 4/26/2016.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.setAlarm(getApplicationContext());

    }
}
