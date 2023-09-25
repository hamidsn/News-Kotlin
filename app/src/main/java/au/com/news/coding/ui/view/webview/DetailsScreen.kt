package au.com.news.coding.ui.view.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DetailsScreen(url: String) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            var webView: WebView? = null
            var backEnabled by remember { mutableStateOf(false) }
            AndroidView(modifier = Modifier
                .testTag("newsOnWeb"),
                factory = {
                    WebView(it).apply {
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(
                                view: WebView,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                backEnabled = view.canGoBack()
                            }
                        }
                        // to play video on a web view
                        settings.javaScriptEnabled = true

                        // to verify that the client requesting your web page is actually your Android app.
                        settings.userAgentString =
                            System.getProperty("http.agent") //Dalvik/2.1.0 (Linux; U; Android 11; M2012K11I Build/RKQ1.201112.002)
                        settings.useWideViewPort = true
                        loadUrl(url)
                        webView = this
                    }
                }, update = {
                    webView = it
                })
            //this is to enable back button in web surfing
            BackHandler(enabled = backEnabled) {
                webView?.goBack()
            }
        }

    }

}


