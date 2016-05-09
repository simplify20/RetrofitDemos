package com.creact.steve.retrofitsample.biz.github;

import com.creact.steve.retrofitsample.data.Repo;
import com.creact.steve.retrofitsample.network.core.MyCall;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/5/8.
 */
public interface GitHubService {

    @GET(ApiConstants.API_USER_REPO)
    MyCall<List<Repo>> listRepos(@Path("user") String user);
}
