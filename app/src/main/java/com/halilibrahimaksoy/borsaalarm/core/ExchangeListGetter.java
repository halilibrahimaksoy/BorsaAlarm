//package com.halilibrahimaksoy.borsaalarm.core;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//
//import com.halilibrahimaksoy.borsaalarm.util.Constants;
//import com.halilibrahimaksoy.borsaalarm.model.Exchange;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
///**
// * Created by halil ibrahim aksoy on 20.03.2016.
// */
//
//public class ExchangeListGetter extends AsyncTask<String, Void, ArrayList<Exchange>> {
//
//    //  private Context context;
//    ProgressDialog progressDialog;
//
//    long start;
//
//    public ExchangeListGetter(FragmentActivity context) {
//        progressDialog = new ProgressDialog(context);
//    }
//
//
//
//    @Override
//    protected void onPostExecute(ArrayList<Exchange> strings) {
//        super.onPostExecute(strings);
//        progressDialog.dismiss();
//
//        Log.e("ExchangeGetter", (System.currentTimeMillis() - start) + " miliseconds ");
//    }
//
//
//    @Override
//    protected ArrayList<Exchange> doInBackground(String... params) {
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
//        Elements elements = document.getElementsByAttributeValueContaining("class", params[0]);
//        for (Element exhanges : elements) {
//            Exchange exchange = new Exchange();
//            exchange.setName(exhanges.attr("data-id"));
//            exchange.setPercentageChange(exhanges.attr("data-change"));
//            Elements elementsAttributes = exhanges.getElementsByTag("small");
//            exchange.setPrice(Float.parseFloat(elementsAttributes.get(0).text().replace(",", ".")));
//            exchange.setPurchasePrice(Float.parseFloat(elementsAttributes.get(1).text().replace(",", ".")));
//            exchange.setSalePrice(Float.parseFloat(elementsAttributes.get(2).text().replace(",", ".")));
//            responseList.add(exchange);
//        }
//        return responseList;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        start = System.currentTimeMillis();
//        progressDialog.setMessage("Yükleniyor ...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//    }
//
//}
