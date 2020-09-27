package com.arslan.newstest.ui.main.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arslan.newstest.R;
import com.arslan.newstest.models.Article;
import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Article> list;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(List<Article> list) {
        this.list = list;
    }

    public void updateAdapter(List<Article> updateList){
        list = updateList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTittle, tvDesc;
        private ImageView imageNews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tv_tittle_news);
            tvDesc = itemView.findViewById(R.id.tv_desc_news);
            imageNews = itemView.findViewById(R.id.image_news);
            itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(getAdapterPosition());
            });
        }

        public void bind(Article article) {
            tvTittle.setText(article.getTitle());
            tvDesc.setText(article.getDescription());
            Glide.with(imageNews.getContext()).load(article.getUrlToImage()).into(imageNews);
        }
    }
}
