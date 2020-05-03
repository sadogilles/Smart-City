package com.smart.smartcity.activity;

import android.os.Bundle;
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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.smart.smartcity.R;
import com.smart.smartcity.adapters.ServiceAdapter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements BottomMenuFragment.OnBottomMenuItemClickedListener{ // implements ,IServiceListContext

    private User user = null;
    private Menu topMenu = null;
    private ServiceAdapter serviceAdapter = null;
    private ListView serviceListView = null;
    private List<Service> services = new ArrayList<Service>();

    //fragments
    private NetworkFragment networkFragment = null;
    private NewsFragment newsFragment = null;
    private HomeFragment homeFragment = null;
    private TradeFragment tradeFragment = null;
    private SettingsFragment settingsFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = getIntent().getExtras().getParcelable(LoginActivity.USER_KEY);

        Toast.makeText(this, "Welcome " + user.getFirstName() + " !", Toast.LENGTH_SHORT).show();

        //ServiceDAO dao = new ServiceDAO();
       // dao.setServiceListContext(this);

     //   dao.findServicesByUserId(user.getId());

        //serviceListView = findViewById(R.id.serviceListView);
       // serviceAdapter = new ServiceAdapter(this, services);
       // serviceListView.setAdapter(serviceAdapter);

        topMenu = findViewById(R.id.menu);
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

//    @Override
//    public void onSuccess(List<Service> services) {
//        serviceAdapter.clear();
//        serviceAdapter.addAll(services);
//        serviceAdapter.notifyDataSetChanged();
//        this.services.clear();
//        this.services.addAll(services);
//    }

//    @Override
//    public void onGetServicesFailed() {
//        Log.e("services", "Error while collecting services from web API");
//    }

    @Override
    public void onBottomMenuItemClicked(MenuItem item) {

       //show fragment corresponding to the bottom menu item clicked
        switch(item.getItemId()){
            case R.id.home_icon:
                this.showHomeFragment();
                break;
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

        if(this.newsFragment==null) {
            this.newsFragment = NewsFragment.newInstance();
            this.startFragmentTransaction(this.newsFragment);

            System.out.println("news clicked");

            this.newsFragment=null;
        }

    }

    private void showTradeFragment() {

        if(this.tradeFragment==null) {
            this.tradeFragment = tradeFragment.newInstance();
            this.startFragmentTransaction(this.tradeFragment);

            System.out.println("trade clicked");

            tradeFragment=null;
        }
        
    }

    private void showNetworkFragment() {

        if(this.networkFragment==null) {
            this.networkFragment = networkFragment.newInstance();
            this.startFragmentTransaction(this.networkFragment);

            System.out.println("network clicked");

            this.networkFragment=null;
        }
    }

    private void showHomeFragment() {
        if(this.homeFragment==null) {
            this.homeFragment = homeFragment.newInstance();
            this.startFragmentTransaction(this.homeFragment);

            System.out.println("home clicked");
            homeFragment=null;
        }

    }

    private void configureAndShowSettingsFragment() {

        if(this.settingsFragment==null) {
            this.settingsFragment = SettingsFragment.newInstance();
            this.startFragmentTransaction(settingsFragment);
            System.out.println("settings clicked");
            settingsFragment=null;
        }

    }

    public void startFragmentTransaction(Fragment fragment){

        if(!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,fragment).commit();
        }

    }


}
