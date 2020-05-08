package com.smart.smartcity.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smart.smartcity.R;

import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrafficFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrafficFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    //variable for google maps
    GoogleMap map=null;
    SupportMapFragment mapFragment=null;
    SearchView searchView=null;

    public TrafficFragment() {
        // Required empty public constructor
    }


    public static TrafficFragment newInstance() {
        TrafficFragment fragment = new TrafficFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_traffic, container, false);

        searchView= (SearchView)view.findViewById(R.id.google_maps_search);


        //connect with the fragment for ma p
        mapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.google_maps);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                //get the enter string
                String location = searchView.getQuery().toString();
                out.println(location);

                //list of addrss
                List<Address> addressList=null;

                if(location !=null || !location.equals(" ")){

                    //add our activity to geocoder
                    Geocoder geocoder = new Geocoder(getActivity());

                    try{
                        //get a list of address from the location string that was entered
                        addressList =geocoder.getFromLocationName(location,1);

                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    //get the first address result
                    Address address =   addressList.get(0);

                    //get the lattitude and longitude
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());

                    //add lat and long with title to google map
                    map.addMarker(new MarkerOptions().position(latLng).title(location));

                    //enable zoom
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                }
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }
}
