package com.arslan.newstest.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.arslan.newstest.models.Article;

@Database(entities = {Article.class},version = 1,exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
        public abstract NewsDao newsDao();

}