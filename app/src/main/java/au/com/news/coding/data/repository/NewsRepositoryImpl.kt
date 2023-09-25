package au.com.news.coding.data.repository

import au.com.news.coding.core.util.Resource
import au.com.news.coding.data.remote.NewsApi
import au.com.news.coding.data.remote.model.HeadlineModel
import au.com.news.coding.data.remote.model.SourceDto
import au.com.news.coding.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepositoryImpl(private val api: NewsApi) : NewsRepository {
    override suspend fun getSources(): Flow<Resource<SourceDto>> = flow {
        emit(Resource.Loading())
        val sources = try {
            api.sources()

        } catch (e: IOException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    e.message ?: "Couldn't reach server, check your internet connection."
                )
            )
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Oops, something went wrong!"))
            null
        }
        sources?.apply {
            emit(Resource.Success(this))
        }
    }

    override suspend fun getHeadlines(sources: String): Flow<Resource<HeadlineModel>> = flow {
        emit(Resource.Loading())
        if (sources.isEmpty()) {
            emit(Resource.Error("No source selected"))
        } else {
            val headLines = try {
                api.headLines(sources)

            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        e.message ?: "Couldn't reach server, check your internet connection."
                    )
                )
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "Oops, something went wrong!"))
                null
            }
            headLines?.apply {
                emit(Resource.Success(this))
            }
        }

    }
}