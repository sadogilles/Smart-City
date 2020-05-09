package com.smart.smartcity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.smartcity.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {



    public WeatherFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.i("HEllo World","whats'up");

        String content;
        Weather weather = new Weather();
        try {
            content = (String) weather.execute("https://openweathermap.org/data/2.5/weather?q=London&appid=b6907d289e10d714a6e88b30761fae22").get();
            //First we will check data is retrieve successfully or not
            Log.i("contentData",content);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }




    //anonymous class for weather
    class Weather extends AsyncTask {  //First String means URL is in String, Void mean nothing, Third String means Return type will be String

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        protected String doInBackground(String... address) {
            //String... means multiple address can be send. It acts as array
            try {

                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //Establish connection with address
                connection.connect();

                //retrieve data from url
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                //Retrieve data and return it as String
                int data = isr.read();
                String content = "";
                char ch;
                while (data != 0){
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                Log.i("Content",content);
                return content;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
