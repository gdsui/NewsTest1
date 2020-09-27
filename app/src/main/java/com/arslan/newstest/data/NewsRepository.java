package com.arslan.newstest.data;

import androidx.lifecycle.LiveData;

import com.arslan.newstest.data.local.NewsDao;
import com.arslan.newstest.data.remote.INewsApiClient;
import com.arslan.newstest.data.remote.INewsStorage;
import com.arslan.newstest.models.Article;

import java.util.List;

public class NewsRepository implements INewsApiClient, INewsStorage {
    private INewsApiClient iNewsApiClient;
    private INewsStorage iNewsStorage;
    private NewsDao newsDao;

    public NewsRepository(INewsApiClient iNewsApiClient, INewsStorage iNewsStorage, NewsDao newsDao) {
        this.iNewsApiClient = iNewsApiClient;
        this.iNewsStorage = iNewsStorage;
        this.newsDao = newsDao;
    }

    @Override
    public void getNewsHeadlines(String language, String apiKey, int page, int pageSize, final NewsCallBack callBack) {
        iNewsApiClient.getNewsHeadlines(language, apiKey, page, pageSize, new NewsCallBack() {
            @Override
            public void onSuccess(List<Article> result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onFailure(Exception e) {
                callBack.onFailure(e);
            }
        });
    }

    @Override
    public LiveData<List<Article>> getAllLive() {
        return iNewsStorage.getAllLive();
    }
}
