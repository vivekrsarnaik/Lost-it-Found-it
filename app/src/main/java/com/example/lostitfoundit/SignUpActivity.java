package com.example.lostitfoundit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    TextView backBtn;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        backBtn = (TextView) findViewById(R.id.backBtn);
        signUpBtn = (Button) findViewById(R.id.loginBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLandingPage();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView firstNameTextView = (TextView) findViewById(R.id.firstName);
                TextView lastNameTextView = (TextView) findViewById(R.id.lastName);
                TextView emailTextView = (TextView) findViewById(R.id.email);
                TextView passwordTextView = (TextView) findViewById(R.id.password);
                TextView confirmPasswordTextView = (TextView) findViewById(R.id.confirmPassword);

                String firstName = firstNameTextView.getText().toString();
                String lastName = lastNameTextView.getText().toString();
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                String confirmPassword = confirmPasswordTextView.getText().toString();

                if (password.equals(confirmPassword)) {
                    insertUserToDb(firstName, lastName, email, password);
                }
            }
        });
    }

    public void goToLandingPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void insertUserToDb(String firstName, String lastName, String email, String password) {
        MyDatabase myDatabase = MyDatabase.getMyDatabase(this);
        AllDao allDao = myDatabase.getAllDao();

        User userToCreate = new User(firstName, lastName, email, password);
        allDao.insertUser(userToCreate);


        goToLandingPage();
    }
}