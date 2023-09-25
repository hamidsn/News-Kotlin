package au.com.news.coding.ui.view.webview

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            DetailsScreen(url = "https://www.google.com")
        }
    }

    @Test
    fun testWebView() {
        composeTestRule.onNodeWithTag("newsOnWeb")
            .assertIsDisplayed()
    }
}