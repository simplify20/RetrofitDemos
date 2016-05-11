package com.creact.steve.retrofitsample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.creact.steve.retrofitsample.biz.github.GitHubService;
import com.creact.steve.retrofitsample.data.Repo;
import com.creact.steve.retrofitsample.network.adapter.Builder;
import com.creact.steve.retrofitsample.network.adapter.MyCall;
import com.creact.steve.retrofitsample.network.adapter.MyCallback;
import com.creact.steve.retrofitsample.network.adapter.MyResponse;
import com.creact.steve.retrofitsample.network.util.ServiceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText mUserEt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mUserEt = (EditText) findViewById(R.id.user_name_et);
    }


    public void searchRepo(View v) {

        if (mUserEt.getText() == null) return;
        final String name = mUserEt.getText().toString();

        GitHubService githubService = ServiceManager
                .getInstance()
                .getService(Builder.getDefault(), GitHubService.class);

        MyCall<List<Repo>> call = githubService.listRepos(name);
        final ProgressDialog progressDialog = ProgressDialog.show(this, "搜索中...", "", true);
        call.enqueue(new MyCallback<List<Repo>>() {
            @Override
            public void onResponse(MyCall<List<Repo>> call, MyResponse<List<Repo>> response) {
                progressDialog.dismiss();
                List<Repo> repos = response.body();
                if (repos != null) {
                    final String repo = "Repositories of " + name + ":" + repos;
                    System.out.println(repo);
                    Toast.makeText(MainActivity.this, repo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(MyCall<List<Repo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Search failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
