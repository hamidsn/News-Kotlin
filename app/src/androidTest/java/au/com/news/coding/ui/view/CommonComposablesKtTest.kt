package au.com.news.coding.ui.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import au.com.news.coding.R
import org.junit.Rule
import org.junit.Test

class CommonComposablesKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingScreen() {
        composeTestRule.setContent {
            LoadingScreen()
        }
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.loading))
            .assertIsDisplayed()
    }

    @Test
    fun errorScreen() {
        composeTestRule.setContent {
            ErrorScreen(error = "error", retryAction = {})
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.retry))
            .assertIsDisplayed()
    }

    @Test
    fun emptyScreen() {
        composeTestRule.setContent {
            EmptyScreen(message = stringResource(R.string.empty_list), retryAction = {})
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.empty_list))
            .assertIsDisplayed()
    }
}