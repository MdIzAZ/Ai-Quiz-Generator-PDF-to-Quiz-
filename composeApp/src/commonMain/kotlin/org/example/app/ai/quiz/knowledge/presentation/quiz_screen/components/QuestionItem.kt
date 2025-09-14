package org.example.app.ai.quiz.knowledge.presentation.quiz_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.app.ai.quiz.knowledge.domain.models.QuizQuestion
import org.example.app.ai.quiz.knowledge.domain.models.UserAnswer

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuestionItem(
    modifier: Modifier = Modifier,
    currentQuestionIdx: Int,
    questions: List<QuizQuestion>,
    answers: List<UserAnswer>,
    onOptionSelect: (id: Int, option: String) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        val currentQuestion = questions[currentQuestionIdx]
        val selectedAnswer = answers.find {
            it.questionId == currentQuestion.id
        }?.selectedOption

        Text(
            text = currentQuestion.question,
            style = MaterialTheme.typography.headlineSmall
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            currentQuestion.options.forEach {
                OptionItem(
                    modifier = Modifier
                        .widthIn(max = 400.dp)
                        .padding(vertical = 10.dp),
                    option = it,
                    isSelected = selectedAnswer == it,
                    onClick = {
                        onOptionSelect(currentQuestion.id, it)
                    }
                )
            }

        }

    }

}