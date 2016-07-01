//package com.halilibrahimaksoy.borsaalarm.core;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.support.v4.app.FragmentActivity;
//
//import com.halilibrahimaksoy.borsaalarm.util.Constants;
//import com.halilibrahimaksoy.borsaalarm.model.Exchange;
//import com.halilibrahimaksoy.borsaalarm.model.FollowExchange;
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
//public class FollowedExchangeListGetter extends AsyncTask<List<FollowExchange>, Void, List<Exchange>> {
//
//    private ProgressDialog progressDialog;
//
//    public FollowedExchangeListGetter(FragmentActivity context) {
//
//        progressDialog = new ProgressDialog(context);
//    }
//
//    @Override
//    protected List<Exchange> doInBackground(List<FollowExchange>... params) {
//
//
//        ArrayList<Exchange> responseList = new ArrayList<>();
//
//        Document document = null;
//        try {
//            document = Jsoup.connect(Constants.ExchangeListURL).ignoreContentType(true).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (FollowExchange followExchange : params[0]) {
//
//            Elements elements = document.getElementsByAttributeValue("data-id", followExchange.getName());
//            Element exhanges = elements.get(0);
//            Exchange exchange = new Exchange();
//            exchange.setName(exhanges.attr("data-id"));
//            exchange.setPercentageChange(exhanges.attr("data-change"));
//            Elements elementsAttributes = exhanges.getElementsByTag("small");
//            exchange.setPrice(Float.parseFloat(elementsAttributes.get(0).text().replace(",", ".")));
//            exchange.setPurchasePrice(Float.parseFloat(elementsAttributes.get(1).text().replace(",", ".")));
//            exchange.setSalePrice(Float.parseFloat(elementsAttributes.get(2).text().replace(",", ".")));
//            responseList.add(exchange);
//
//        }
//        return responseList;
//
//    }
//}
