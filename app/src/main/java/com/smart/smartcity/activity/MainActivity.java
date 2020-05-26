package com.smart.smartcity.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smart.smartcity.R;
import com.smart.smartcity.context.global.IMainFragmentContext;
import com.smart.smartcity.fragment.global.BottomMenuFragment;
import com.smart.smartcity.fragment.networks.NetworkAdministrationFragment;
import com.smart.smartcity.fragment.networks.NetworkDetailsFragment;
import com.smart.smartcity.fragment.networks.NetworkFragment;
import com.smart.smartcity.fragment.services.NewsFragment;
import com.smart.smartcity.fragment.settings.SettingsFragment;
import com.smart.smartcity.fragment.trades.TradeDetailsFragment;
import com.smart.smartcity.fragment.trades.TradeFragment;
import com.smart.smartcity.fragment.services.TrafficFragment;
import com.smart.smartcity.fragment.services.WeatherFragment;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.User;
import com.smart.smartcity.util.CurrentFragment;

import static com.smart.smartcity.util.CurrentFragment.NEWS;


public class MainActivity extends AppCompatActivity implements BottomMenuFragment.OnBottomMenuItemClickedListener, IMainFragmentContext {

    private User user = null;
    private Menu topMenu = null;

    //fragments
    private NetworkFragment networkFragment = null;
    private NewsFragment newsFragment = null;
    private TradeFragment tradeFragment = null;
    private SettingsFragment settingsFragment = null;
    private CurrentFragment currentFragment = CurrentFragment.NONE;
    private BottomNavigationView bottomMenuView;

    private static final String[] ACCESS_FINE_LOCATION_PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final String[] ACCESS_COARSE_LOCATION_PERMISSION = {
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Show an explanation if needed
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, ACCESS_FINE_LOCATION_PERMISSION, 0);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, ACCESS_COARSE_LOCATION_PERMISSION, 1);
        }

        user = getIntent().getExtras().getParcelable(LoginActivity.USER_KEY);

        Toast.makeText(this, "Welcome " + user.getFirstName() + " !", Toast.LENGTH_SHORT).show();

        topMenu = findViewById(R.id.menu);
        bottomMenuView = findViewById(R.id.bottom_menu);

        showNewsFragment(true);
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
               this.showNewsFragment(false);
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

    public void showNewsFragment(boolean initial) {
        if (currentFragment != NEWS) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            newsFragment = NewsFragment.newInstance();
            transaction.replace(R.id.main_fragment, newsFragment);
            if (! initial) {
                transaction.addToBackStack(null);
            }
            currentFragment = NEWS;
            transaction.commit();
        }
    }

    public void showTradeFragment() {
        if(currentFragment != CurrentFragment.TRADES) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            tradeFragment = TradeFragment.newInstance();
            transaction.replace(R.id.main_fragment, tradeFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            currentFragment = CurrentFragment.TRADES;
        }
    }

    public void showNetworkFragment() {
        if(currentFragment != CurrentFragment.NETWORKS) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            networkFragment = NetworkFragment.newInstance();
            transaction.replace(R.id.main_fragment, networkFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            currentFragment = CurrentFragment.NETWORKS;
        }
    }

    public void configureAndShowSettingsFragment() {
        if(currentFragment != CurrentFragment.SETTINGS) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            settingsFragment = SettingsFragment.newInstance();
            transaction.replace(R.id.main_fragment, settingsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            currentFragment = CurrentFragment.SETTINGS;
        }
    }

    public void showNetworkDetailsFragment(Network network) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        NetworkDetailsFragment fragment = NetworkDetailsFragment.newInstance(network);
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showNetworkAdministrationFragment(Network network) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        NetworkAdministrationFragment fragment = NetworkAdministrationFragment.newInstance(network);
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showTradeDetailsFragment(int tradeId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TradeDetailsFragment fragment = TradeDetailsFragment.newInstance(tradeId);
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showTrafficFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TrafficFragment fragment = TrafficFragment.newInstance();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showWeatherFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        WeatherFragment fragment = WeatherFragment.newInstance();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void updateBottomMenu(int icon) {
        bottomMenuView.setSelectedItemId(icon);
    }

    public User getUser() {
        return user;
    }
}
