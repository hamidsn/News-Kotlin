package au.com.news.coding.domain.repositories

import au.com.news.coding.data.remote.model.Article
import au.com.news.coding.data.remote.model.Source
import kotlinx.coroutines.flow.Flow

interface LocalSourceDatabaseRepository {

    fun getAllSources(): Flow<List<Source>>

    suspend fun insertSource(source: Source): Long

    suspend fun deleteSource(source: Source)

    suspend fun getSourceById(sourceId: String): Source?

    fun getSourceBySelection(selection: Boolean): Flow<List<Source>>

    suspend fun updateSource(source: Source)

    fun getAllArticles(): Flow<List<Article>>

    suspend fun deleteArticle(article: Article)

    suspend fun insertArticle(article: Article)

    suspend fun getArticleById(articleId: String): Article?

}