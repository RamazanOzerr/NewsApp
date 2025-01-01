package com.example.newsapp.adapter;

import static com.example.newsapp.util.Constants.NEWS_URL_INTENT;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.Article;
import com.example.newsapp.ui.savedNews.SavedNewsViewModel;
import com.example.newsapp.ui.webview.ViewNewsActivity;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.NewsViewHolder> {

    private static final String TAG = "NEWS ADAPTER";

    // the list of saved articles
    private List<Article> articles;

    // context: need it to open viewNewsActivity
    private final Context context;

    // constructor
    public SavedNewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        // get current article and bind it
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());

        // set click listener to open viewNewsActivity
        holder.currentNews.setOnClickListener(view -> {
            openNewsInWebView(article.getUrl()); // Open news in WebView
        });

        // Load image using Glide library
        Glide.with(holder.itemView.getContext())
                .load(article.getUrlToImage())
                .into(holder.imageView);
    }

    // get list of the articles
    public List<Article> getArticles() {
        return articles;
    }

    // view news in a new activity
    private void openNewsInWebView(String url){
        Intent intent = new Intent(context, ViewNewsActivity.class);
        intent.putExtra(NEWS_URL_INTENT, url);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
    }

    // update articles
    public void updateArticles(List<Article> newArticles) {
        this.articles = newArticles;
        notifyDataSetChanged();
    }

    // remove an article at a certain position
    // we call this method from the fragment when the user swipes left an article
    public void removeArticleAt(int position) {
        if (articles != null && position >= 0 && position < articles.size()) {
            articles.remove(position);
            notifyItemRemoved(position);
            Log.d(TAG, "removeArticleAt: article removed: " + position);
        }
    }

    // add an article to a certain position
    // we call this method when the user undo the swipe left to remove operation
    public void addArticleAt(int position, Article article) {
        articles.add(position, article);
        notifyItemInserted(position);
    }


    // view holder
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView imageView;
        LottieAnimationView lottiLikeAnimation;
        LinearLayout currentNews;

        public NewsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.article_image);
            lottiLikeAnimation = itemView.findViewById(R.id.animationView_news_like);
            currentNews = itemView.findViewById(R.id.linear_news);
        }
    }
}
