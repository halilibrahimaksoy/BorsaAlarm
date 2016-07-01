package com.halilibrahimaksoy.borsaalarm.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.halilibrahimaksoy.borsaalarm.R;
import com.halilibrahimaksoy.borsaalarm.adapter.ExchangeListArrayAdapter;
import com.halilibrahimaksoy.borsaalarm.core.RealmDb;
import com.halilibrahimaksoy.borsaalarm.model.AlarmExchange;
import com.halilibrahimaksoy.borsaalarm.model.AlarmHolder;
import com.halilibrahimaksoy.borsaalarm.model.Exchange;
import com.halilibrahimaksoy.borsaalarm.model.FollowExchange;
import com.halilibrahimaksoy.borsaalarm.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.ExecutionException;

/**
 * Created by halil ibrahim aksoy on 20.03.2016.
 */
public class ExchangeList extends Fragment {

    private SwipeMenuListView lsvExchangeList;
    private ExchangeListArrayAdapter exchangeListArrayAdapter;
    private List<Exchange> exchangeList;
    private String categoryID;
    private AlertDialog alertDialog;
    private RealmDb realmDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_list, container, false);
        lsvExchangeList = (SwipeMenuListView) view.findViewById(R.id.lsvExchangeList);
        exchangeList = new ArrayList<>();
        categoryID = getArguments().getString("SelectedCategory");
        exchangeListGenerate();

        lsvExchangeList.setMenuCreator(generateSwipeMenuCreator());
        lsvExchangeList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        realmDb = new RealmDb(getActivity().getApplicationContext());
        lsvExchangeList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    addFollowListItem(position);
                } else if (index == 1) {
                    addAlarmListItem(position);
                }
                return false;
            }
        });
        exchangeListArrayAdapter = new ExchangeListArrayAdapter(getContext(), 0, exchangeList);
        lsvExchangeList.setAdapter(exchangeListArrayAdapter);

        lsvExchangeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Exchange exchange = exchangeList.get(position);
                showAlertDialog(exchange);

                return false;
            }
        });
        lsvExchangeList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP && alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                return false;
            }
        });


        Log.d("onCreateView", "ExchangeList onCreateView called");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if(savedInstanceState!=null)


        exchangeListArrayAdapter.notifyDataSetChanged();
        Log.d("onActivityCreated", "ExchangeList onActivityCreated called");
    }


    private void addFollowListItem(int position) {

        FollowExchange followExchange = new FollowExchange();
        followExchange.setName(exchangeList.get(position).getName());
        followExchange.setCreateDate(new Date());
        realmDb.addFollow(followExchange);

    }


    private void addAlarmListItem(int position) {


        generateAddAlarmPopUp(position);
    }

    private void generateAddAlarmPopUp(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getAddAlarmView(position);
        builder.setView(view);
        builder.setPositiveButton("Alarm Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlarmHolder alarmHolder = (AlarmHolder) view.getTag();
                Float minValue = Float.parseFloat(alarmHolder.getEdtAlarmMinValue().getText().toString());
                Float maxValue = Float.parseFloat(alarmHolder.getEdtAlarmMaxValue().getText().toString());
                if (minValue != null || maxValue != null) {

                    if (!(maxValue < minValue)) {
                        AlarmExchange alarmExchange = new AlarmExchange();
                        alarmExchange.setName(exchangeList.get(position).getName());
                        alarmExchange.setPrice(exchangeList.get(position).getPrice());

                        alarmExchange.setMinPrice(minValue);
                        alarmExchange.setMaxPrice(maxValue);
                        alarmExchange.setCreateDate(new Date());
//                        addExchangeAlarm(alarmExchange);
                        realmDb.addAlarm(alarmExchange);
                    } else {
                        Toast.makeText(getContext(), "Minimum değer maximum değerden büyük olamaz !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Minimum veya maximum değeri giriniz !", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("İptal ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.exchange_list_item_shape);

        dialog.show();

        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.YELLOW);


    }


    private View getAddAlarmView(int position) {

        View holder = LayoutInflater.from(getContext()).inflate(R.layout.add_alarm_exchange_popup, null);

        AlarmHolder alarmHolder = new AlarmHolder();
        alarmHolder.setEdtAlarmMinValue((EditText) holder.findViewById(R.id.edtAlarmMinValue));
        alarmHolder.setEdtAlarmMaxValue((EditText) holder.findViewById(R.id.edtAlarmMaxValue));
        alarmHolder.setTxtAlarmActiveValue((TextView) holder.findViewById(R.id.txtAlarmActiveValue));
        alarmHolder.setTxtAlarmCompanyName((TextView) holder.findViewById(R.id.txtAlarmCompanyName));

        alarmHolder.getTxtAlarmCompanyName().setText(exchangeList.get(position).getName());
        alarmHolder.getTxtAlarmActiveValue().setText(exchangeList.get(position).getPrice() + "");

        holder.setTag(alarmHolder);
        return holder;

    }

    private SwipeMenuCreator generateSwipeMenuCreator() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem addFollowList = new SwipeMenuItem(getContext());
                addFollowList.setBackground(R.drawable.exchange_swipe_menu_shape);
                addFollowList.setWidth(300);
                addFollowList.setTitle("Takip Et");
                addFollowList.setTitleSize(18);
                addFollowList.setTitleColor(getResources().getColor(R.color.alarmTitleColor));

                menu.addMenuItem(addFollowList);

                SwipeMenuItem addAlarmList = new SwipeMenuItem(getContext());
                addAlarmList.setBackground(R.drawable.exchange_swipe_menu_shape);
                addAlarmList.setWidth(300);
                addAlarmList.setTitle("Alarm Ekle");
                addAlarmList.setTitleSize(18);
                addAlarmList.setTitleColor(getResources().getColor(R.color.alarmTitleColor));

                menu.addMenuItem(addAlarmList);

            }
        };
        return creator;
    }

    private void exchangeListGenerate() {
        new ExchangeListGetter(getActivity()).execute(categoryID);
    }

    private void showAlertDialog(Exchange activeExchange) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View holder = layoutInflater.inflate(R.layout.exchange_details_popup, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(holder);

        final TextView txtCompanyName = (TextView) holder.findViewById(R.id.txtCompanyName);
        final TextView txtPriceValue = (TextView) holder.findViewById(R.id.txtPriceValue);
        final TextView txtPurchasePriceValue = (TextView) holder.findViewById(R.id.txtPurchasePriceValue);
        final TextView txtSalePriceValue = (TextView) holder.findViewById(R.id.txtSalePriceValue);
        final TextView txtPercantageValue = (TextView) holder.findViewById(R.id.txtPercantageValue);


        txtCompanyName.setText(activeExchange.getName());
        txtPriceValue.setText(activeExchange.getPrice() + "");
        txtSalePriceValue.setText(activeExchange.getSalePrice() + "");
        txtPurchasePriceValue.setText(activeExchange.getPurchasePrice() + "");
        txtPercantageValue.setText(activeExchange.getPercentageChange());
        if (activeExchange.getPercentageChange().contains("-")) {
            txtPercantageValue.setTextColor(getContext().getResources().getColor(R.color.redTextColor));
        } else if (activeExchange.getPercentageChange().equals("0,00")) {
            txtPercantageValue.setTextColor(getContext().getResources().getColor(R.color.blueTextColor));
        } else {
            txtPercantageValue.setTextColor(getContext().getResources().getColor(R.color.greenTextColor));
        }

        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.exchange_list_item_shape);
        alertDialog.show();
    }


    public class ExchangeListGetter extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;

        long start;

        public ExchangeListGetter(FragmentActivity context) {
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            exchangeListArrayAdapter.notifyDataSetChanged();
            Log.e("ExchangeGetter", (System.currentTimeMillis() - start) + " miliseconds ");
        }


        @Override
        protected Void doInBackground(String... params) {
            Document document = null;
            try {
                document = Jsoup.connect(Constants.ExchangeListURL).ignoreContentType(true).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements elements = document.getElementsByAttributeValueContaining("class", params[0]);
            for (Element exhanges : elements) {
                Exchange exchange = new Exchange();
                exchange.setName(exhanges.attr("data-id"));
                exchange.setPercentageChange(exhanges.attr("data-change"));
                Elements elementsAttributes = exhanges.getElementsByTag("small");
                exchange.setPrice(Float.parseFloat(elementsAttributes.get(0).text().replace(",", ".")));
                exchange.setPurchasePrice(Float.parseFloat(elementsAttributes.get(1).text().replace(",", ".")));
                exchange.setSalePrice(Float.parseFloat(elementsAttributes.get(2).text().replace(",", ".")));
                exchangeList.add(exchange);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            start = System.currentTimeMillis();
            progressDialog.setMessage("Yükleniyor ...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

    }


}
