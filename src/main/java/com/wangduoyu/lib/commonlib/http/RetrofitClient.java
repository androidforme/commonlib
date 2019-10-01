package com.wangduoyu.lib.commonlib.http;



import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wangduoyu.lib.commonlib.BuildConfig;
import com.wangduoyu.lib.commonlib.http.interceptor.BaseInterceptor;
import com.wangduoyu.lib.commonlib.utils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 自定义的Retrofit客户端，单例模式方便使用
 */
public class RetrofitClient {

    /**
     * Retrofit实例
     */
    private static Retrofit mRetrofit;

    /**
     * 获取Retrofit实例
     *
     * @return Retrofit实例
     */
    public static Retrofit retrofit() {
        LogUtils.d();
        if (null == mRetrofit) {
            // 初始化 OkHttpClient
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.addInterceptor(new BaseInterceptor());
            //日志拦截器
            if (BuildConfig.DEBUG) {
                LogUtils.d("日志拦截器");
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpBuilder.addNetworkInterceptor(logging);
            }

            // 初始化Retrofit
            mRetrofit = new Retrofit.Builder()
                    // 基础地址
                    .baseUrl(Urls.BASE_URL)
                    // JSON转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    // RxJava2适配器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    // 使用okHttp
                    .client(okHttpBuilder.build())
                    .build();
        }
        return mRetrofit;
    }

    public static void reset() {
        mRetrofit = null;
    }

    /**
     * 创建一个API的实例
     *
     * @param service API接口类
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> service) {
        return retrofit().create(service);
    }

    /**
     * 订阅网络请求
     *
     * @param observable 可观察者，即被观察者
     * @param observer   观察者
     */
    public static void subscribe(Observable<?> observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 构建一个JSON请求体
     *
     * @param content JSON格式请求体的字符串
     * @return
     */
    public static RequestBody createJsonBody(JsonObject content) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content
                .toString());
    }

}
