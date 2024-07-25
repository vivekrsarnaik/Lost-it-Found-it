package com.example.lostitfoundit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextView backBtn;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        backBtn = (TextView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLandingPage();
            }
        });

        login = (Button) findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView emailTextView = (TextView) findViewById(R.id.emailLogin);
                TextView passwordTextView = (TextView) findViewById(R.id.passwordLogin);

                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                goToViewList(email, password);
            }
        });
    }

    public void goToLandingPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToViewList(String email, String password) {
        MyDatabase myDatabase = MyDatabase.getMyDatabase(this);
        AllDao allDao = myDatabase.getAllDao();

        User currentUser = allDao.getUserAtLogin(email, password);

        if (Objects.isNull(currentUser) || Objects.equals(currentUser.uid, -1)) {
            goToLandingPage();
        } else {
            System.out.println("User at Login: " + currentUser);

            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
        }

    }
}