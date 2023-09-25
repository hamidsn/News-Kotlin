package au.com.news.coding.ui.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import au.com.news.coding.data.remote.model.Source
import au.com.news.coding.ui.viewmodel.SourceViewModel

@Composable
fun SourceScreen(viewModel: SourceViewModel = hiltViewModel()) {

    val sources by viewModel.sources.collectAsState(initial = emptyList())
    val uiViewStates by remember { mutableStateOf(viewModel.uiSourceStates) }

    when (uiViewStates.value) {

        is UiSourceStates.SourceState -> {
            SourcesListScreen(viewModel, sources)
        }

        is UiSourceStates.SourcesErrorState -> {
            ErrorScreen(
                (uiViewStates.value as? UiSourceStates.SourcesErrorState)?.message
                    ?: "Unknown Error",
                Modifier.fillMaxSize()
            ) { viewModel.getSources() }
        }

        UiSourceStates.SourcesLoadingState -> LoadingScreen(modifier = Modifier.fillMaxSize())

    }
}

@Composable
private fun SourcesListScreen(
    viewModel: SourceViewModel,
    sources: List<Source>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .draggable(
                state = viewModel.dragState.value!!,
                orientation = Orientation.Horizontal,
                onDragStarted = { },
                onDragStopped = {
                    viewModel.updateTabIndexBasedOnSwipe()
                }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(weight = 1f, fill = false)
                .verticalScroll(rememberScrollState())
        ) {
            GroupedCheckbox(
                sources,
                //filterList
            ) { source: Source, selected: Boolean ->
                viewModel.updateSourceSelection(
                    source = source,
                    selected = selected
                )
            }
        }
    }
}

@Composable
fun GroupedCheckbox(
    mItemList: List<Source>,
    updateSelection: (source: Source, selected: Boolean) -> Unit
) {
    mItemList.forEachIndexed { index, item ->
        val isChecked = item.selected
        Row(
            modifier = Modifier
                .padding(1.dp)
                .sizeIn(
                    minWidth = 48.dp,
                    minHeight = 48.dp
                )
                .semantics(mergeDescendants = true) { }
                .toggleable(
                    value = isChecked,
                    enabled = true,
                    role = Role.Checkbox,
                    onValueChange = { updateSelection(item, it) },
                ),
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Checkbox(
                checked = isChecked,
                onCheckedChange = null,
                enabled = true,
                modifier = Modifier.padding(16.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Magenta,
                    uncheckedColor = Color.DarkGray,
                    checkmarkColor = Color.Cyan
                )
            )
            Text(text = item.name)
        }
    }
}