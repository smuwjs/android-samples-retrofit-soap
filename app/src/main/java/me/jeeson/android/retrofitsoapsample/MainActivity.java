package me.jeeson.android.retrofitsoapsample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import me.jeeson.android.retrofitsoapsample.adapter.WeatherResponseAdapter;
import me.jeeson.android.retrofitsoapsample.databinding.ActivityMainBinding;
import me.jeeson.android.retrofitsoapsample.webservice.RetrofitGenerator;
import me.jeeson.android.retrofitsoapsample.webservice.request.RequestBody;
import me.jeeson.android.retrofitsoapsample.webservice.request.RequestEnvelope;
import me.jeeson.android.retrofitsoapsample.webservice.request.RequestModel;
import me.jeeson.android.retrofitsoapsample.webservice.request.User;
import me.jeeson.android.retrofitsoapsample.webservice.response.ResponseEnvelope;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 主类，用于模拟请求每个城市天气情况
 * Created by Jeeson on 16/7/15.
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private WeatherResponseAdapter adapter;
    private List<String> listResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.rvElements.setLayoutManager(new LinearLayoutManager(this));
        listResult =  new ArrayList<String>();
        adapter = new WeatherResponseAdapter(listResult);
        binding.rvElements.setAdapter(adapter);

    }

    /**
     * 根据城市名称获取天气
     * @param view
     */
    public void sendRequest(View view) {
        if(TextUtils.isEmpty(binding.etCityName.getText())) {
            Toast.makeText(this, getString(R.string.make_request), Toast.LENGTH_SHORT).show();
        } else {
            hideKeyboard();
            getWeatherbyCityName();
        }
    }

    /**
     * 获取Github用户排行列表
     * @param view
     */
    public void sendRequest2(View view) {
        getUsers();
    }

    /**
     * 根据城市名获取天气情况
     * @return
     */
    public void getWeatherbyCityName() {
        binding.progressbar.setVisibility(View.VISIBLE);
        RequestEnvelope requestEnvelop = new RequestEnvelope();
        RequestBody requestBody = new RequestBody();
        RequestModel requestModel = new RequestModel();
        requestModel.theCityName = binding.etCityName.getText().toString();
        requestModel.cityNameAttribute = "http://WebXml.com.cn/";
        requestBody.getWeatherbyCityName = requestModel;
        requestEnvelop.body = requestBody;
        Call<ResponseEnvelope> call = RetrofitGenerator.getWeatherInterfaceApi().getWeatherbyCityName(requestEnvelop);
        call.enqueue(new Callback<ResponseEnvelope>() {
            @Override
            public void onResponse(Response<ResponseEnvelope> response) {
                binding.progressbar.setVisibility(View.GONE);
                ResponseEnvelope responseEnvelope = response.body();
                if (responseEnvelope != null ) {
                    List<String> list = responseEnvelope.body.getWeatherbyCityNameResponse.result;
                    listResult.clear();
                    listResult.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("getWeatherbyCityName", t.getMessage());
                binding.progressbar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 获取Github用户排行列表
     * @return
     */
    public void getUsers() {
        binding.progressbar.setVisibility(View.VISIBLE);
        Call<List<User>> call = RetrofitGenerator.getGithubInterfaceApi().getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response) {
                binding.progressbar.setVisibility(View.GONE);
                List<User> responseEnvelope = response.body();
                if (responseEnvelope != null ) {
                    List<String> list = new ArrayList<>(responseEnvelope.size());
                    for(User user : responseEnvelope) {
                        list.add(user.getLogin());
                    }
                    listResult.clear();
                    listResult.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("getUsers", t.getMessage());
                binding.progressbar.setVisibility(View.GONE);
            }
        });
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }
}
