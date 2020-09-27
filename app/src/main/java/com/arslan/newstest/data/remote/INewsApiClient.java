package com.arslan.newstest.data.remote;

import com.arslan.newstest.core.BaseCallback;
import com.arslan.newstest.models.Article;

import java.util.List;

public interface INewsApiClient {

    void getNewsHeadlines(String language, String apiKey, int page, int pageSize, NewsCallBack callBack);

    interface NewsCallBack extends BaseCallback<List<Article>> {

        @Override
        void onSuccess(List<Article> result);

        @Override
        void onFailure(Exception e);
    }
}
