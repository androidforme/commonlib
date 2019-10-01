package com.wangduoyu.lib.commonlib.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 基础拦截器，用于封装通用参数
 * <p>
 * 2017-01-17
 *
 * @author WuMeng
 * @version 1.0
 */
public class BaseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        // 添加公共请求头
        Request request = chain.request().newBuilder()
                // Authorization => 令牌（String，格式形如Bearer {token}）
              //  .addHeader("Authorization", auth)
                .build();

        // 解析服务器响应头
        Response response = chain.proceed(request);
//        // 解析并保存认证信息(不为空才保存)
//        String authorization = response.header("Authorization");
//        if (StringUtils.isNotEmpty(authorization)) {
//            NetSharedPref.setAuthorization(authorization);
//        }

        return response;
    }

}
