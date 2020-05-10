package com.smart.smartcity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    private User user=null;
    private String userTown = null;

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

        user = ((MainActivity)getActivity()).getUser();
        userTown = user.getTown();
        String main = null;
        String description = null;
        String  icon  = null;
        String country = null;
        String humidity = null;
        String temperature = null;

        String content;
        Weather weather = new Weather();

        String url = "http://openweathermap.org/data/2.5/weather?q="+userTown+"&appid=439d4b804bc8187953eb36d2a8c26a02";

        try {
            //request-->OK
            content = (String) weather.execute(url).get();Log.i("contentData",content);

            //parsing the data
            JSONObject js = new JSONObject(content);

            String weatherData = js.getString("weather"); Log.i("Weather",weatherData);

            JSONArray data = new JSONArray(weatherData);



            for(int i=0; i<data.length();i++){

                JSONObject weatherPart = data.getJSONObject(i);

                 main = weatherPart.getString("main");
                 description= weatherPart.getString("description");
                 icon = weatherPart.getString("icon");
            }

            System.out.println("\n \n");
            System.out.println(main);
            System.out.println(description);
            System.out.println(icon);

            //next is to get the country and humidity

            //getting the country
            String sys = js.getString("sys"); //returns  a string with a json object
            JSONObject sysObject = new JSONObject(sys);

          System.out.println(sysObject);

          String countryCode=sysObject.getString("country");

          System.out.println(countryCode);

          //convert the country Code to country using in build java locale variable
            Locale l = new Locale("",countryCode);

            country =l.getDisplayCountry();

            System.out.println(country);


            //humidity
            String mainString = js.getString("main");

            JSONObject main1 = new JSONObject(mainString);

            humidity = main1.getString("humidity");

            System.out.println(humidity);

            String main2 = js.getString("main");

            JSONObject temp= new JSONObject(main2);

            temperature = temp.getString("temp");

            System.out.println(temperature);

            // ok we have country, humidity, temp_description, icons ,temp in degree i think we are good to go

        } catch (Exception e) {
            e.printStackTrace();
        }




        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        ((TextView)(v.findViewById(R.id.temperature))).setText(temperature);
        ((TextView)(v.findViewById(R.id.humidity))).setText(humidity);
        ((TextView)(v.findViewById(R.id.town_name))).setText(userTown);
        ((TextView)(v.findViewById(R.id.country))).append(country);
        ((TextView)(v.findViewById(R.id.temperature_description))).setText(description);
        ((TextView)(v.findViewById(R.id.date))).setText((new Date()).toString());

        return v;
    }




    //anonymous class for weather
    class Weather extends AsyncTask<String,Void,String> {  //First String is input , last string is output

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

                while (data > 0){
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }

                Log.i("Content",content);
                return content;

            } catch (Exception e) { e.printStackTrace(); }

            return null;
        }
    }

}
