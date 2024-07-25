package com.example.lostitfoundit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User currentUser = (User) getIntent().getParcelableExtra("currentUser");
        Post post = (Post) getIntent().getParcelableExtra("post");
        setContentView(R.layout.activity_post_details);

        TextView itemName = (TextView) findViewById(R.id.pd_itemName);
        itemName.setText(post.itemName);

        TextView itemDesc = (TextView) findViewById(R.id.pd_itemDesc);
        itemDesc.setText(post.itemDescription);

//        TextView itemLocation = (TextView) findViewById(R.id.pd_itemLocation);
//        itemLocation.setText(post.location);

        User postUser = getPostUser(post.creator);
        TextView itemReport = (TextView) findViewById(R.id.pd_reportedBy);
        String itemReportString = "Item " + post.status + " by " + postUser.firstName + " " + postUser.lastName;
        itemReport.setText(itemReportString);

        TextView backBtn = (TextView) findViewById(R.id.pd_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewActivity();
            }
        });

        Button claimBtn = (Button) findViewById(R.id.pd_claimButton);
        if (post.status == Post.STATUS.CLAIMED || post.status == Post.STATUS.PENDING) {
            claimBtn.setVisibility(View.GONE);
        }
        claimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                claimItem(post.pid, currentUser.uid);
                claimBtn.setVisibility(View.GONE);
            }
        });
    }

    public User getPostUser(int uid) {
        MyDatabase myDatabase = MyDatabase.getMyDatabase(this);
        AllDao allDao = myDatabase.getAllDao();

        return allDao.getUserById(uid);
    }

    public void listViewActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void claimItem(int pid, int uid){
        MyDatabase myDatabase = MyDatabase.getMyDatabase(this);
        AllDao allDao = myDatabase.getAllDao();

        allDao.claimItem(pid, uid);
    }

}