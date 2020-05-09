package com.smart.smartcity.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;
import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.NetworkListAdapter;
import com.smart.smartcity.adapters.PublicationListAdapter;
import com.smart.smartcity.context.IDownloadImageContext;
import com.smart.smartcity.context.IPublicationListContext;
import com.smart.smartcity.dao.NetworkDAO;
import com.smart.smartcity.dao.PublicationDAO;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Publication;
import com.smart.smartcity.util.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkDetailsFragment extends Fragment implements View.OnClickListener, IDownloadImageContext, IPublicationListContext {
    private static final String NETWORK_KEY = "NETWORK";
    private Network network;
    private MaterialTextView networkName;
    private MaterialTextView networkDescription;
    private ImageView networkImage;
    private Button togglePublishFormButton;
    private LinearLayout publicationForm;
    private ListView publicationListView;
    private TextView publicationListStatus;
    private PublicationListAdapter publicationListAdapter;
    private List<Publication> publications = new ArrayList<>();

    public NetworkDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NetworkDetailsFragment newInstance(Network network) {
        NetworkDetailsFragment fragment = new NetworkDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(NETWORK_KEY, network);
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
        View view = inflater.inflate(R.layout.network_details_fragment, container, false);

        network = (Network) getArguments().getParcelable(NETWORK_KEY);

        networkName = view.findViewById(R.id.network_details_name);
        networkDescription = view.findViewById(R.id.network_details_description);
        networkImage = view.findViewById(R.id.network_details_image);
        togglePublishFormButton = view.findViewById(R.id.toggle_publish_form);
        publicationForm = view.findViewById(R.id.publication_form);
        publicationListView = view.findViewById(R.id.network_details_publication_list);
        publicationListStatus = view.findViewById(R.id.publication_list_status);

        // Fills network metadata
        networkName.setText(network.getName());
        networkDescription.setText(network.getDescription());

        // Requests network image
        new DownloadImageTask(this, network.getId()).execute(network.getImageUrl());

        // Bind toggle publish button
        togglePublishFormButton.setOnClickListener(this);

        // Init list view
        publicationListAdapter = new PublicationListAdapter(getActivity().getApplicationContext(), publications);
        publicationListView.setAdapter(publicationListAdapter);

        // Find publications from web API
        PublicationDAO dao = new PublicationDAO();
        dao.setPublicationListContext(this);
        dao.findPublications(network.getId());

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toggle_publish_form) {
            if (publicationForm.getVisibility() == View.GONE) {
                publicationForm.setVisibility(View.VISIBLE);
            } else if (publicationForm.getVisibility() == View.VISIBLE) {
                publicationForm.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onImageDownloaded(Bitmap bitmap, int id) {
        network.setImageBitmap(bitmap);

        networkImage.setImageBitmap(bitmap);
    }

    @Override
    public void onGetPublicationsSuccessful(List<Publication> publications) {
        this.publications.clear();
        this.publications.addAll(publications);
        publicationListAdapter.clear();
        publicationListAdapter.addAll(publications);
        publicationListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPublicationsFailure() {
        publicationListStatus.setText("Error : can't get publication list");
    }
}
