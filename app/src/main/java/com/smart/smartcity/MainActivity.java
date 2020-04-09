package com.smart.smartcity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Menu top_menu=null;
    ArrayAdapter<String> arrayAdapter=null;
    BottomNavigationView bottom_menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         ListView listView = findViewById(R.id.new_list);
         List<String> list = new ArrayList<>();

         list.add("Actualite 1");
         list.add("Actualite 2");
         list.add("Actualite 3");
         list.add("Actualite 4");
         list.add("Actualite 5");

         arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        top_menu = (Menu)findViewById(R.id.menu);
        bottom_menu =findViewById(R.id.bottom_menu);

        //set the home as default
        bottom_menu.setSelectedItemId(R.id.home_icon);

        //add action listener
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.home_icon:
                        Toast.makeText(getApplicationContext(),"Home icon clicked",Toast.LENGTH_SHORT).show();
                    case R.id.news_icon:
                        Toast.makeText(getApplicationContext(),"news icon clicked",Toast.LENGTH_SHORT).show();
                    case R.id.trade_icon:
                        Toast.makeText(getApplicationContext(),"Trade icon Clicke",Toast.LENGTH_SHORT).show();
                    case R.id.network_icon:
                        Toast.makeText(getApplicationContext(),"Network Icon Clicked",Toast.LENGTH_SHORT).show();

                }

                return true;
            }
        });
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
                arrayAdapter.getFilter().filter(newText);
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
}
