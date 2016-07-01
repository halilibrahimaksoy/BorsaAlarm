package com.halilibrahimaksoy.borsaalarm;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.halilibrahimaksoy.borsaalarm.core.MarketDataGetter;
import com.halilibrahimaksoy.borsaalarm.core.RealmDb;
import com.halilibrahimaksoy.borsaalarm.fragment.ExchangeList;
import com.halilibrahimaksoy.borsaalarm.fragment.SpecialExhangeList;
import com.halilibrahimaksoy.borsaalarm.model.ExchangeCategory;
import com.halilibrahimaksoy.borsaalarm.model.Market;
import com.halilibrahimaksoy.borsaalarm.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private ListView lsvMenu;
    private Toolbar tbMain;
    private List<String> exchangeCategoryList;
    private ArrayAdapter<String> arrayAdapter;
    private Market market;

    private RealmDb realmDb;
    private ExchangeList exchangeListFragment;
    private SpecialExhangeList specialExhangeListFragment;
    private View menuHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbMain = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(tbMain);
        ExchangeCategory.createCategoty();
        setMenuList();
        setMenuDrawer();
        setMenuHeader();
        createFragment();


        lsvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                drawerLayout.closeDrawer(lsvMenu);
                if (--position >= 0) selectedItem(position);
            }
        });


        selectedItem(1);

//        Realm realm = Realm.getInstance(MainActivity.this);
//        realm.addChangeListener(new RealmChangeListener() {
//            @Override
//            public void onChange() {
//                Log.d("Realm", "Bir Kayıt Eklendi");
//            }
//        });


