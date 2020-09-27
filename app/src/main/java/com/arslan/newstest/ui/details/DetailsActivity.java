package com.arslan.newstest.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.arslan.newstest.R;
import com.arslan.newstest.models.Article;
import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {
    private TextView tvTittle, tvDesc, tvUrl, tvPublishedAt, tvAuthor;
    private ImageView imageNews;
    private static final String ARTICLE = "article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        initialization();
        getDataFromIntent();
    }
    @SuppressLint("SetTextI18n")
    private void getDataFromIntent() {
        if (getIntent() != null) {
            Article article = (Article) getIntent().getSerializableExtra(ARTICLE);
            if (article != null) {
                tvTittle.setText(article.getTitle());
                tvDesc.setText(article.getDescription());
                Glide.with(imageNews.getContext()).load(article.getUrlToImage()).into(imageNews);
                if (article.getAuthor() != null) {
                    tvAuthor.setText(getString(R.string.res_author) + article.getAuthor());
                } else {
                    tvAuthor.setText("unknown_author");
                }
                tvUrl.setText("news_url" + article.getUrl());
                tvPublishedAt.setText(getString(R.string.res_date) + article.getPublishedAt());
            }
        }
    }

    private void initialization() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("details");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initViews() {
        tvTittle = findViewById(R.id.tv_tittle);
        tvDesc = findViewById(R.id.tv_desc);
        imageNews = findViewById(R.id.image_news);
        tvUrl = findViewById(R.id.tv_url);
        tvPublishedAt = findViewById(R.id.tv_publishedAt);
        tvAuthor = findViewById(R.id.tv_author);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}