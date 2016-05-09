package com.creact.steve.retrofitsample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.creact.steve.retrofitsample.biz.github.ApiConstants;
import com.creact.steve.retrofitsample.biz.github.GitHubService;
import com.creact.steve.retrofitsample.data.Repo;
import com.creact.steve.retrofitsample.network.HttpClientWrapper;
import com.creact.steve.retrofitsample.network.core.Config;
import com.creact.steve.retrofitsample.network.core.MyCall;
import com.creact.steve.retrofitsample.network.core.MyCallback;
import com.creact.steve.retrofitsample.network.core.MyResponse;
import com.creact.steve.retrofitsample.network.core.calladapter.MyCallAdapterFactory;
import com.creact.steve.retrofitsample.network.interceptors.EncryptInterceptor;
import com.creact.steve.retrofitsample.network.interceptors.HeadInterceptor;
import com.creact.steve.retrofitsample.network.interceptors.LoggingInterceptor;
import com.creact.steve.retrofitsample.network.util.HttpClientFactory;
import com.creact.steve.retrofitsample.network.util.ServiceManager;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .getService(Config.getDefault(), GitHubService.class);

        MyCall<List<Repo>> call = githubService.listRepos(name);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"搜索中...","",true);
        call.enqueue(new MyCallback<List<Repo>>() {
            @Override
            public void onResponse(MyCall<List<Repo>> call, MyResponse<List<Repo>> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    List<Repo> repos = response.body();
                    final String repo = "Repositories of " + name + ":" + repos;
                    System.out.println(repo);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, repo, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    String errorBody = response.errorBodyStr();
                    System.out.println("Response Code:" + response.code() + "body:" + errorBody);
                }
            }

            @Override
            public void onFailure(MyCall<List<Repo>> call, Throwable t) {
                progressDialog.dismiss();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Search failed", Toast.LENGTH_SHORT).show();
                    }
                });
                t.printStackTrace();
            }
        });
    }

    private Handler mHandler = new Handler();
}
