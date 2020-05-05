package com.smart.smartcity.fragment;

import android.app.Fragment;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ButtonBarLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smart.smartcity.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomMenuFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomMenu = null;

    // variable for callback to Main activity
    private OnBottomMenuItemClickedListener  callback= null;

    //redirecting listener to main activity
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //spread click to parent main activity
        callback.onBottomMenuItemClicked(item);

        return true;
    }

    //private interface to link bottom menu fragment to main activity
    public interface OnBottomMenuItemClickedListener{
        public void onBottomMenuItemClicked(MenuItem item);
    }

    public BottomMenuFragment() {
        // Required empty public constructor
    }

    public static BottomMenuFragment newInstance() {
        BottomMenuFragment fragment = new BottomMenuFragment();

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
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);

        bottomMenu = view.findViewById(R.id.bottom_menu);

        bottomMenu.setSelectedItemId(R.id.news_icon);

        //redirect listener to class listener
        bottomMenu.setOnNavigationItemSelectedListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

    // 3 - Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            callback= (OnBottomMenuItemClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtomItemClickedListener");
        }
    }
}
