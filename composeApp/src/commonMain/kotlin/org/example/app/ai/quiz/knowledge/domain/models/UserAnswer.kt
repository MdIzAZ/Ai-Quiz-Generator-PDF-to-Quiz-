package org.example.app.ai.quiz.knowledge.domain.models

data class UserAnswer(
    val questionId: Int,
    val selectedOption: String
)