package au.com.news.coding.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import au.com.news.coding.data.remote.model.Article
import au.com.news.coding.data.remote.model.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM sources")
    fun getAllSources(): Flow<List<Source>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSource(source: Source): Long

    @Delete
    suspend fun deleteSource(source: Source)

    @Query("select * from sources where id = :sourceId ")
    suspend fun getSourceById(sourceId: String): Source?

    @Query("select * from sources where selected = :selection ")
    fun getSourceBySelection(selection: Boolean): Flow<List<Source>>

    @Update
    suspend fun updateSource(source: Source)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Query("select * from articles where url = :url ")
    suspend fun getArticleById(url: String): Article?
}