package au.com.news.coding.di

import android.content.Context
import androidx.room.Room
import au.com.news.coding.data.local.AppDatabase
import au.com.news.coding.data.local.NewsDao
import au.com.news.coding.data.repository.LocalSourceDatabaseRepositoryImpl
import au.com.news.coding.domain.repositories.LocalSourceDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideSourcesDao(appDatabase: AppDatabase): NewsDao {
        return appDatabase.newsDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun getLocalDatabaseRepository(
        db: AppDatabase
    ): LocalSourceDatabaseRepository = LocalSourceDatabaseRepositoryImpl(db.newsDao())
}