package com.example.newsapp.adapter;

import static com.example.newsapp.util.Constants.NEWS_URL_INTENT;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.Article;
import com.example.newsapp.ui.webview.ViewNewsActivity;

import java.util.ArrayList;
import java.util.List;

// recyclerview adapter for home fragment and search news fragment
// lists the breaking news (home fragment)
// lists the searches news (search news fragment)
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private static final String TAG = "NEWS ADAPTER";
    // duration for like and unlike animations
    private static final int ANIMATION_DURATION = 1000;

    // the list of the liked/saved articles
    private List<Article> savedArticles;

    // the list of the articles
    private List<Article> articles;

    // time for double click feature
    private static final long DOUBLE_CLICK_TIME_DELTA = 300; // Time threshold for double-click (in ms)
    private long lastClickTime;

    // colors
    private final int colorGreen;
    private final int colorWhite;

    // context: need it to open viewNewsActivity
    private final Context context;

    // Handler for managing delayed actions (e.g., distinguishing single vs. double clicks).
    private Handler handler;

    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        lastClickTime = 0;
        savedArticles = new ArrayList<>();
        this.context = context;
        colorGreen = ContextCompat.getColor(context, R.color.primary_green_saturated);
        colorWhite = ContextCompat.getColor(context, R.color.white);
        handler = new Handler();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());

        // Set background color based on the article's like status.
        if(article.isLiked()){
            holder.currentNews.setBackgroundColor(colorGreen);
        } else {
            holder.currentNews.setBackgroundColor(colorWhite);
        }

        // Set click listener for single and double-click actions.
        holder.currentNews.setOnClickListener(view -> {
            long clickTime = System.currentTimeMillis();

            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // Double-click detected
                handler.removeCallbacksAndMessages(null); // Cancel single-click action
                playAnimation(holder, article);
            } else {
                // Handle single click with a delay to differentiate from double-click
                handler.postDelayed(() -> {
                    openNewsInWebView(article.getUrl()); // Open news in WebView
                }, DOUBLE_CLICK_TIME_DELTA);
            }
            lastClickTime = clickTime;
        });

        // Load article image using Glide, or use a placeholder if loading fails.
        try{
            // Load image using Glide
            Glide.with(holder.itemView.getContext())
                    .load(article.getUrlToImage())
                    .into(holder.imageView);
        } catch (Exception e){
            //do nothing
            holder.imageView.setImageResource(R.drawable.img_1);
        }

    }

    // get the list of the saved/liked activities
    public List<Article> getSavedArticles(){
        return savedArticles;
    }

    // view news in a new activity
    private void openNewsInWebView(String url){
        Intent intent = new Intent(context, ViewNewsActivity.class);
        intent.putExtra(NEWS_URL_INTENT, url);
        context.startActivity(intent);
    }

    // handle like
    private void markAsLiked(NewsViewHolder holder, Article article){
        holder.currentNews.setBackgroundColor(colorGreen);
        article.setLiked(true);
        savedArticles.add(article);
    }

    // handle unlike
    private void undoLike(NewsViewHolder holder, Article article){
        holder.currentNews.setBackgroundColor(colorWhite);
        article.setLiked(false);
        savedArticles.remove(article);
    }

    // play like or unlike animation
    private void playAnimation(NewsViewHolder holder, Article article){
        holder.lottiLikeAnimation.setVisibility(View.VISIBLE);
        holder.lottiLikeAnimation.bringToFront();
        holder.lottiLikeAnimation.playAnimation();

        if(article.isLiked()){
            undoLike(holder, article);
            holder.lottiLikeAnimation.setAnimation(R.raw.unlike_anim);
        } else {
            markAsLiked(holder, article);
            holder.lottiLikeAnimation.setAnimation(R.raw.like_animation);
        }

        // view animation during the ANIMATION_DURATION
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.lottiLikeAnimation.setVisibility(View.INVISIBLE);
            }
        }, ANIMATION_DURATION);
    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
    }

    // method to update articles
    public void updateArticles(List<Article> newArticles) {
        this.articles = newArticles;
        notifyDataSetChanged();
    }

    //view holder
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
