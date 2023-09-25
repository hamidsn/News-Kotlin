package au.com.news.coding.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import au.com.news.coding.ui.theme.NewsTheme
import au.com.news.coding.ui.view.TabLayout
import au.com.news.coding.ui.view.webview.DetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = Route.Home.route
                    ) {
                        composable(Route.Home.route) {
                            TabLayout(navController = navController)
                        }
                        composable(Route.Detail.route) { navBackStackEntry ->
                            val newsUrl = navBackStackEntry.arguments?.getString("newsUrl")
                            newsUrl?.let {
                                DetailsScreen(it)
                            }
                        }
                    }
                }
            }
        }

    }
}

