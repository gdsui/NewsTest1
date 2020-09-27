package com.arslan.newstest.data.remote;

import androidx.lifecycle.LiveData;

import com.arslan.newstest.data.local.NewsDao;
import com.arslan.newstest.models.Article;

import java.util.List;

public class NewsStorage implements INewsStorage {
    private NewsDao dao;

    public NewsStorage(NewsDao dao) {
        this.dao = dao;
    }

    @Override
    public LiveData<List<Article>> getAllLive() {
        return dao.getAllLive();
    }
}
