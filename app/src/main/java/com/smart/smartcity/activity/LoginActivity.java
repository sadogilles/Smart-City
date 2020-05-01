package com.smart.smartcity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.context.IAuthenticationContext;
import com.smart.smartcity.dao.UserDAO;
import com.smart.smartcity.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, IAuthenticationContext {
    public static final String USER_KEY = "USER";
    private EditText mailAddress;
    private EditText password;
    private Button signInButton;
    private Button signUpButton;
    private TextView invalidCredentialsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mailAddress = findViewById(R.id.mailAddressField);
        password = findViewById(R.id.passwordField);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);
        invalidCredentialsText = findViewById(R.id.invalidCredentialsText);

        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signInButton) {
            UserDAO dao = new UserDAO();
            dao.setAuthenticationContext(this);
            dao.authentify(mailAddress.getText().toString(), password.getText().toString());
        } else if (v.getId() == R.id.signUpButton) {
            Intent intent = new Intent(this, IRegistrationActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSuccess(User user) {
        System.out.println(user.getId());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getDateOfBirth());
        System.out.println(user.getGender());
        System.out.println(user.getTown());
        System.out.println(user.getAddress());
        System.out.println(user.getInterests().size());
        System.out.println(user.getServices().size());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(USER_KEY, user);
        startActivity(intent);
    }

    @Override
    public void onUserNotFound() {
        invalidCredentialsText.setVisibility(View.VISIBLE);
    }
}
