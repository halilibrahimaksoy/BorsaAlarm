package com.halilibrahimaksoy.borsaalarm.core;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.halilibrahimaksoy.borsaalarm.MainActivity;
import com.halilibrahimaksoy.borsaalarm.R;
import com.halilibrahimaksoy.borsaalarm.model.AlarmExchange;
import com.halilibrahimaksoy.borsaalarm.model.Exchange;
import com.halilibrahimaksoy.borsaalarm.util.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code AlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class AlarmExhangeControlService extends IntentService {

    public AlarmExhangeControlService() {
        super("AlarmExhangeControlService");
    }

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private RealmDb realmDb;

    private List<Exchange> actualExchangeList;
    private List<AlarmExchange> alarmExchangeList;

    @Override
    protected void onHandleIntent(Intent intent) {

        realmDb = new RealmDb(getApplicationContext());
        alarmExchangeList = realmDb.getAlarmExchangeList();
        getActualExchangeList();


        Log.e("Alarm", "Alarm is runn");
    }

    private void getActualExchangeList() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                new AlarmedExchangeListGetter().execute(alarmExchangeList);

            }
        }).start();

    }

    private void AlarmExchangeListControl() {
        for (AlarmExchange alarmExchange : alarmExchangeList) {
            for (Exchange actualExchange : actualExchangeList) {
                if (alarmExchange.getName().equals(actualExchange.getName())) {

                    if (alarmExchange.getMaxPrice() < actualExchange.getPrice())
                        sendNotification(actualExchange);
                    else if (alarmExchange.getMinPrice() > actualExchange.getPrice())
                        sendNotification(actualExchange);


                }
            }
        }
    }

    // Post a notification indicating whether a doodle was found.
    private void sendNotification(Exchange exchange) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(exchange.getName())
                        .setStyle(new NotificationCompat.InboxStyle().setBigContentTitle("Title"))
                        .setContentText(exchange.getPrice() + "TL");


        mBuilder.setContentIntent(contentIntent);
        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        mBuilder.setVibrate(new long[]{500});
        mNotificationManager.notify(exchange.getName().hashCode(), mBuilder.build());

    }

    private class AlarmedExchangeListGetter extends AsyncTask<List<AlarmExchange>, Void, Void> {

        @Override
        protected Void doInBackground(List<AlarmExchange>... params) {
            actualExchangeList = new ArrayList<>();
            Document document = null;
            try {
                document = Jsoup.connect(Constants.ExchangeListURL).ignoreContentType(true).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (AlarmExchange alarmExchange : params[0]) {
                Elements elements = document.getElementsByAttributeValue("data-id", alarmExchange.getName());
                Element element = elements.get(0);
                Exchange exchange = new Exchange();
                exchange.setName(element.attr("data-id"));
                exchange.setPercentageChange(element.attr("data-change"));
                Elements elementsAttributes = element.getElementsByTag("small");
                exchange.setPrice(Float.parseFloat(elementsAttributes.get(0).text().replace(",", ".")));
                exchange.setPurchasePrice(Float.parseFloat(elementsAttributes.get(1).text().replace(",", ".")));
                exchange.setSalePrice(Float.parseFloat(elementsAttributes.get(2).text().replace(",", ".")));
                actualExchangeList.add(exchange);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AlarmExchangeListControl();
        }

    }

}
