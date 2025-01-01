package com.example.newsapp.db;

import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;

import com.example.newsapp.model.Article;

/**
 * Main database class for the News App. This class represents the Room database.
 * It includes the database configuration and serves as the main access point to the persisted data.

 * - The database stores articles using the `Article` entity.
 * - A `NewsDao` object is provided to perform database operations.
 */

// export schema is false because this is a small project and schema changes are unlikely
@Database(entities = {Article.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class NewsDatabase extends RoomDatabase {

    /**
     * Abstract method to get the Data Access Object (DAO) for performing operations on the database.
     *
     * @return An instance of `NewsDao`.
     */
    public abstract NewsDao getNewsDao();

    // Singleton instance of the database to ensure a single point of access.
    private static volatile NewsDatabase instance;

    // Lock object for thread-safe initialization of the singleton instance.
    private static final Object LOCK = new Object();

    /**
     * Provides the singleton instance of the `NewsDatabase`.
     * Initializes the database if it hasn't been created yet.
     *
     * @param context The application context.
     * @return The singleton instance of the database.
     */
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

    /**
     * Creates the database using the Room library.
     *
     * @param context The application context.
     * @return A new instance of the `NewsDatabase`.
     */
    private static NewsDatabase createDatabase(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                NewsDatabase.class,
                "article_db.db"
        ).build();
    }
}
