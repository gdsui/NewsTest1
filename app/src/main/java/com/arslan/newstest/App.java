package com.arslan.newstest;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.arslan.newstest.data.NewsRepository;
import com.arslan.newstest.data.local.NewsDatabase;
import com.arslan.newstest.data.remote.INewsApiClient;
import com.arslan.newstest.data.remote.INewsStorage;
import com.arslan.newstest.data.remote.NewsApiClient;
import com.arslan.newstest.data.remote.NewsStorage;

public class App extends Application {
    public static INewsApiClient iNewsApiClient;
    public static NewsRepository newsRepository;
    public static NewsDatabase newsDataBase;
    public static INewsStorage iNewsStorage;
    public static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        iNewsApiClient = new NewsApiClient();
        newsDataBase = Room.databaseBuilder(instance,
                NewsDatabase.class, "news.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        iNewsStorage = new NewsStorage(newsDataBase.newsDao());
        newsRepository = new NewsRepository(iNewsApiClient, iNewsStorage, newsDataBase.newsDao());
    }
}
