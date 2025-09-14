package org.example.app.ai.quiz.knowledge.domain.repo

import kotlinx.coroutines.flow.StateFlow
import org.example.app.ai.quiz.knowledge.domain.models.QuizQuestion

interface QuizRepo {

    val questions: StateFlow<List<QuizQuestion>>
    val pdfText: StateFlow<String>
    suspend fun fetchAndCacheQuestions(onSuccess:()-> Unit)

    suspend fun extractTextFromByteArray(byteArray: ByteArray, onSuccess:(String)-> Unit)

}