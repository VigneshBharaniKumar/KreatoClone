package com.vignesh.kreatoclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChatActivity extends AppCompatActivity implements ChatUsersRecyclerAdapter.onClickUserInterface {

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout pullToRefreshLayout;
    private SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setTitle("Chats");

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView_chat);
        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);

        setSupportActionBar(toolbar);

        pullToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

    }

    private void refreshList() {

        pullToRefreshLayout.setRefreshing(true);

        final ArrayList<String> users = new ArrayList<>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null) {

                    pullToRefreshLayout.setRefreshing(false);

                    for (ParseUser user : objects) {
                        users.add(user.getUsername());
                    }

                    recyclerView.setAdapter(new ChatUsersRecyclerAdapter(users, ChatActivity.this));
                    recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

                } else {

                    pullToRefreshLayout.setRefreshing(false);

                    alertDialog = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Error!")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshList();

    }

    @Override
    public void onClickUser(String selectedUser) {

    }
}
