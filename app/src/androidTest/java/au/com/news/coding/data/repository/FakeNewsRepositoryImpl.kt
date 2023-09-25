package au.com.news.coding.data.repository

import au.com.news.coding.core.util.Resource
import au.com.news.coding.data.remote.NewsApi
import au.com.news.coding.data.remote.model.HeadlineModel
import au.com.news.coding.data.remote.model.SourceDto
import au.com.news.coding.domain.repositories.NewsRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FakeNewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    var gson = Gson()
    private var sourcesData = SourceDto(status = "ok", sources = arrayListOf())


    private var articlesData =
        HeadlineModel(status = "ok", totalResults = 0, articles = arrayListOf())


    override suspend fun getSources(): Flow<Resource<SourceDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = sourcesData
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = null
                )
            )
        }
    }

    override suspend fun getHeadlines(sources: String): Flow<Resource<HeadlineModel>> = flow {
        emit(Resource.Loading())
        try {
            val response = articlesData
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = null
                )
            )
        }
    }
}