package org.example.app.ai.quiz.knowledge.presentation.quiz_screen

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.app.ai.quiz.knowledge.data.quiz_generator.QuizGenerator
import org.example.app.ai.quiz.knowledge.domain.models.QuizResult
import org.example.app.ai.quiz.knowledge.domain.models.UserAnswer
import org.example.app.ai.quiz.knowledge.domain.repo.QuizRepo
import org.example.app.ai.quiz.knowledge.presentation.home_screen.HomeScreenState

class QuizViewModel(
    quizRepo: QuizRepo
) : ViewModel() {


    private val _state = MutableStateFlow(QuizScreenState())
    private val quizQuestions = quizRepo.questions

    val state = combine(
        _state,
        quizQuestions
    ) { state, questions ->

        state.copy(
            quizQuestions = questions
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        QuizScreenState()
    )


    fun onTabSelected(tabIdx: Int) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(currentQuestionIndex = tabIdx) }
            } catch (e: Exception) {
                e.printStackTrace()
                println(e)
            }
        }

    }

    fun onOptionSelect(id: Int, option: String) {
        viewModelScope.launch {
            try {
                val isAlreadyPresent = state.value.userAnswers.find { it.questionId == id }

                if (isAlreadyPresent != null) {
                    val updatedList = if (isAlreadyPresent.selectedOption == option) {
                        // Deselect → remove the answer
                        state.value.userAnswers.filterNot { it.questionId == id }
                    } else {
                        // Update selected option
                        state.value.userAnswers.map {
                            if (it.questionId == id) it.copy(selectedOption = option) else it
                        }
                    }

                    _state.update { it.copy(userAnswers = updatedList) }
                } else {
                    // Add new answer
                    val updatedList = state.value.userAnswers + UserAnswer(id, option)
                    _state.update { it.copy(userAnswers = updatedList) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun onSubmit(): QuizResult {
        val correctAnswers = state.value.quizQuestions.filter { question ->
            state.value.userAnswers.any { it.questionId == question.id && it.selectedOption == question.correctAnswer }
        }.size

        val totalQuestions = state.value.quizQuestions.size

        val inCorrectAnswer = state.value.quizQuestions.filter { question ->
            state.value.userAnswers.any { it.questionId == question.id && it.selectedOption != question.correctAnswer }
        }.size

        val unAttempted = totalQuestions - correctAnswers - inCorrectAnswer

        return QuizResult(
            total = totalQuestions,
            correct = correctAnswers,
            incorrect = inCorrectAnswer,
            unAttempted = unAttempted
        )
    }


}