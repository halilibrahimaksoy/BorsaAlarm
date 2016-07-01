package com.halilibrahimaksoy.borsaalarm.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halilibrahimaksoy.borsaalarm.R;
import com.halilibrahimaksoy.borsaalarm.adapter.ViewPagerAdapter;
import com.halilibrahimaksoy.borsaalarm.util.NonSwipeableViewPager;

/**
 * Created by haksoy on 3/22/2016.
 */
public class SpecialExhangeList extends Fragment {

    private TabLayout tblSpecialExchangeList;
    private NonSwipeableViewPager vpgrSpecialExchangeList;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.special_exchange_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tblSpecialExchangeList = (TabLayout) getActivity().findViewById(R.id.tblSpecialExchangeList);
        vpgrSpecialExchangeList = (NonSwipeableViewPager) getActivity().findViewById(R.id.vpgrSpecialExchangeList);

        setupViewPager(vpgrSpecialExchangeList);
        tblSpecialExchangeList.setupWithViewPager(vpgrSpecialExchangeList);

    }

    private void setupViewPager(ViewPager vpgrSpecialExchangeList) {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFrag(new FollowedExhagneList(), "Takip Ettiklerim");
        viewPagerAdapter.addFrag(new AlarmedExchangeList(), "Alarmlar");

        vpgrSpecialExchangeList.setAdapter(viewPagerAdapter);
    }

}
