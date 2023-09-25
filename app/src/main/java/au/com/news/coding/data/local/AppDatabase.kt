package au.com.news.coding.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import au.com.news.coding.data.remote.model.Article
import au.com.news.coding.data.remote.model.Source

@Database(entities = [Source::class, Article::class], version = 4)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        const val DATABASE_NAME = "News"
    }
}