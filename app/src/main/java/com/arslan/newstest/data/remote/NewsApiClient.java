package com.arslan.newstest.data.remote;

import com.arslan.newstest.models.NewsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.arslan.newstest.data.remote.Api.BASE_ULR;

public class NewsApiClient implements INewsApiClient {
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_ULR)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static NewsApi client = retrofit.create(NewsApi.class);

    @Override
    public void getNewsHeadlines(String language, String apiKey, int page, int pageSize, final INewsApiClient.NewsCallBack callBack) {
        Call<NewsModel> call = client.getNewsHeadlines(language, apiKey, page, pageSize);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.onSuccess(response.body().getArticles());
                } else {
                    callBack.onFailure(new Exception("Response is empty" + response.code()));
                }

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                callBack.onFailure(new Exception(t));
            }
        });
    }

    public interface NewsApi {
        @GET("v2/top-headlines")
        Call<NewsModel> getNewsHeadlines(
                @Query("country") String language,
                @Query("apiKey") String apiKey,
                @Query("page") int page,
                @Query("pageSize")int pageSize);
    }
}
