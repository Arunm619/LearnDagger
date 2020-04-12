package io.arunbuilds.learndagger.data.remote.interceptor;

import java.io.IOException;

import io.arunbuilds.learndagger.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalUrl = originalRequest.url();
        HttpUrl url = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build();
        Request.Builder requestBuilder = originalRequest.newBuilder().url(url);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
