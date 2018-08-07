package com.example.bruger.hangmanv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameInput;
    EditText passwordInput;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameInput = (EditText) findViewById(R.id.nameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton) {
            Intent home = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(home);
        }
    }
}
