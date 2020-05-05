package com.smart.smartcity.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smart.smartcity.R;
import com.smart.smartcity.activity.LoginActivity;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.ServiceAdapter;
import com.smart.smartcity.context.IDownloadImageContext;
import com.smart.smartcity.context.IServiceListContext;
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
public class NewsFragment extends Fragment implements IServiceListContext, IDownloadImageContext {

    private ServiceAdapter serviceAdapter = null;
    private ListView serviceListView = null;
    private List<Service> services = new ArrayList<Service>();
    private User user = null;

    public NewsFragment() {
        // Required empty public constructor
    }



    public static NewsFragment newInstance(User user) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(LoginActivity.USER_KEY, user);
        fragment.setArguments(bundle);

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

        user = getArguments().getParcelable(LoginActivity.USER_KEY);

        ServiceDAO dao = new ServiceDAO();
        dao.setServiceListContext(this);

        dao.findServicesByUserId(user.getId());

        serviceListView = view.findViewById(R.id.serviceListView);
        serviceAdapter = new ServiceAdapter(getActivity().getApplicationContext(), services);
        serviceListView.setAdapter(serviceAdapter);


        return view;
    }

    @Override
    public void onSuccess(List<Service> services) {
        this.services.clear();
        this.services.addAll(services);
        serviceAdapter.clear();
        serviceAdapter.addAll(services);
        serviceAdapter.notifyDataSetChanged();

        for (Service service : services) {
            new DownloadImageTask(this, service.getId()).execute(service.getImageUrl());
        }
    }

    @Override
    public void onGetServicesFailed() {
        System.out.println("services : failed");
        Log.e("services", "Error while collecting services from web API");
    }

    @Override
    public void onImageDownloaded(Bitmap bitmap, int id) {
        System.out.println("services : " + services.size());
        Service service = (Service) serviceAdapter.getItem(id);
        service.setImageBitmap(bitmap);
        serviceAdapter.notifyDataSetChanged();
    }
}
