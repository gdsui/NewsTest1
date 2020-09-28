package com.arslan.newstest.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arslan.newstest.App;
import com.arslan.newstest.R;
import com.arslan.newstest.models.Article;
import com.arslan.newstest.ui.details.DetailsActivity;
import com.arslan.newstest.ui.main.recycler.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ARTICLE = "article";
    private MainViewModel mViewModel;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<Article> list = new ArrayList<>();
    private ProgressBar isLoading, progressDown;
    private NestedScrollView nestedScrollView;
    private SwipeRefreshLayout swipeUp;
    private int pageSize = 10, page = 1;
    private ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initialization();
        createRecycler();
        getDataFromLiveData();
        listeners();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        isLoading = findViewById(R.id.progress_bar);
        nestedScrollView = findViewById(R.id.nested_scroll);
        progressDown = findViewById(R.id.progress_bar_down);
        swipeUp = findViewById(R.id.swipe_up);
    }

    private void initialization() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getSupportActionBar().setTitle("News");
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private void createRecycler() {
        adapter = new NewsAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void getDataFromLiveData() {
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            mViewModel.receiveData(page, pageSize);
            if (App.newsDataBase.newsDao().getAll() != null) App.newsDataBase.newsDao().deleteAll();

            mViewModel.news.observe(this, result -> {
                App.newsDataBase.newsDao().insert(result);
                list.addAll(result);
                adapter.updateAdapter(list);
                progressDown.setVisibility(View.GONE);
            });
        } else {
            mViewModel.newsData.observe(this, articles -> {
                if (articles != null) {
                    list.addAll(articles);
                    adapter.updateAdapter(articles);
                    isLoading.setVisibility(View.GONE);
                    progressDown.setVisibility(View.GONE);
                }
            });
        }
        mViewModel.isLoading.observe(this, aBoolean -> {
            if (aBoolean) isLoading.setVisibility(View.GONE);
        });
    }

    private void listeners() {
        adapter.setOnItemClickListener(pos ->
                startActivity(new Intent(this, DetailsActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .putExtra(ARTICLE, list.get(pos))));

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
                            if (pageSize <= mViewModel.news.getValue().size()) {
                                page++;
                           //     pageSize = +10;
                                progressDown.setVisibility(View.VISIBLE);
                                mViewModel.receiveData(page, pageSize);
                            } else {
                                Toast.makeText(this, "Все данные загружены", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "У вас интернет не подключен", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        swipeUp.setOnRefreshListener(() -> {
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
                list.clear();
                page = 1;
                pageSize = 10;
                mViewModel.receiveData(page, pageSize);
                Toast.makeText(this, "Данные успешно обновлены", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "У вас интернет не подключен", Toast.LENGTH_SHORT).show();
            }
            swipeUp.setRefreshing(false);
        });
    }


}