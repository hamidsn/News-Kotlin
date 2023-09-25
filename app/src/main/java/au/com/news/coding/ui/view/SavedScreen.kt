package au.com.news.coding.ui.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.news.coding.R
import au.com.news.coding.ui.viewmodel.SourceViewModel

@Composable
fun SavedScreen(viewModel: SourceViewModel = hiltViewModel(), navController: NavHostController) {
    val saves by viewModel.saves.collectAsState(initial = emptyList())

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

        saves.apply {
            if (this.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),

                    content = {
                        items(this@apply.size) { index ->
                            NewsCard(
                                article = this@apply[index],
                                onItemClicked = { article -> viewModel.saveOrDeleteArticle(article) },
                                navController,
                                true
                            )
                        }
                    }
                )
            } else {
                EmptyScreen(
                    modifier = Modifier.fillMaxSize(),
                    stringResource(R.string.empty_saves),
                    false
                ) { /*viewModel.getHeadlines()*/ }
            }

        }

    }
}