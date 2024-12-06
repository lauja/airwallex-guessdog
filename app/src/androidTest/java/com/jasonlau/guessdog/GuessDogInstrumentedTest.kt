package com.jasonlau.guessdog

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class GuessDogInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<GuessDogActivity>()

    @Test
    fun guessDogEndToEndTest() {
        val nextLabel = "NEXT >"
        for (i in 1..10) {
            composeTestRule.waitUntilExactlyOneExists(matcher = hasText(nextLabel), 10000L)

            // not really required, just a pause so that we can see the screen briefly
            Thread.sleep(1000)

            composeTestRule.onNodeWithTag("Button0").performClick()

            composeTestRule.onNodeWithText(nextLabel).performClick()
        }
    }
}