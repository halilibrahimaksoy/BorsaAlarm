package com.halilibrahimaksoy.borsaalarm.adapter;

import android.content.Context;
import android.support.v4.widget.ExploreByTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.halilibrahimaksoy.borsaalarm.R;
import com.halilibrahimaksoy.borsaalarm.model.Exchange;

import java.util.List;


/**
 * Created by halil ibrahim aksoy on 21.03.2016.
 */
public class ExchangeListArrayAdapter extends ArrayAdapter<Exchange> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Exchange> exchangeList;

    public ExchangeListArrayAdapter(Context context, int resource, List<Exchange> objects) {
        super(context, resource, objects);
        this.context = context;
        exchangeList = objects;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {

            row = layoutInflater.inflate(R.layout.exchange_list_item, parent, false);

            holder = new ViewHolder();
            holder.imgExhangeStatus = (ImageView) row.findViewById(R.id.imgExhangeStatus);
            holder.txtExchangeName = (TextView) row.findViewById(R.id.txtExchangeName);
            holder.txtExhangeValue = (TextView) row.findViewById(R.id.txtExhangeValue);
            holder.txtExhangePercantage = (TextView) row.findViewById(R.id.txtExhangePercantage);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Exchange exchange = exchangeList.get(position);
        holder.txtExchangeName.setText(exchange.getName());
        holder.txtExhangeValue.setText(exchange.getPrice() + "");
        holder.txtExhangePercantage.setText("% "+exchange.getPercentageChange());
        if (exchange.getPercentageChange().contains("-")) {
            holder.txtExhangePercantage.setTextColor(context.getResources().getColor(R.color.redTextColor));
            holder.imgExhangeStatus.setImageResource(R.drawable.ic_down_d32f2f);
        } else if (exchange.getPercentageChange().equals("0,00")) {
            holder.txtExhangePercantage.setTextColor(context.getResources().getColor(R.color.blueTextColor));
            holder.imgExhangeStatus.setImageResource(R.drawable.ic_stabil_0277bd);
        } else {
            holder.txtExhangePercantage.setTextColor(context.getResources().getColor(R.color.greenTextColor));
            holder.imgExhangeStatus.setImageResource(R.drawable.ic_up_2e7d32);
        }

        return row;
    }

    private class ViewHolder {
        private TextView txtExchangeName, txtExhangeValue, txtExhangePercantage;
        private ImageView imgExhangeStatus;
    }
}
