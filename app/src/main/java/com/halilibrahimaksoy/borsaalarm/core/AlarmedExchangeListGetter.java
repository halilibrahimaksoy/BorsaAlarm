//package com.halilibrahimaksoy.borsaalarm.core;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.support.v4.app.FragmentActivity;
//
//import com.halilibrahimaksoy.borsaalarm.model.AlarmExchange;
//import com.halilibrahimaksoy.borsaalarm.model.Exchange;
//import com.halilibrahimaksoy.borsaalarm.model.FollowExchange;
//import com.halilibrahimaksoy.borsaalarm.util.Constants;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by haksoy on 4/18/2016.
// */
//public class AlarmedExchangeListGetter extends AsyncTask<List<AlarmExchange>, Void, List<Exchange>> {
//    private List<Exchange> exchangeList;
//
//    @Override
//    protected List<Exchange> doInBackground(List<AlarmExchange>... params) {
//        exchangeList = new ArrayList<>();
//        Document document = null;
//        try {
//            document = Jsoup.connect(Constants.ExchangeListURL).ignoreContentType(true).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (AlarmExchange alarmExchange : params[0]) {
//            Elements elements = document.getElementsByAttributeValue("data-id", alarmExchange.getName());
//            Element element = elements.get(0);
//            Exchange exchange = new Exchange();
//            exchange.setName(element.attr("data-id"));
//            exchange.setPercentageChange(element.attr("data-change"));
//            Elements elementsAttributes = element.getElementsByTag("small");
//            exchange.setPrice(Float.parseFloat(elementsAttributes.get(0).text().replace(",", ".")));
//            exchange.setPurchasePrice(Float.parseFloat(elementsAttributes.get(1).text().replace(",", ".")));
//            exchange.setSalePrice(Float.parseFloat(elementsAttributes.get(2).text().replace(",", ".")));
//            exchangeList.add(exchange);
//
//        }
//        return null;
//    }
//
//
//}