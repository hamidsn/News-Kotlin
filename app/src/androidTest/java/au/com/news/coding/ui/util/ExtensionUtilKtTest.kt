package au.com.news.coding.ui.util

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionUtilKtTest {
    @Test
    fun test_is_english() {
        val en = "en"
        val fr = "fr"
        assertEquals(true, en.isEnglish())
        assertEquals(false, fr.isEnglish())
    }
}