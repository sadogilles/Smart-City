package com.smart.smartcity.fragment;

import android.graphics.Bitmap;
import android.opengl.Visibility;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.NetworkListAdapter;
import com.smart.smartcity.adapters.PublicationListAdapter;
import com.smart.smartcity.context.IDownloadImageContext;
import com.smart.smartcity.context.IMainFragmentContext;
import com.smart.smartcity.context.IPublicationCreationContext;
import com.smart.smartcity.context.IPublicationListContext;
import com.smart.smartcity.dao.NetworkDAO;
import com.smart.smartcity.dao.PublicationDAO;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Publication;
import com.smart.smartcity.model.User;
import com.smart.smartcity.util.DownloadImageTask;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkDetailsFragment extends Fragment implements View.OnClickListener, IDownloadImageContext, IPublicationListContext, IPublicationCreationContext {
    private static final String NETWORK_KEY = "NETWORK";
    private Network network;
    private User user;
    private MaterialTextView networkName;
    private MaterialTextView networkDescription;
    private ImageView networkImage;
    private Button togglePublishFormButton;
    private LinearLayout publicationForm;
    private ListView publicationListView;
    private TextView publicationListStatus;
    private PublicationListAdapter publicationListAdapter;
    private List<Publication> publications = new ArrayList<>();
    private PublicationDAO publicationDAO;
    private TextView publicationCreationStatus;
    private Button networkAdministrationButton;

    // Publication form
    private TextInputEditText newPublicationContent;
    private Button newPublicationPublishButton;

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
        user = ((MainActivity) getActivity()).getUser();

        networkName = view.findViewById(R.id.network_details_name);
        networkDescription = view.findViewById(R.id.network_details_description);
        networkImage = view.findViewById(R.id.network_details_image);
        togglePublishFormButton = view.findViewById(R.id.toggle_publish_form);
        publicationForm = view.findViewById(R.id.publication_form);
        publicationListView = view.findViewById(R.id.network_details_publication_list);
        publicationListStatus = view.findViewById(R.id.publication_list_status);
        newPublicationContent = view.findViewById(R.id.new_publication_content);
        newPublicationPublishButton = view.findViewById(R.id.new_publication_publish_btn);
        publicationCreationStatus = view.findViewById(R.id.publication_creation_status);
        networkAdministrationButton = view.findViewById(R.id.admin_network_button);

        // Fills network metadata
        networkName.setText(network.getName());
        networkDescription.setText(network.getDescription());

        if (network.getAuthorId() == user.getId()) {
            networkAdministrationButton.setVisibility(View.VISIBLE);
            networkAdministrationButton.setOnClickListener(this);
        }

        // Requests network image
        new DownloadImageTask(this, network.getId()).execute(network.getImageUrl());

        // Bind toggle publish button
        togglePublishFormButton.setOnClickListener(this);

        // Init list view
        publicationListAdapter = new PublicationListAdapter(getActivity().getApplicationContext(), publications);
        publicationListView.setAdapter(publicationListAdapter);

        // Find publications from web API
        publicationDAO = new PublicationDAO();
        publicationDAO.setPublicationListContext(this);
        publicationDAO.findPublications(network.getId());

        newPublicationPublishButton.setOnClickListener(this);

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
        } else if (v.getId() == R.id.new_publication_publish_btn) {
            String content = newPublicationContent.getText().toString();

            Instant now = Instant.now();
            String date = DateTimeFormatter.ISO_INSTANT.format(now);
            Publication publication = new Publication(network.getId(), user.getId(), date, content);
            publicationDAO.setPublicationCreationContext(this);
            publicationDAO.insertPublication(publication);
        } else if (v.getId() == R.id.admin_network_button) {
            ((IMainFragmentContext) getActivity()).showNetworkAdministrationFragment(network);
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
        publicationListAdapter.sort(new Comparator<Publication>() {
            @Override
            public int compare(Publication o1, Publication o2) {
                return o2.compareTo(o1);
            }
        });
        publicationListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPublicationsFailure() {
        publicationListStatus.setText("Error : can't get publication list");
    }

    @Override
    public void onPublicationCreationSuccessful(Publication publication) {
        publicationDAO.findPublications(network.getId());
    }

    @Override
    public void onPublicationCreationFailure() {
        publicationCreationStatus.setText("Error : can't create publication");
    }
}
