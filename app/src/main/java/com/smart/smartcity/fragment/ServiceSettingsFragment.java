package com.smart.smartcity.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.ServiceSettingsAdapter;
import com.smart.smartcity.context.IDownloadImageContext;
import com.smart.smartcity.context.IProfileUpdateContext;
import com.smart.smartcity.context.IServiceListContext;
import com.smart.smartcity.dao.ServiceDAO;
import com.smart.smartcity.dao.UserDAO;
import com.smart.smartcity.model.Service;
import com.smart.smartcity.model.User;
import com.smart.smartcity.util.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceSettingsFragment extends Fragment implements IServiceListContext, IDownloadImageContext, View.OnClickListener, IProfileUpdateContext {
    private User user;
    private ArrayList<Integer> userServices;
    private ServiceSettingsAdapter serviceSettingsAdapter = null;
    private ListView serviceListView = null;
    private List<Service> services = new ArrayList<>();
    private Button updateButton;
    private TextView serviceSettingsStatus;

    public ServiceSettingsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ServiceSettingsFragment newInstance() {
        ServiceSettingsFragment fragment = new ServiceSettingsFragment();

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
        View view = inflater.inflate(R.layout.fragment_service_settings, container, false);

        serviceSettingsStatus = view.findViewById(R.id.serviceSettingsStatus);
        user = ((MainActivity) getActivity()).getUser();
        userServices = new ArrayList<>(user.getServices());

        // Fetch all services from web API
        ServiceDAO dao = new ServiceDAO();
        dao.setServiceListContext(this);
        dao.findServices();

        // Initialize listView
        serviceListView = view.findViewById(R.id.service_settings_list);
        serviceSettingsAdapter = new ServiceSettingsAdapter(getActivity().getApplicationContext(), services, userServices);
        serviceListView.setAdapter(serviceSettingsAdapter);

        // Set handler for validation button
        updateButton = view.findViewById(R.id.update_service_settings_btn);
        updateButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onSuccess(List<Service> services) {
        this.services.clear();
        this.services.addAll(services);
        serviceSettingsAdapter.clear();
        serviceSettingsAdapter.addAll(services);
        serviceSettingsAdapter.notifyDataSetChanged();

        for (Service service : services) {
            new DownloadImageTask(this, service.getId()).execute(service.getImageUrl());
        }
    }

    @Override
    public void onGetServicesFailed() {
        Log.e("services", "Error while collecting services from web API");
    }

    @Override
    public void onImageDownloaded(Bitmap bitmap, int id) {
        Service service = (Service) serviceSettingsAdapter.getItem(id);
        service.setImageBitmap(bitmap);
        serviceSettingsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.update_service_settings_btn) {
            user.setServices(userServices);

            UserDAO dao = new UserDAO();
            dao.setProfileUpdateContextContext(this);
            dao.update(user);
            System.out.println(((MainActivity) getActivity()).getUser().getServices().size());
        }
    }

    @Override
    public void onUpdateSuccessful(User user) {
        serviceSettingsStatus.setText("Update successful !");
    }

    @Override
    public void onUpdateFailure() {
        Log.e("settings", "Failed to update user activated services");
        serviceSettingsStatus.setText("Error : Can't update activated services");
    }
}
