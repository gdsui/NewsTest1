package com.arslan.newstest.data.remote;

import androidx.lifecycle.LiveData;

import com.arslan.newstest.models.Article;

import java.util.List;

public interface INewsStorage {
    LiveData<List<Article>> getAllLive();
}
