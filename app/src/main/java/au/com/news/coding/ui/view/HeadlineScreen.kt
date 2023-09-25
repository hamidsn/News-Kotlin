package au.com.news.coding.ui.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.news.coding.data.remote.model.Article
import au.com.news.coding.ui.Route
import au.com.news.coding.ui.util.navigate
import au.com.news.coding.ui.viewmodel.SourceViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import au.com.news.coding.R

@Composable
fun HeadlineScreen(viewModel: SourceViewModel = hiltViewModel(), navController: NavHostController) {
    val uiViewStates by remember { mutableStateOf(viewModel.uiHeadlinesStates) }

    when (uiViewStates.value) {

        is UiHeadlineStates.HeadlineState -> {
            HeadlinesListScreen(viewModel, uiViewStates, navController)
        }

        is UiHeadlineStates.HeadlinesErrorState -> {
            ErrorScreen(
                (uiViewStates.value as? UiHeadlineStates.HeadlinesErrorState)?.message
                    ?: "Unknown Error",
                Modifier.fillMaxSize()
            ) { viewModel.getHeadlines() }
        }

        UiHeadlineStates.HeadlinesLoadingState -> LoadingScreen(modifier = Modifier.fillMaxSize())

        UiHeadlineStates.HeadlinesEmptyState -> EmptyScreen(modifier = Modifier.fillMaxSize()) { viewModel.getHeadlines() }

    }
}

@Composable
fun HeadlinesListScreen(
    viewModel: SourceViewModel,
    uiViewStates: MutableState<UiHeadlineStates>,
    navController: NavHostController
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

        (uiViewStates.value as? UiHeadlineStates.HeadlineState)?.headLineList?.apply {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),

                content = {
                    items(this@apply.size ?: 0) { index ->
                        NewsCard(
                            article = this@apply[index],
                            onItemClicked = { article -> viewModel.saveOrDeleteArticle(article) },
                            navController
                        )
                    }
                }
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCard(
    article: Article,
    onItemClicked: (Article) -> Unit,
    navController: NavHostController,
    onlyDeleteButton: Boolean = false
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        onClick = {

            article.url.apply {
                navController.navigate(Route.Detail.route, bundleOf("newsUrl" to this))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = article.title ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3

            )
            Text(
                text = article.description ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )

            Text(
                text = article.author ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Images should be loaded asynchronously and cached
                // Coil library works better than Glide with jetpack Compose
                AsyncImage(
                    error = painterResource(R.drawable.broken_svgrepo_com),
                    placeholder = painterResource(R.drawable.loading_svgrepo_com),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.article_photo),
                    alignment = Alignment.CenterStart,
                    modifier = Modifier
                        .size(80.dp, 80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                )

                IconButton(
                    onClick = {
                        onItemClicked(article)
                    }
                ) {
                    Icon(
                        painter = if (onlyDeleteButton) painterResource(R.drawable.baseline_delete_24) else painterResource(
                            R.drawable.baseline_save_24
                        ),
                        tint = if (onlyDeleteButton) {
                            Color.Gray
                        } else {
                            if (article.saved) {
                                Color.Blue
                            } else {
                                Color.Gray
                            }
                        },
                        contentDescription = "Clear"
                    )
                }
            }
        }
    }
}
