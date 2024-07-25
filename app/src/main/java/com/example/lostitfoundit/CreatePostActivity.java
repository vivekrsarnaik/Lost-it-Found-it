package com.example.lostitfoundit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Spinner;

import java.util.List;

public class CreatePostActivity extends AppCompatActivity {

    TextView backBtn;
    Button postBtn;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = (User) getIntent().getParcelableExtra("currentUser");
        setContentView(R.layout.activity_create_post);

        backBtn = (TextView) findViewById(R.id.createPostBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToViewList();
            }
        });
        postBtn = (Button) findViewById(R.id.postBtn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView itemNameTextView = (TextView) findViewById(R.id.itemName);
                TextView itemDescTextView = (TextView) findViewById(R.id.itemDescription);
                TextView reportedDateTextView = (TextView) findViewById(R.id.reportDate);
                Spinner statusTextView = (Spinner) findViewById(R.id.statusOptions);
                ImageView locationTextView = (ImageView) findViewById(R.id.addLocation);
                ImageView imageTextView = (ImageView) findViewById(R.id.addImage);

                String itemName = itemNameTextView.getText().toString();
                String itemDesc = itemDescTextView.getText().toString();
                String reportedDate = reportedDateTextView.getText().toString();
                String statusString = statusTextView.getSelectedItem().toString();
                Post.STATUS status;
                if (statusString.equalsIgnoreCase(String.valueOf(Post.STATUS.FOUND))) {
                    status = Post.STATUS.FOUND;
                } else {
                    status = Post.STATUS.LOST;
                }

                createPost(itemName, itemDesc, reportedDate, status);
            }
        });
    }

    public void goToViewList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void createPost(String itemName, String itemDesc, String reportedDate, Post.STATUS status) {
        MyDatabase myDatabase = MyDatabase.getMyDatabase(this);
        AllDao allDao = myDatabase.getAllDao();

        Post createdPost = new Post(currentUser.uid, itemName, itemDesc, "", status, reportedDate, "");
        allDao.createPost(createdPost);

        List<Post> allPosts = allDao.getAllPosts();

        for (Post p : allPosts) {
            System.out.println(p);
        }

        goToViewList();
    }
}