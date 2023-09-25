package au.com.news.coding.ui.view

import au.com.news.coding.data.remote.model.Source
import kotlinx.coroutines.flow.Flow

sealed class UiSourceStates {
    object SourcesLoadingState : UiSourceStates()
    class SourceState(val sourceList: Flow<List<Source>>) : UiSourceStates()
    class SourcesErrorState(val message: String) : UiSourceStates()

}
