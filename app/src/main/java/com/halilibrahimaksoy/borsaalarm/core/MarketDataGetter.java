package com.halilibrahimaksoy.borsaalarm.core;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.halilibrahimaksoy.borsaalarm.model.Market;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Random;

import io.realm.Realm;

/**
 * Created by halil ibrahim aksoy on 29.03.2016.
 */
public class MarketDataGetter extends AsyncTask<String, Void, Market> {
    private Context context;

    private Realm realm;

    public MarketDataGetter(Context context) {
        this.context = context;
    }

    @Override
    protected Market doInBackground(String... params) {

        Document document = null;
        try {
            document = Jsoup.connect(params[0]).ignoreContentType(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = document.select("div[id=turkiye");

        Market market = new Market();
        Element divElement = elements.first();

        Elements liElements = divElement.getElementsByTag("li");

        setMarketBIST100Data(liElements.get(0), market);
        setMarketUSDData(liElements.get(1), market);
        setMarketEUROData(liElements.get(2), market);
        setMarketGOLDData(liElements.get(3), market);
        setMarketBRENTData(liElements.get(5), market);


        return market;
    }


    private void setMarketBIST100Data(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String bist100Value = spanElement.text();
        market.setBIST100_VALUE(Float.parseFloat(bist100Value.replace(",", ".")));

        Element bElement = liElement.getElementsByTag("b").first();
        String bistPercantage = bElement.text();
        market.setBIST100_PERCANTAGE(Float.parseFloat(bistPercantage.split("%")[1].replace(",", ".")));

    }

    private void setMarketUSDData(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String usdValue = spanElement.text();
        market.setUSD_VALUE(Float.parseFloat(usdValue.replace(",", ".")));
        Element bElement = liElement.getElementsByTag("b").first();
        String usdPercentage = bElement.text();
        market.setUSD_PERCANTAGE(Float.parseFloat(usdPercentage.split("%")[1].replace(",", ".")));

    }

    private void setMarketEUROData(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String euroValue = spanElement.text();
        market.setEURO_VALUE(Float.parseFloat(euroValue.replace(",", ".")));

        Element bElement = liElement.getElementsByTag("b").first();
        String euroPercentage = bElement.text();
        market.setEURO_PERCANTAGE(Float.parseFloat(euroPercentage.split("%")[1].replace(",", ".")));

    }

    private void setMarketGOLDData(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String goldValue = spanElement.text();
        market.setGOLD_VALUE(Float.parseFloat(goldValue.replace(",", ".")));

        Element bElement = liElement.getElementsByTag("b").first();
        String goldPercentage = bElement.text();
        market.setGOLD_PERCANTAGE(Float.parseFloat(goldPercentage.split("%")[1].replace(",", ".")));

    }

    private void setMarketBRENTData(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String brentValue = spanElement.text();
        market.setBRENT_VALUE(Float.parseFloat(brentValue.replace(",", ".")));

        Element bElement = liElement.getElementsByTag("b").first();
        String brentPercentage = bElement.text();
        market.setBRENT_PERCANTAGE(Float.parseFloat(brentPercentage.split("%")[1].replace(",", ".")));

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Market market) {
        super.onPostExecute(market);
    }


}
