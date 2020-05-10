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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.model.User;

import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrafficFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrafficFragment extends Fragment implements OnMapReadyCallback {
    private User user;
    private MapView mapView;
    private GoogleMap googleMap;
    private SearchView searchView;

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

        user = ((MainActivity) getActivity()).getUser();

        mapView = view.findViewById(R.id.map_view);
        //searchView = view.findViewById(R.id.search_view);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                System.out.println("begin");
//                if (googleMap == null) {
//                    return false;
//                }
//
//                String location = searchView.getQuery().toString();
//
//                if (location == null || location.equals("")) {
//                    return false;
//                }
//
//                return setFocus(location);
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        return view;
    }

    public boolean setFocus(String location) {
        List<Address> addressList;
        Geocoder geocoder = new Geocoder(getActivity());

        try{
            //get a list of address from the location string that was entered
            addressList = geocoder.getFromLocationName(location,1);
        }catch (IOException e){
            e.printStackTrace();

            return false;
        }

        Address address =   addressList.get(0);
        //get the lattitude and longitude
        LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
        //add lat and long with title to google map
        googleMap.addMarker(new MarkerOptions().position(latLng).title(location));

        //enable zoom
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        //return true
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("map ready");
        this.googleMap = googleMap;

        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        setFocus(user.getTown());

        mapView.onResume();
    }
}
