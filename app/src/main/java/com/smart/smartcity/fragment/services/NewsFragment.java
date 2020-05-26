package com.smart.smartcity.fragment.services;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.ServiceAdapter;
import com.smart.smartcity.context.global.IDownloadImageContext;
import com.smart.smartcity.context.global.IMainFragmentContext;
import com.smart.smartcity.context.services.IServiceListContext;
import com.smart.smartcity.dao.ServiceDAO;
import com.smart.smartcity.model.Service;
import com.smart.smartcity.model.User;
import com.smart.smartcity.util.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment implements IServiceListContext, IDownloadImageContext, AdapterView.OnItemClickListener {

    private ServiceAdapter serviceAdapter = null;
    private ListView serviceListView = null;
    private List<Service> services = new ArrayList<Service>();
    private User user = null;
    private TextView noServiceActivatedStatus;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();

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
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        User user = ((MainActivity) getActivity()).getUser();

        ServiceDAO dao = new ServiceDAO();
        dao.setServiceListContext(this);

        dao.findServicesByUserId(user.getId());

        serviceListView = view.findViewById(R.id.serviceListView);
        serviceAdapter = new ServiceAdapter(getActivity().getApplicationContext(), services);
        serviceListView.setAdapter(serviceAdapter);
        noServiceActivatedStatus = view.findViewById(R.id.noServiceActivatedStatus);
        serviceListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onSuccess(List<Service> services) {
        if (services.isEmpty()) {
            noServiceActivatedStatus.setVisibility(View.VISIBLE);
        } else {
            noServiceActivatedStatus.setVisibility(View.GONE);
        }

        this.services.clear();
        this.services.addAll(services);
        serviceAdapter.clear();
        serviceAdapter.addAll(services);
        serviceAdapter.notifyDataSetChanged();

        for (Service service : services) {
            System.out.println("service : " + service);
            new DownloadImageTask(this, service.getId()).execute(service.getImageUrl());
        }
    }

    @Override
    public void onGetServicesFailed() {
        Log.e("services", "Error while collecting services from web API");
    }

    @Override
    public void onImageDownloaded(Bitmap bitmap, int id) {
        System.out.println("Downloaded " + id + " !");

        for (Service s : services) {
            if (s.getId() == id) {
                s.setImageBitmap(bitmap);
                serviceAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).updateBottomMenu(R.id.news_icon);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Service service = services.get(position);
        IMainFragmentContext mainFragmentContext = ((IMainFragmentContext) getActivity());

        switch(service.getServiceTypeEnum()) {
            case TRAFFIC:
                mainFragmentContext.showTrafficFragment();
                break;
            case METEO:
                mainFragmentContext.showWeatherFragment();
        }
    }
}
