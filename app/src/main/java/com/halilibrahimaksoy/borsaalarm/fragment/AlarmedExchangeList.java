package com.halilibrahimaksoy.borsaalarm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.halilibrahimaksoy.borsaalarm.R;
import com.halilibrahimaksoy.borsaalarm.adapter.AlarmedExchangeArrayAdapter;
import com.halilibrahimaksoy.borsaalarm.core.RealmDb;
import com.halilibrahimaksoy.borsaalarm.model.AlarmExchange;
import com.halilibrahimaksoy.borsaalarm.model.FollowExchange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by haksoy on 3/22/2016.
 */
public class AlarmedExchangeList extends Fragment {

    private SwipeMenuListView lsvAlarmedExchangeList;
    private AlarmedExchangeArrayAdapter alarmedExchangeArrayAdapter;
    private List<AlarmExchange> alarmExchangeList;
    private RealmDb realmDb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarmed_exchange_fragment, container, false);
        lsvAlarmedExchangeList = (SwipeMenuListView) view.findViewById(R.id.lsvAlarmedExchangeList);

        alarmExchangeList = new ArrayList<>();

        lsvAlarmedExchangeList.setMenuCreator(generateSwipeMenuCreator());
        lsvAlarmedExchangeList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        realmDb = new RealmDb(getActivity().getApplicationContext());
        alarmedExchangeListGenerate();


        lsvAlarmedExchangeList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    addFollowListItem(position);
                } else if (index == 1) {
                    rollAlarmListItem(position);
                }
                return false;
            }
        });

        return view;
    }

    private void rollAlarmListItem(int position) {
        realmDb.deleteAlarmItem(alarmExchangeList.get(position).getName());
        alarmedExchangeListGenerate();
        alarmedExchangeArrayAdapter.notifyDataSetChanged();
    }

    private void addFollowListItem(int position) {

        FollowExchange followExchange = new FollowExchange();
        followExchange.setName(alarmExchangeList.get(position).getName());
        followExchange.setCreateDate(new Date());
        realmDb.addFollow(followExchange);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        alarmedExchangeArrayAdapter = new AlarmedExchangeArrayAdapter(getContext(), 0, alarmExchangeList);
        lsvAlarmedExchangeList.setAdapter(alarmedExchangeArrayAdapter);
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
                addAlarmList.setTitle("Alarmı Sil");
                addAlarmList.setTitleSize(18);
                addAlarmList.setTitleColor(getResources().getColor(R.color.alarmTitleColor));

                menu.addMenuItem(addAlarmList);

            }
        };
        return creator;
    }


    private void alarmedExchangeListGenerate() {
        alarmExchangeList = realmDb.getAlarmExchangeList();

    }

}
