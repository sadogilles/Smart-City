package com.smart.smartcity.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.context.INetworkCreationContext;
import com.smart.smartcity.dao.NetworkDAO;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.User;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNetworkSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNetworkSettingsFragment extends Fragment implements View.OnClickListener, INetworkCreationContext {
    private TextView networkCreationStatus;
    private TextInputEditText networkName;
    private TextInputEditText networkDescription;
    private Uri networkImageUri;
    private ImageView network_image;
    private Button submitButton;
    private User user;

    public CreateNetworkSettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateNetworkSettingsFragment newInstance() {
        CreateNetworkSettingsFragment fragment = new CreateNetworkSettingsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_network_settings, container, false);

        user = ((MainActivity) getActivity()).getUser();

        networkCreationStatus = view.findViewById(R.id.network_creation_status);
        networkName = view.findViewById(R.id.network_name);
        networkDescription = view.findViewById(R.id.network_description);
        network_image= view.findViewById(R.id.upload_network_image);
        submitButton = view.findViewById(R.id.network_creation_settings_submit_button);
        network_image.setOnClickListener(this);
        submitButton.setOnClickListener(this);

       return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                networkImageUri = result.getUri();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), networkImageUri);
                    network_image.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.upload_network_image) {
            Toast.makeText(getContext(),"Upload click",Toast.LENGTH_SHORT).show();

            Intent gallery = new Intent();

            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);

            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(getContext(), this);
        } else if (v.getId() == R.id.network_creation_settings_submit_button) {
            int authorId = user.getId();
            String name = networkName.getText().toString();
            String description = networkDescription.getText().toString();
            String localImageUri = networkImageUri.getPath();

            Network network = new Network(authorId, name, description, localImageUri);
            NetworkDAO dao = new NetworkDAO();
            dao.setNetworkCreationContext(this);

            dao.insert(network);
        }
    }

    @Override
    public void onNetworkCreationSuccessful(Network network) {
        networkCreationStatus.setText("Network '" + network.getName() + "' created successfully !");
    }

    @Override
    public void onNetworkCreationFailure() {
        networkCreationStatus.setText("Error : Failed to create network");
    }
}
