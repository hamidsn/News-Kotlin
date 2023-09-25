package au.com.news.coding.ui.view

import au.com.news.coding.data.remote.model.Article

sealed class UiHeadlineStates {
    object HeadlinesLoadingState : UiHeadlineStates()
    object HeadlinesEmptyState : UiHeadlineStates()
    class HeadlineState(val headLineList: List<Article>) : UiHeadlineStates()
    class HeadlinesErrorState(val message: String) : UiHeadlineStates()
}
