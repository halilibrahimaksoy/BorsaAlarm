package com.halilibrahimaksoy.borsaalarm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.halilibrahimaksoy.borsaalarm.R;
import com.halilibrahimaksoy.borsaalarm.model.AlarmExchange;

import java.util.List;

/**
 * Created by haksoy on 4/18/2016.
 */
public class AlarmedExchangeArrayAdapter extends ArrayAdapter<AlarmExchange> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<AlarmExchange> alarmedExchangeList;

    public AlarmedExchangeArrayAdapter(Context context, int resource, List<AlarmExchange> objects) {
        super(context, resource, objects);
        this.context = context;
        alarmedExchangeList = objects;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {

            row = layoutInflater.inflate(R.layout.alarmed_exchange_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtAlarmedExchangeName = (TextView) row.findViewById(R.id.txtAlarmedExchangeName);
            holder.txtAlarmedExchangeMinValue = (TextView) row.findViewById(R.id.txtAlarmedExchangeMinValue);
            holder.txtAlarmedExchangeMaxValue = (TextView) row.findViewById(R.id.txtAlarmedExchangeMaxValue);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        AlarmExchange alarmedExchange = alarmedExchangeList.get(position);
        holder.txtAlarmedExchangeName.setText(alarmedExchange.getName());
        holder.txtAlarmedExchangeMinValue.setText(alarmedExchange.getMinPrice() + "");
        holder.txtAlarmedExchangeMaxValue.setText(alarmedExchange.getMaxPrice() + "");

        return row;
    }

    private class ViewHolder {
        private TextView txtAlarmedExchangeName, txtAlarmedExchangeMinValue, txtAlarmedExchangeMaxValue;

    }
}
