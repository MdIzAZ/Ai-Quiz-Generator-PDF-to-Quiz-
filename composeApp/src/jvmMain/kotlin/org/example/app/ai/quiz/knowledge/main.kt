package org.example.app.ai.quiz.knowledge

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.app.ai.quiz.knowledge.di.initKoin
import org.example.app.ai.quiz.knowledge.presentation.App

fun main() = application {


    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "AiQuizGenerator",
    ) {
        App()
    }
}