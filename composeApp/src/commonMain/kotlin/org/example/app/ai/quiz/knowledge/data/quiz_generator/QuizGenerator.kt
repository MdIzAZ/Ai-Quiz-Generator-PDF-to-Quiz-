package org.example.app.ai.quiz.knowledge.data.quiz_generator

import org.example.app.ai.quiz.knowledge.domain.models.QuizQuestion


expect class QuizGenerator() {
    suspend fun generateQuestions(pdfText: String): List<QuizQuestion>

    suspend fun extractTextFromByteArray(byteArray: ByteArray): String

}