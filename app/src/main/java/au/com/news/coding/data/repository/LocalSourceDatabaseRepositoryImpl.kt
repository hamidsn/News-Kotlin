package au.com.news.coding.data.repository

import au.com.news.coding.data.local.NewsDao
import au.com.news.coding.data.remote.model.Article
import au.com.news.coding.data.remote.model.Source
import au.com.news.coding.domain.repositories.LocalSourceDatabaseRepository
import kotlinx.coroutines.flow.Flow

class LocalSourceDatabaseRepositoryImpl(
    private val dao: NewsDao
) : LocalSourceDatabaseRepository {

    override fun getAllSources(): Flow<List<Source>> {
        return dao.getAllSources()
    }

    override suspend fun insertSource(source: Source): Long {
        return dao.insertSource(source)
    }

    override suspend fun deleteSource(source: Source) {
        dao.deleteSource(source)
    }

    override suspend fun getSourceById(sourceId: String): Source? {
        return dao.getSourceById(sourceId)
    }

    override fun getSourceBySelection(selection: Boolean): Flow<List<Source>> {
        return dao.getSourceBySelection(selection)
    }

    override suspend fun updateSource(source: Source) {
        dao.updateSource(source)
    }

    override fun getAllArticles(): Flow<List<Article>> {
        return dao.getAllArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        dao.deleteArticle(article)
    }

    override suspend fun insertArticle(article: Article) {
        dao.insertArticle(article)
    }

    override suspend fun getArticleById(articleId: String): Article? {
        return dao.getArticleById(articleId)
    }

}