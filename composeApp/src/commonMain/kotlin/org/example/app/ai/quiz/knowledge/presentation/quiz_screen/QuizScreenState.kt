package org.example.app.ai.quiz.knowledge.presentation.quiz_screen

import org.example.app.ai.quiz.knowledge.domain.models.QuizQuestion
import org.example.app.ai.quiz.knowledge.domain.models.UserAnswer
import org.koin.viewmodel.emptyState

data class QuizScreenState(
    val isLoading: Boolean = false,
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val userAnswers: List<UserAnswer> = emptyList(),
    val currentQuestionIndex: Int = 0,
)


val dummyQuizQuestion = listOf(
    QuizQuestion(
        id = 0,
        question = "What is the capital of France?",
        options = listOf("Paris", "London", "Berlin", "Rome"),
        correctAnswer = "Paris"
    ),
    QuizQuestion(
        id = 1,
        question = "Which planet is known as the Red Planet?",
        options = listOf("Earth", "Mars", "Jupiter", "Venus"),
        correctAnswer = "Mars"
    ),
    QuizQuestion(
        id = 2,
        question = "Who wrote 'Hamlet'?",
        options = listOf("William Shakespeare", "Charles Dickens", "Mark Twain", "Leo Tolstoy"),
        correctAnswer = "William Shakespeare"
    ),
    QuizQuestion(
        id = 3,
        question = "What is the boiling point of water?",
        options = listOf("90°C", "100°C", "110°C", "120°C"),
        correctAnswer = "100°C"
    ),
    QuizQuestion(
        id = 4,
        question = "Which element has the chemical symbol 'O'?",
        options = listOf("Gold", "Oxygen", "Osmium", "Oxide"),
        correctAnswer = "Oxygen"
    ),
    QuizQuestion(
        id = 5,
        question = "What is the largest mammal on Earth?",
        options = listOf("Elephant", "Blue Whale", "Giraffe", "Hippopotamus"),
        correctAnswer = "Blue Whale"
    )
)

val dummyUserAnswers = listOf(
    UserAnswer(
        questionId = 0,
        selectedOption = "Paris"
    ),
    UserAnswer(
        questionId = 1,
        selectedOption = "Mars"
    ),
    UserAnswer(
        questionId = 2,
        selectedOption = "Mark Twain"
    ),
    UserAnswer(
        questionId = 3,
        selectedOption = "100°C"
    ),
    UserAnswer(
        questionId = 4,
        selectedOption = "Osmium"
    ),
    UserAnswer(
        questionId = 5,
        selectedOption = "Blue Whale"
    )
)
