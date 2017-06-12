package me.jeeson.android.retrofitsoapsample.webservice;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;

/**
 * Retrofit变量初始化
 * Created by Jeeson on 16/7/16.
 */
public class RetrofitGenerator {
    public final static String WEATHER_BASE_URL = "http://www.webxml.com.cn/WebServices/";
    public final static String GITHUB_BASE_URL = "https://api.github.com/";

    public static WeatherInterfaceApi weatherInterfaceApi;
    public static GithubInterfaceApi githubInterfaceApi;

    private static Strategy strategy = new AnnotationStrategy();
    private static Serializer serializer = new Persister(strategy);

    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

   private static Retrofit.Builder retrofitBuilder =  new Retrofit.Builder()
           .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
           .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        /*okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Content-Type", "text/xml;charset=UTF-8")   // 对于SOAP 1.1， 如果是soap1.2 应是Content-Type: application/soap+xml; charset=utf-8
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });*/

        OkHttpClient client = okHttpClient.connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = retrofitBuilder.baseUrl(baseUrl).client(client).build();
        return retrofit.create(serviceClass);
    }

    public static WeatherInterfaceApi getWeatherInterfaceApi() {
        if(weatherInterfaceApi == null) {
            weatherInterfaceApi = createService(WeatherInterfaceApi.class, WEATHER_BASE_URL);
        }
        return weatherInterfaceApi;

    }

    public static GithubInterfaceApi getGithubInterfaceApi() {
        if(githubInterfaceApi == null) {
            githubInterfaceApi = createService(GithubInterfaceApi.class, GITHUB_BASE_URL);
        }
        return githubInterfaceApi;

    }
}
