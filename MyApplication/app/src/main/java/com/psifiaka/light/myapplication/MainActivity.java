package com.psifiaka.light.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.javafaker.Faker;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button LogInButton;

    Faker faker = new Faker();

    String generatedUsername = faker.bothify("User_??###");
    String generatedPassword = faker.internet().password(16, 20);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditPassword);

        LogInButton = findViewById(R.id.buttonLogIn);

        username.setText(generatedUsername);
        password.setText(generatedPassword);


        LogInButton.setOnClickListener(v -> {

            String usernameToLogIn = username.getText().toString();
            String passwordToLogIn = password.getText().toString();

            if (usernameToLogIn.equals(generatedUsername) && passwordToLogIn.equals(generatedPassword)) {

                System.out.println("Success: " + usernameToLogIn + " ---> " + passwordToLogIn);
                Intent OpapRESTintent = new Intent(MainActivity.this, OpapREST.class);
                OpapRESTintent.putExtra("providedUsername",usernameToLogIn);

                generatedPassword = faker.internet().password(16, 20);

                startActivity(OpapRESTintent);
            }
            else {
                System.out.println("Invalid Credentials");
            }

        });

    }

}