//        realmDb = new RealmDb(getApplicationContext());
//        realmDb.deleteAllAlarmElements();
//        realmDb.deleteAllFollowElements();


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setMarketData();
                        setHeaderData((ViewHeader) menuHeader.getTag());
                        Log.d("Timer", "Menu Header Refreshed");
                    }
                });

            }
        }, Constants.MarketDateRefreshTime, Constants.MarketDateRefreshTime);

    }

    private void createFragment() {
        exchangeListFragment = new ExchangeList();
        specialExhangeListFragment = new SpecialExhangeList();
    }


    private void setMenuDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, tbMain, R.string.drawerOpen, R.string.drawerClose) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void setMenuList() {
        lsvMenu = (ListView) findViewById(R.id.lsvMenu);

        exchangeCategoryList = new ArrayList<>(ExchangeCategory.getExchangeCategoryList().values());
        arrayAdapter = new ArrayAdapter<>(this, R.layout.main_drawer_item, R.id.txtExchangeCategoryName, exchangeCategoryList);

        lsvMenu.setAdapter(arrayAdapter);
    }

    private void setMenuHeader() {
        setMarketData();
        menuHeader = createHeaderView();
        lsvMenu.addHeaderView(menuHeader);
    }

    private void setMarketData() {

        try {
            market = new MarketDataGetter(getApplicationContext()).execute(Constants.MainPageURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private View createHeaderView() {
        View header = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_drawer_header, null);

        ViewHeader viewHeader = new ViewHeader();

        viewHeader.txtMarketUSDValue = (TextView) header.findViewById(R.id.txtMarketUSDValue);
        viewHeader.txtMarketUSDPercantage = (TextView) header.findViewById(R.id.txtMarketUSDPercantage);
        viewHeader.txtMarketEUROValue = (TextView) header.findViewById(R.id.txtMarketEUROValue);
        viewHeader.txtMarketEUROPercantage = (TextView) header.findViewById(R.id.txtMarketEUROPercantage);
        viewHeader.txtMarketGOLDValue = (TextView) header.findViewById(R.id.txtMarketGOLDValue);
        viewHeader.txtMarketGOLDPercantage = (TextView) header.findViewById(R.id.txtMarketGOLDPercantage);
        viewHeader.txtMarketBRENTValue = (TextView) header.findViewById(R.id.txtMarketBRENTValue);
        viewHeader.txtMarketBRENTPercantage = (TextView) header.findViewById(R.id.txtMarketBRENTPercantage);
        viewHeader.txtMarketBISTValue = (TextView) header.findViewById(R.id.txtMarketBISTValue);
        viewHeader.txtMarketBISTPercentage = (TextView) header.findViewById(R.id.txtMarketBISTPercentage);
        viewHeader.imgMarketUSDStatus = (ImageView) header.findViewById(R.id.imgMarketUSDStatus);
        viewHeader.imgMarketEUROStatus = (ImageView) header.findViewById(R.id.imgMarketEUROStatus);
        viewHeader.imgMarketGOLDStatus = (ImageView) header.findViewById(R.id.imgMarketGOLDStatus);
        viewHeader.imgMarketBRENTStatus = (ImageView) header.findViewById(R.id.imgMarketBRENTStatus);
        viewHeader.imgMarketBISTStatus = (ImageView) header.findViewById(R.id.imgMarketBISTStatus);

        setHeaderData(viewHeader);
        header.setTag(viewHeader);


        return header;

    }

    private void setHeaderData(ViewHeader viewHeader) {
        setMarketTextsAndImage(viewHeader.txtMarketUSDValue, market.getUSD_VALUE(), viewHeader.txtMarketUSDPercantage, market.getUSD_PERCANTAGE(), viewHeader.imgMarketUSDStatus);
        setMarketTextsAndImage(viewHeader.txtMarketEUROValue, market.getEURO_VALUE(), viewHeader.txtMarketEUROPercantage, market.getEURO_PERCANTAGE(), viewHeader.imgMarketEUROStatus);
        setMarketTextsAndImage(viewHeader.txtMarketGOLDValue, market.getGOLD_VALUE(), viewHeader.txtMarketGOLDPercantage, market.getGOLD_PERCANTAGE(), viewHeader.imgMarketGOLDStatus);
        setMarketTextsAndImage(viewHeader.txtMarketBRENTValue, market.getBRENT_VALUE(), viewHeader.txtMarketBRENTPercantage, market.getBRENT_PERCANTAGE(), viewHeader.imgMarketBRENTStatus);
        setMarketTextsAndImage(viewHeader.txtMarketBISTValue, market.getBIST100_VALUE(), viewHeader.txtMarketBISTPercentage, market.getBIST100_PERCANTAGE(), viewHeader.imgMarketBISTStatus);
    }

    private void setMarketTextsAndImage(TextView valueText, float valueData, TextView percantageText, float percantageData, ImageView status) {

        valueText.setText(valueData + "");
        percantageText.setText(percantageData + "");

        if (percantageData < 0) {
            percantageText.setTextColor(getResources().getColor(R.color.redTextColor));
            status.setImageResource(R.drawable.ic_down_d32f2f);
        } else if (percantageData > 0) {
            percantageText.setTextColor(getResources().getColor(R.color.greenTextColor));
            status.setImageResource(R.drawable.ic_up_2e7d32);
        } else {
            percantageText.setTextColor(getResources().getColor(R.color.blueTextColor));
            status.setImageResource(R.drawable.ic_stabil_0277bd);
        }
    }

    private void selectedItem(int position) {

        if (position == 0)
            getSupportFragmentManager().beginTransaction().replace(R.id.frmMainContainer, specialExhangeListFragment, "SpecialExchangeFragment").commit();
        else {
            Bundle arg = new Bundle();
            arg.putString("SelectedCategory", ExchangeCategory.getKey(exchangeCategoryList.get(position)));

            exchangeListFragment = new ExchangeList();
            exchangeListFragment.setArguments(arg);

            getSupportFragmentManager().beginTransaction().replace(R.id.frmMainContainer, exchangeListFragment, "ExchangeListFragment").commit();
        }
        getSupportActionBar().setTitle(exchangeCategoryList.get(position));

    }

    private class ViewHeader {
        TextView txtMarketUSDValue;
        TextView txtMarketUSDPercantage;
        TextView txtMarketEUROValue;
        TextView txtMarketEUROPercantage;
        TextView txtMarketGOLDValue;
        TextView txtMarketGOLDPercantage;
        TextView txtMarketBRENTValue;
        TextView txtMarketBRENTPercantage;
        TextView txtMarketBISTValue;
        TextView txtMarketBISTPercentage;
        ImageView imgMarketUSDStatus;
        ImageView imgMarketEUROStatus;
        ImageView imgMarketGOLDStatus;
        ImageView imgMarketBRENTStatus;
        ImageView imgMarketBISTStatus;
    }


}
