package xyz.truehrms.retrofit;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.truehrms.BuildConfig;
import xyz.truehrms.utils.Constant;

public class RetrofitClient {

    private static Retrofit mRetrofit, mRetrofit1;

    private static OkHttpClient getClient() {
        OkHttpClient client;
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        } else {
            client = new OkHttpClient.Builder().build();
        }
        OkHttpClient.Builder builder = client.newBuilder().readTimeout(20000, TimeUnit.SECONDS).connectTimeout(20000, TimeUnit.SECONDS);
        return builder.build();
    }

    public static RetrofitApiService getRetrofitClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return mRetrofit.create(RetrofitApiService.class);
    }

    public static RetrofitApiService getMultipartRetrofitClient(final String token, final String boundary) {
        if (mRetrofit1 == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Content-Type", "multipart/form-data")
                            .header("x-Token", token)
                            .header("boundary", boundary)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

//            OkHttpClient client = httpClient.build();
            mRetrofit1 = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

        }
        return mRetrofit1.create(RetrofitApiService.class);
    }
}
