package org.example.app.ai.quiz.knowledge.data.repo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.app.ai.quiz.knowledge.data.quiz_generator.QuizGenerator
import org.example.app.ai.quiz.knowledge.domain.models.QuizQuestion
import org.example.app.ai.quiz.knowledge.domain.repo.QuizRepo

class QuizRepoImp(
    private val quizGenerator: QuizGenerator
) : QuizRepo {

    private val _questions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    override val questions = _questions.asStateFlow()


    private val _pdfText = MutableStateFlow("")
    override val pdfText = _pdfText.asStateFlow()

    val dummyText = """
            The Internet Protocol (IP) is the fundamental communication protocol that allows devices to connect 
            and exchange data across networks. It works by assigning a unique numerical address, known as an 
            IP address, to each device, ensuring that data packets are sent to the correct destination. 
            There are two main versions of IP in use today: IPv4 and IPv6. IPv4 uses a 32-bit addressing scheme, 
            providing approximately 4.3 billion unique addresses, but with the rapid growth of the internet, 
            this pool has become nearly exhausted. To overcome this limitation, IPv6 was introduced, 
            which uses a 128-bit addressing scheme and can support an almost unlimited number of devices. 
            IP works closely with other protocols, such as the Transmission Control Protocol (TCP), to ensure 
            reliable communication by handling packet sequencing, error detection, and retransmission. Together, 
            TCP/IP forms the backbone of the internet. Understanding how IP works is crucial in networking, 
            as it enables technologies like routing, subnetting, and the functioning of services such as email,
             web browsing, and video streaming.
        """.trimIndent()


    override suspend fun fetchAndCacheQuestions(
        onSuccess: () -> Unit
    ) {
        val quizQuestions = quizGenerator.generateQuestions(pdfText.value)
        _questions.value = quizQuestions
        println(quizQuestions)
        if (quizQuestions.isNotEmpty()) {
            onSuccess()
        }
    }

    override suspend fun extractTextFromByteArray(
        byteArray: ByteArray,
        onSuccess: (msg: String) -> Unit
    ) {

        val text = quizGenerator.extractTextFromByteArray(byteArray)
        _pdfText.value = text
        if (text.isBlank()) {
            onSuccess("No Text Fetched")
        } else {
            onSuccess("Text fetched successfully")
        }

    }


}