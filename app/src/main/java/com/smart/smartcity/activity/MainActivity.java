package com.smart.smartcity.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.smart.smartcity.R;
import com.smart.smartcity.adapters.ServiceAdapter;
import com.smart.smartcity.context.IServiceListContext;
import com.smart.smartcity.dao.ServiceDAO;
import com.smart.smartcity.fragment.BottomMenuFragment;
import com.smart.smartcity.fragment.HomeFragment;
import com.smart.smartcity.fragment.InterestSettingsFragment;
import com.smart.smartcity.fragment.NetworkFragment;
import com.smart.smartcity.fragment.NewsFragment;
import com.smart.smartcity.fragment.ProfileSettingsFragment;
import com.smart.smartcity.adapters.SettingsPageAdapter;
import com.smart.smartcity.fragment.SettingsFragment;
import com.smart.smartcity.fragment.TradeFragment;
import com.smart.smartcity.model.Service;
import com.smart.smartcity.model.User;
import com.smart.smartcity.util.CurrentFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.smart.smartcity.util.CurrentFragment.NEWS;


public class MainActivity extends AppCompatActivity implements BottomMenuFragment.OnBottomMenuItemClickedListener {

    private User user = null;
    private Menu topMenu = null;

    //fragments
    private NetworkFragment networkFragment = null;
    private NewsFragment newsFragment = null;
    private HomeFragment homeFragment = null;
    private TradeFragment tradeFragment = null;
    private SettingsFragment settingsFragment = null;
    private CurrentFragment currentFragment = CurrentFragment.NONE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = getIntent().getExtras().getParcelable(LoginActivity.USER_KEY);

        Toast.makeText(this, "Welcome " + user.getFirstName() + " !", Toast.LENGTH_SHORT).show();

        topMenu = findViewById(R.id.menu);

        showNewsFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_icon); //get the icon from the menu
        SearchView searchView = (SearchView) menuItem.getActionView(); //get a search view
        searchView.setQueryHint("Search Here!");//query hint

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { //search_icon action
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filter here
                // arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });

         return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.search_icon:
                Toast.makeText(this,"search Clicked",Toast.LENGTH_SHORT).show();
            case R.id.alert_icon:
                Toast.makeText(this,"alert Clicked",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.configuration_icon:
                Toast.makeText(this,"Configuration Clicked",Toast.LENGTH_SHORT).show();
            case R.id.help_icon :
                Toast.makeText(this,"Help Clicked",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings_icon:
                this.configureAndShowSettingsFragment();


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBottomMenuItemClicked(MenuItem item) {

       //show fragment corresponding to the bottom menu item clicked
        switch(item.getItemId()){
            case R.id.news_icon:
               this.showNewsFragment();
               break;
            case R.id.trade_icon:
                this.showTradeFragment();
                break;
            case R.id.network_icon:
                this.showNetworkFragment();
                break;
            default:
                break;
        }

    }

    private void showNewsFragment() {
        if (currentFragment != NEWS) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            newsFragment = NewsFragment.newInstance(user);
            transaction.replace(R.id.main_fragment, newsFragment);
            currentFragment = NEWS;
            transaction.commit();
        }
    }

    private void showTradeFragment() {
        if(currentFragment != CurrentFragment.TRADES) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            tradeFragment = TradeFragment.newInstance(user);
            transaction.replace(R.id.main_fragment, tradeFragment);
            transaction.commit();
            currentFragment = CurrentFragment.TRADES;
        }
    }

    private void showNetworkFragment() {
        if(currentFragment != CurrentFragment.NETWORKS) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            networkFragment = NetworkFragment.newInstance(user);
            transaction.replace(R.id.main_fragment, networkFragment);
            transaction.commit();
            currentFragment = CurrentFragment.NETWORKS;
        }
    }

    private void configureAndShowSettingsFragment() {
        if(currentFragment != CurrentFragment.SETTINGS) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            settingsFragment = SettingsFragment.newInstance(user);
            transaction.replace(R.id.main_fragment, settingsFragment);
            transaction.commit();
            currentFragment = CurrentFragment.SETTINGS;
        }
    }
}
