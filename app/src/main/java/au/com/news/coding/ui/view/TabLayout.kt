package au.com.news.coding.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.news.coding.ui.viewmodel.SourceViewModel

/**
 * https://m3.material.io/components/tabs/overview
 * https://developer.android.com/jetpack/compose/touch-input/pointer-input
 * https://github.com/TomerPacific/MediumArticles/tree/master/JetpackComposeTabs
 */
@Composable
fun TabLayout(viewModel: SourceViewModel = hiltViewModel(), navController: NavHostController) {

    val tabIndex = viewModel.tabIndex.observeAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex.value ?: 0) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex.value == index,
                    onClick = { viewModel.updateTabIndex(index) },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.List, contentDescription = null)
                            1 -> Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null
                            )

                            2 -> Icon(imageVector = Icons.Default.Star, contentDescription = null)
                        }
                    }
                )
            }
        }

        when (tabIndex.value) {
            0 -> HeadlineScreen(viewModel = viewModel, navController)
            1 -> SourceScreen(viewModel = viewModel)
            2 -> SavedScreen(viewModel = viewModel, navController)
        }
    }
}