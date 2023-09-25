package au.com.news.coding.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import au.com.news.coding.data.local.AppDatabase
import au.com.news.coding.data.local.NewsDao
import au.com.news.coding.data.remote.model.Article
import au.com.news.coding.data.remote.model.Source
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After


import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NewsDataBaseTest {
    private lateinit var dataDao: NewsDao
    private lateinit var db: AppDatabase
    private lateinit var source1: Source
    private lateinit var source2: Source
    private lateinit var article: Article

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        dataDao = db.newsDao()

        source1 = Source(
            id = "123456789",
            name = "Hamid",
            description = "code challenge",
            url = "www.google.com",
            category = "test",
            language = "en",
            country = "Australia",
            selected = false
        )

        source2 = Source(
            id = "987654321",
            name = "Andrew",
            description = "code compile",
            url = "www.yahoo.com",
            category = "compile",
            language = "es",
            country = "Spain",
            selected = true
        )

        article = Article(
            description = "test",
            url = "www.test.com",
            author = "uniting",
            saved = true,
            title = "challenge",
            content = "",
            source = Source("id", "name", "des", "url", "cat", "lan", "cou", true),
            publishedAt = "newspaper",
            urlToImage = ""
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_insert_source() = runBlocking {
        dataDao.insertSource(source1)
        assertEquals(dataDao.getSourceById("123456789")?.name, source1.name)
    }

    @Test
    fun test_insert_article() = runBlocking {
        dataDao.insertArticle(article)
        assertEquals(dataDao.getArticleById("www.test.com")?.author, article.author)
    }

    @Test
    fun test_not_inserted_source() = runBlocking {
        dataDao.insertSource(source1)
        dataDao.insertSource(source2)
        assertEquals(dataDao.getSourceById("wrongId")?.name, null)
    }

    @Test
    fun test_delete_source() = runBlocking {
        dataDao.insertSource(source1)
        dataDao.insertSource(source2)
        dataDao.deleteSource(source2)
        assertEquals(dataDao.getSourceById(source2.id)?.name, null)
    }

    @Test
    fun test_delete_article() = runBlocking {
        dataDao.insertArticle(article)
        dataDao.deleteArticle(article)
        assertEquals(dataDao.getSourceById("wrongId"), null)
    }

    @Test
    fun test_get_selected_sources_from_db() {
        runBlocking {
            dataDao.insertSource(source1)
            dataDao.insertSource(source2)
        }
    }

    @Test
    fun test_insert_Article_on_conflict_strategy() {
        runBlocking {
            dataDao.insertArticle(article)
            dataDao.insertArticle(article.copy(author = "splitting"))
            assertEquals(dataDao.getArticleById("www.test.com")?.author, "splitting")
        }
    }
}