package me.jeeson.android.retrofitsoapsample.webservice;


import java.util.List;

import me.jeeson.android.retrofitsoapsample.webservice.request.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @Description: //todo
 * @anthor Jeeson Email:smuwjs@163.com
 * @time 2017/6/12 16:13
 */
public interface GithubInterfaceApi {

    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Call<List<User>> getUsers();
}
