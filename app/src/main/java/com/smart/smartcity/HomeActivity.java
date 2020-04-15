package com.smart.smartcity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.smart.smartcity.adapters.ServiceAdapter;
import com.smart.smartcity.context.IServiceListContext;
import com.smart.smartcity.dao.ServiceDAO;
import com.smart.smartcity.model.Service;
import com.smart.smartcity.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements IServiceListContext {
    private User user = null;
    private Menu topMenu = null;
    private ServiceAdapter serviceAdapter = null;
    private ListView serviceListView = null;
    private List<Service> services = new ArrayList<Service>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = getIntent().getExtras().getParcelable(LoginActivity.USER_KEY);

        Toast.makeText(this, "Welcome " + user.getFirstName() + " !", Toast.LENGTH_SHORT).show();

        ServiceDAO dao = new ServiceDAO();
        dao.setServiceListContext(this);

        dao.findServicesByUserId(user.getId());

        serviceListView = findViewById(R.id.serviceListView);
        serviceAdapter = new ServiceAdapter(this, services);
        serviceListView.setAdapter(serviceAdapter);

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
                Toast.makeText(this,"Settings clicked",Toast.LENGTH_SHORT).show();


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(List<Service> services) {
        serviceAdapter.clear();
        serviceAdapter.addAll(services);
        serviceAdapter.notifyDataSetChanged();
        this.services.clear();
        this.services.addAll(services);
    }

    @Override
    public void onGetServicesFailed() {
        Log.e("services", "Error while collecting services from web API");
    }
}
