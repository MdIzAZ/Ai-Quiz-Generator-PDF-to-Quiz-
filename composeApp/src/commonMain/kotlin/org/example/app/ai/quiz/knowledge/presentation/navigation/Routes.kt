package org.example.app.ai.quiz.knowledge.presentation.navigation

import kotlinx.serialization.Serializable
import org.example.app.ai.quiz.knowledge.domain.models.QuizResult

@Serializable
sealed class Routes {

    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object QuizScreen : Routes()

    @Serializable
    data class ResultScreen(
        val total: Int,
        val correct: Int,
        val incorrect: Int,
        val unAttempted: Int
    ) : Routes()

}