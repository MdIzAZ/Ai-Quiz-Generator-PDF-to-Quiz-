package org.example.app.ai.quiz.knowledge.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)
