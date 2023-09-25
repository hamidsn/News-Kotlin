package au.com.news.coding.ui.viewmodel

import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.news.coding.core.util.Resource
import au.com.news.coding.data.remote.model.Article
import au.com.news.coding.data.remote.model.HeadlineModel
import au.com.news.coding.data.remote.model.Source
import au.com.news.coding.data.remote.model.SourceDto
import au.com.news.coding.domain.repositories.LocalSourceDatabaseRepository
import au.com.news.coding.domain.repositories.NewsRepository
import au.com.news.coding.ui.util.isEnglish
import au.com.news.coding.ui.view.UiHeadlineStates
import au.com.news.coding.ui.view.UiSourceStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SourceViewModel @Inject constructor(
    private val remoteRepository: NewsRepository,
    private val localDbRepository: LocalSourceDatabaseRepository
) : ViewModel() {

    private val _uiSourcesStates: MutableState<UiSourceStates> =
        mutableStateOf(UiSourceStates.SourcesLoadingState)
    val uiSourceStates: MutableState<UiSourceStates> = _uiSourcesStates
    private val _uiHeadlinesStates: MutableState<UiHeadlineStates> =
        mutableStateOf(UiHeadlineStates.HeadlinesLoadingState)
    val uiHeadlinesStates: MutableState<UiHeadlineStates> = _uiHeadlinesStates

    //tabs
    private val _tabIndex: MutableLiveData<Int> = MutableLiveData(1)
    val tabIndex: LiveData<Int> = _tabIndex
    val tabs = listOf("Headlines", "Sources", "Saved")
    var isSwipeToTheLeft: Boolean = false
    private val draggableState = DraggableState { delta ->
        isSwipeToTheLeft = delta > 0
    }
    private val _dragState = MutableLiveData<DraggableState>(draggableState)
    val dragState: LiveData<DraggableState> = _dragState
    var offlineList: List<Article>? = arrayListOf()

    fun updateTabIndexBasedOnSwipe() {
        _tabIndex.value = when (isSwipeToTheLeft) {
            true -> Math.floorMod(_tabIndex.value!!.plus(1), tabs.size)
            false -> Math.floorMod(_tabIndex.value!!.minus(1), tabs.size)
        }
    }

    fun updateTabIndex(i: Int) {
        _tabIndex.value = i
    }
    ///tabs above

    private var _sources = localDbRepository.getAllSources()
    var sources: Flow<List<Source>> = _sources

    private var _saves = localDbRepository.getAllArticles()
    var saves: Flow<List<Article>> = _saves

    private var _selection = localDbRepository.getSourceBySelection(true)
    var selection: Flow<List<Source>> = _selection

    init {
        getSources()
        getHeadlines()
    }

    fun getHeadlines() {
        _uiHeadlinesStates.value = UiHeadlineStates.HeadlinesLoadingState
        val result = MutableStateFlow<Resource<HeadlineModel>>(value = Resource.Loading())
        viewModelScope.launch {
            var selectedSources = ""
            selection.first().filter { it.selected }.forEach {
                selectedSources = selectedSources + it.id + ","
            }

            remoteRepository.getHeadlines(selectedSources).collect() {
                result.value = it
            }
            when (result.value) {
                is Resource.Error -> _uiHeadlinesStates.value =
                    UiHeadlineStates.HeadlinesErrorState(message = result.value.message ?: "")

                is Resource.Loading -> _uiHeadlinesStates.value =
                    UiHeadlineStates.HeadlinesLoadingState

                is Resource.Success -> {
                    offlineList = result.value.data?.articles
                    _uiHeadlinesStates.value = result.value.data?.articles?.let {
                        UiHeadlineStates.HeadlineState(headLineList = it)
                    }
                        ?: UiHeadlineStates.HeadlinesEmptyState
                }
            }

        }
    }

    fun getSources() {
        _uiSourcesStates.value = UiSourceStates.SourcesLoadingState
        val result = MutableStateFlow<Resource<SourceDto>>(value = Resource.Loading())
        viewModelScope.launch {
            remoteRepository.getSources().collect() {
                result.value = it
            }
            when (result.value) {
                is Resource.Error -> _uiSourcesStates.value =
                    UiSourceStates.SourcesErrorState(message = result.value.message ?: "")

                is Resource.Loading -> _uiSourcesStates.value = UiSourceStates.SourcesLoadingState
                is Resource.Success -> {
                    result.value.data?.sources?.filter { it.language.isEnglish() }?.forEach {
                        it saveToDatabase localDbRepository
                    }
                }
            }
            _uiSourcesStates.value = UiSourceStates.SourceState(sourceList = sources)
        }
    }

    fun saveOrDeleteArticle(article: Article) {
        article saveOrDeleteDatabase localDbRepository
    }

    fun updateSourceSelection(source: Source, selected: Boolean) {
        source.copy(selected = selected) updateDatabase localDbRepository
    }

    private infix fun Source.saveToDatabase(localDbRepository: LocalSourceDatabaseRepository) {
        viewModelScope.launch {
            localDbRepository.insertSource(this@saveToDatabase)
        }
    }

    private infix fun Source.updateDatabase(localDbRepository: LocalSourceDatabaseRepository) {
        viewModelScope.launch {
            localDbRepository.updateSource(this@updateDatabase)
            getHeadlines()
        }
    }

    private infix fun Article.saveOrDeleteDatabase(localDbRepository: LocalSourceDatabaseRepository) {
        viewModelScope.launch {
            if (localDbRepository.getArticleById(this@saveOrDeleteDatabase.url) == null) {
                localDbRepository.insertArticle(this@saveOrDeleteDatabase)
                updateSavedFlag(this@saveOrDeleteDatabase, true)
            } else {
                updateSavedFlag(this@saveOrDeleteDatabase, false)
            }
        }
    }

    private fun updateSavedFlag(article: Article, flag: Boolean) {
        viewModelScope.launch {
            offlineList = offlineList?.map {
                if (localDbRepository.getArticleById(article.url)?.url != it.url)
                    it
                else
                    it.copy(saved = flag)
            }
            offlineList?.apply {
                _uiHeadlinesStates.value = UiHeadlineStates.HeadlineState(headLineList = this)
                if (!flag) {
                    localDbRepository.deleteArticle(article)
                }
            }
        }
    }
}
