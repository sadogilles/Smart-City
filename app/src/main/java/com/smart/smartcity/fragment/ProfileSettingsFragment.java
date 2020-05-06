package com.smart.smartcity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.smart.smartcity.R;
import com.smart.smartcity.activity.LoginActivity;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.context.IProfileUpdateContext;
import com.smart.smartcity.dao.UserDAO;
import com.smart.smartcity.model.User;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSettingsFragment extends Fragment implements View.OnClickListener, IProfileUpdateContext {
    private User user;
    private TextInputEditText firstNameField;
    private TextInputEditText lastNameField;
    private TextInputEditText townField;
    private TextInputEditText addressField;
    private TextInputEditText emailField;
    private TextInputEditText passwordField;
    private Button updateButton;
    private TextView updateStatus;

    public ProfileSettingsFragment() {
        // Required empty public constructor
    }

    public static ProfileSettingsFragment newInstance() {
        ProfileSettingsFragment fragment = new ProfileSettingsFragment();

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
        View view = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        user = ((MainActivity) getActivity()).getUser();

        // TODO : Normalize naming convention ids
        firstNameField = view.findViewById(R.id.user_first_name);
        firstNameField.setText(user.getFirstName());
        lastNameField = view.findViewById(R.id.user_last_name);
        lastNameField.setText(user.getLastName());
        townField = view.findViewById(R.id.user_town);
        townField.setText(user.getTown());
        addressField = view.findViewById(R.id.user_address);
        addressField.setText(user.getAddress());
        emailField = view.findViewById(R.id.user_email);
        emailField.setText(user.getEmail());
        passwordField = view.findViewById(R.id.user_password);
        passwordField.setText(user.getPassword());
        updateButton = view.findViewById(R.id.profile_settings_submit_button);
        updateButton.setOnClickListener(this);
        updateStatus = view.findViewById(R.id.updateStatus);

        return view;
    }

    @Override
    public void onClick(View v) {
        UserDAO dao = new UserDAO();
        dao.setProfileUpdateContextContext(this);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String town = townField.getText().toString();
        String address = addressField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTown(town);
        user.setAddress(address);
        user.setEmail(email);
        user.setPassword(password);

        dao.update(user);
    }

    @Override
    public void onUpdateSuccessful(User user) {
        updateStatus.setText("Update successful");
    }

    @Override
    public void onUpdateFailure() {
        updateStatus.setText("Update failed");
    }
}
