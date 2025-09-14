package org.example.app.ai.quiz.knowledge

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.example.app.ai.quiz.knowledge.di.initKoin
import org.example.app.ai.quiz.knowledge.presentation.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    initKoin()

    ComposeViewport(document.body!!) {
//        App()
        Text("Working on it!")


    }
}