package au.com.news.coding.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import au.com.news.coding.data.local.AppDatabase
import au.com.news.coding.data.local.NewsDao
import au.com.news.coding.data.repository.LocalSourceDatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.junit.After
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FakeNewsDataBase {
    private lateinit var dataDao: NewsDao
    private lateinit var db: AppDatabase


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Provides
    @Singleton
    fun provideDataBaseDao(): LocalSourceDatabaseRepositoryImpl {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        return LocalSourceDatabaseRepositoryImpl(db.newsDao())
    }

}