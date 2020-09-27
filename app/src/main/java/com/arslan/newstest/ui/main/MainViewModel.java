package com.arslan.newstest.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arslan.newstest.App;
import com.arslan.newstest.data.remote.INewsApiClient;
import com.arslan.newstest.models.Article;

import java.util.List;

import static com.arslan.newstest.data.remote.Api.API_KEY;

public class MainViewModel  extends ViewModel {
    MutableLiveData<List<Article>> news = new MutableLiveData<>();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    LiveData<List<Article>> newsData = App.iNewsStorage.getAllLive();

     void receiveData(int page, int pageSize) {
        App.newsRepository.getNewsHeadlines("ru", API_KEY, page, pageSize,
                new INewsApiClient.NewsCallBack() {
                    @Override
                    public void onSuccess(List<Article> result) {
                        news.setValue(result);
                        isLoading.setValue(true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("ololo", "error" + e);
                    }
                });
    }
}
