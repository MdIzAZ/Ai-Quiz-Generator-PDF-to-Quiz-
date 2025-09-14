package org.example.app.ai.quiz.knowledge.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class QuizResult(
    val total: Int,
    val correct: Int,
    val incorrect: Int,
    val unAttempted: Int
)
