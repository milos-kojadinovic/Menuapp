package com.example.menuapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.menuapp.test.TestTag
import org.junit.Rule
import org.junit.Test

class SuccessfulUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testOpenedNow() {
        composeTestRule.apply {

            onNodeWithTag(TestTag.EMAIL)
                .performTextInput("test@testmenu.app")

            onNodeWithTag(TestTag.PASSWORD)
                .assertIsDisplayed()
                .performTextInput("test1234")
            onNodeWithTag(TestTag.LOGIN_BUTTON).performScrollTo()
            onNodeWithTag(TestTag.LOGIN_BUTTON).assertIsDisplayed().performClick()
            onNodeWithTag(TestTag.LOADING_SPINNER).assertIsDisplayed()
            waitUntil(3000) {
                onAllNodesWithTag(TestTag.LOADING_SPINNER).fetchSemanticsNodes().isEmpty()
            }
            onNodeWithText("Holy Cow Lausanne").performClick()
            onNodeWithTag(TestTag.IMAGE).assertIsDisplayed()
            onNodeWithText("Holy Cow Lausanne").assertIsDisplayed()
            onNodeWithTag(TestTag.LOGOUT).performClick()
            onNodeWithTag(TestTag.LOGIN_BUTTON).assertIsDisplayed()
        }
    }

}