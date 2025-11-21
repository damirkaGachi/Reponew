package com.example.myapplication

import android.Manifest
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class MyTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test1(){
        composeTestRule.setContent {
            WaterTracker()
        }
        composeTestRule.onNodeWithTag("button").assertExists()
        composeTestRule.onNodeWithText("Стаканов выпито: 0").assertExists()
        composeTestRule.onNodeWithText("Объем: 0 мл / 1500 мл").assertExists()

        composeTestRule.onNodeWithTag("button").performClick()
        composeTestRule.onNodeWithText("Стаканов выпито: 1").assertExists()
        composeTestRule.onNodeWithText("Объем: 250 мл / 1500 мл").assertExists()

    }
    @Test
    fun test2(){
        composeTestRule.setContent {
            WaterTracker()
        }
        composeTestRule.onNodeWithTag("endday").assertExists()
        composeTestRule.onNodeWithText("endday").performClick()
        composeTestRule.onNodeWithText("Завершение дня").assertExists()
        composeTestRule.onNodeWithText("Вы выпили 0 мл воды сегодня.").assertExists()
        composeTestRule.onNodeWithText("Да, завершить").performClick()
        composeTestRule.onNodeWithText("Стаканов выпито: 0").assertExists()
    }
    @Test
    fun test3(){
        composeTestRule.setContent {
            WaterTracker()
        }
        composeTestRule.onNodeWithText("Дней подряд с нормой 1500+ мл").assertExists()
        composeTestRule.onNodeWithText("0").assertExists()
        repeat(6) {
            composeTestRule.onNodeWithTag("button").performClick()
        }
        composeTestRule.onNodeWithText("Завершить день").performClick()
        composeTestRule.onNodeWithText("Да, завершить").performClick()

        composeTestRule.onNodeWithText("1").assertExists()
        composeTestRule.onNodeWithText("Так держать!").assertExists()

        repeat(2) {
            composeTestRule.onNodeWithTag("button").performClick()
        }
        composeTestRule.onNodeWithText("Завершить день").performClick()
        composeTestRule.onNodeWithText("Да, завершить").performClick()
        composeTestRule.onNodeWithText("0").assertExists()

    }

}