package org.example.app.ai.quiz.knowledge.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.app.ai.quiz.knowledge.domain.repo.QuizRepo
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.QuizScreenState

class HomeViewModel(
    private val quizRepo: QuizRepo
): ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = combine(
        _state,
        quizRepo.pdfText
    ) { state, pdfText ->

        state.copy(
            extractedText = pdfText
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HomeScreenState()
    )


    fun generateQuiz(onSuccess: () -> Unit) {

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

        _state.update { it.copy(isLoading = true) }


        viewModelScope.launch {
            try {
                quizRepo.fetchAndCacheQuestions {
                    onSuccess()
                }
                _state.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                e.printStackTrace()
                _state.update { it.copy(isLoading = false) }
                print(e.message)
            }
        }

    }


    fun extractTextFromByteArray(byteArray: ByteArray, onSuccess: (msg: String) -> Unit) {

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                quizRepo.extractTextFromByteArray(
                    byteArray = byteArray,
                    onSuccess = onSuccess
                )

                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.update { it.copy(isLoading = false) }
                print(e.message)
            }
        }

    }


}