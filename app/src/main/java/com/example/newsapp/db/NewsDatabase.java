package com.example.newsapp.db;

import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;

import com.example.newsapp.model.Article;

// export schema is false because this is a small project and schema changes are unlikely
@Database(entities = {Article.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class NewsDatabase extends RoomDatabase {

    public abstract NewsDao getNewsDao();

    private static volatile NewsDatabase instance;
    private static final Object LOCK = new Object();

    public static NewsDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = createDatabase(context);
                }
            }
        }
        return instance;
    }

    private static NewsDatabase createDatabase(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                NewsDatabase.class,
                "article_db.db"
        ).build();
    }
}
