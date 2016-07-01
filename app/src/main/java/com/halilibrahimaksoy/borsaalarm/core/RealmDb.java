package com.halilibrahimaksoy.borsaalarm.core;

import android.content.Context;
import android.widget.Toast;

import com.halilibrahimaksoy.borsaalarm.model.AlarmExchange;
import com.halilibrahimaksoy.borsaalarm.model.FollowExchange;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by haksoy on 4/20/2016.
 */
public class RealmDb {

    private Context context;
    Realm realm;

    public RealmDb(Context context) {
        this.context = context;
        realm = Realm.getInstance(context);
    }

    public void addFollow(FollowExchange followExchange) {

        try {
            realm.beginTransaction();
            FollowExchange saveFollowExchange = realm.createObject(FollowExchange.class);
            saveFollowExchange.setName(followExchange.getName());
            saveFollowExchange.setCreateDate(followExchange.getCreateDate());

            realm.commitTransaction();
            Toast.makeText(context, saveFollowExchange.getName() + " takip edliliyor..", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(context, "Bir Hata Oluştu !", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void addAlarm(AlarmExchange alarmExchange) {
        try {
            realm.beginTransaction();
            AlarmExchange saveAlarmExchange = realm.createObject(AlarmExchange.class);

            saveAlarmExchange.setName(alarmExchange.getName());
            saveAlarmExchange.setMinPrice(alarmExchange.getMinPrice());
            saveAlarmExchange.setPrice(alarmExchange.getPrice());
            saveAlarmExchange.setMaxPrice(alarmExchange.getMaxPrice());

            realm.commitTransaction();
            Toast.makeText(context, saveAlarmExchange.getName() + " alarma eklendi ..", Toast.LENGTH_SHORT).show();
        } catch (RealmException ex) {
            Toast.makeText(context, "Bir Hata Oluştu !", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public List<FollowExchange> getFollowExchangeList() {
        RealmResults<FollowExchange> followExchanges = realm.where(FollowExchange.class).findAll();
        return followExchanges;
    }

    public List<AlarmExchange> getAlarmExchangeList() {
        RealmResults<AlarmExchange> alarmExchanges = realm.where(AlarmExchange.class).findAll();
        return alarmExchanges;
    }

    public void deleteAllFollowElements() {

        RealmResults<FollowExchange> followExchanges = realm.where(FollowExchange.class).findAll();
        realm.beginTransaction();
        for (FollowExchange follow : followExchanges) {
            followExchanges.removeLast();
        }
        realm.commitTransaction();


    }

    public void deleteAllAlarmElements() {
        RealmResults<AlarmExchange> alarmExchanges = realm.where(AlarmExchange.class).findAll();
        realm.beginTransaction();
        for (AlarmExchange alarm : alarmExchanges) {
            alarmExchanges.removeLast();
        }
        realm.commitTransaction();

    }

    public void deleteFollowItem(String name) {
        realm.beginTransaction();
        RealmResults<FollowExchange> followExchanges = realm.where(FollowExchange.class).equalTo("name", name).findAll();
        followExchanges.removeLast();
        realm.commitTransaction();
    }

    public void deleteAlarmItem(String name) {
        realm.beginTransaction();
        RealmResults<AlarmExchange> alarmExchanges = realm.where(AlarmExchange.class).equalTo("name", name).findAll();
        alarmExchanges.removeLast();
        realm.commitTransaction();
    }
}
