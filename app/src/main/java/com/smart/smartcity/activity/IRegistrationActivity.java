package com.smart.smartcity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.context.IRegistrationContext;
import com.smart.smartcity.dao.UserDAO;
import com.smart.smartcity.model.User;

public class IRegistrationActivity extends AppCompatActivity implements View.OnClickListener, IRegistrationContext {
    private EditText mailAddressField;
    private EditText passwordField;
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText dateOfBirthField;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private EditText townField;
    private EditText addressField;
    private Button registerButton;
    private TextView registrationMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mailAddressField = findViewById(R.id.mailAddressFieldRegistration);
        passwordField = findViewById(R.id.passwordFieldRegistration);
        firstNameField = findViewById(R.id.firstNameFieldRegistration);
        lastNameField = findViewById(R.id.lastNameFieldRegistration);
        dateOfBirthField = findViewById(R.id.dateOfBirthFieldRegistration);
        genderRadioGroup = findViewById(R.id.genderRadioGroupRegistration);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        townField = findViewById(R.id.townFieldRegistration);
        addressField = findViewById(R.id.addressFieldRegistration);
        registerButton = findViewById(R.id.registerButton);
        registrationMessage = findViewById(R.id.registrationMessageText);

        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == registerButton.getId()) {
            registerUser();
        }
    }

    private void registerUser() {
        String mailAddress = fieldValue(mailAddressField);
        String password = fieldValue(passwordField);
        String firstName = fieldValue(firstNameField);
        String lastName = fieldValue(lastNameField);
        String dateOfBirth = fieldValue(dateOfBirthField);
        String gender = radioValue(genderRadioGroup);
        String town = fieldValue(townField);
        String address = fieldValue(addressField);

        User user = new User(firstName, lastName, mailAddress, password, dateOfBirth, gender, town, address);
        UserDAO dao = new UserDAO();
        dao.setRegistrationContext(this);

        dao.register(user);
    }

    private String fieldValue(EditText field) {
        return field.getText().toString();
    }

    private String radioValue(RadioGroup radioGroup) {
        int optionId = radioGroup.getCheckedRadioButtonId();

        RadioButton selectedOption = findViewById(optionId);

        if (selectedOption == null) {
            return "";
        }

        return selectedOption.getText().toString();
    }

    @Override
    public void onSuccess(User user) {
        registrationMessage.setVisibility(View.VISIBLE);

        mailAddressField.setEnabled(false);
        passwordField.setEnabled(false);
        firstNameField.setEnabled(false);
        lastNameField.setEnabled(false);
        dateOfBirthField.setEnabled(false);
        maleRadioButton.setEnabled(false);
        femaleRadioButton.setEnabled(false);
        townField.setEnabled(false);
        addressField.setEnabled(false);
        registerButton.setEnabled(false);
    }

    @Override
    public void onRegistrationError() {

    }
}
