package com.example.weather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class SearchNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun searchCityAddToFavouritesAndReturnToFavouritesScreen() {
        composeTestRule.onNodeWithTag(FAVOURITE_SEARCH_CARD).performClick()
        waitForTag(SEARCH_TEXT_FIELD)

        composeTestRule.onNodeWithTag(SEARCH_TEXT_FIELD).performTextInput("Moscow")
        waitForTag(SEARCH_CITY)

        composeTestRule.onNodeWithTag(SEARCH_CITY).performClick()
        waitForTag(DETAILS_FAVOURITE_BUTTON)

        composeTestRule.onNodeWithTag(DETAILS_FAVOURITE_BUTTON).performClick()

        composeTestRule.onNodeWithTag(DETAILS_BACK_BUTTON).performClick()
        waitForTag(SEARCH_BACK_BUTTON)

        composeTestRule.onNodeWithTag(SEARCH_BACK_BUTTON).performClick()
        waitForTag(FAVOURITE_CITY)

        composeTestRule.onNodeWithTag(FAVOURITE_CITY).assertIsDisplayed()
    }

    private fun waitForTag(tag: String) {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private companion object {
        const val FAVOURITE_SEARCH_CARD = "favourite_search_card"
        const val SEARCH_TEXT_FIELD = "search_text_field"
        const val SEARCH_BACK_BUTTON = "search_back_button"
        const val SEARCH_CITY = "search_city_1"
        const val DETAILS_BACK_BUTTON = "details_back_button"
        const val DETAILS_FAVOURITE_BUTTON = "details_favourite_button"
        const val FAVOURITE_CITY = "favourite_city_1"
    }
}
