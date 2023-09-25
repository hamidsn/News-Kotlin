package au.com.news.coding.domain.repositories

import au.com.news.coding.core.util.Resource
import au.com.news.coding.data.remote.model.HeadlineModel
import au.com.news.coding.data.remote.model.SourceDto
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getSources(): Flow<Resource<SourceDto>>
    suspend fun getHeadlines(sources: String): Flow<Resource<HeadlineModel>>
}