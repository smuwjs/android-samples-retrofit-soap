package me.jeeson.android.retrofitsoapsample.webservice;

import me.jeeson.android.retrofitsoapsample.webservice.request.RequestEnvelope;
import me.jeeson.android.retrofitsoapsample.webservice.response.ResponseEnvelope;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 接口请求
 * Created by Jeeson on 16/7/15.
 */
public interface WeatherInterfaceApi {

    @Headers({"Content-Type: text/xml;charset=UTF-8", "SOAPAction: http://WebXml.com.cn/getWeatherbyCityName"})//请求的Action，类似于方法名
    @POST("WeatherWebService.asmx")
    Call<ResponseEnvelope> getWeatherbyCityName(@Body RequestEnvelope requestEnvelope);

}
