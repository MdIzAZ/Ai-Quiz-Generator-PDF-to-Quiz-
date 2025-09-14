package org.example.app.ai.quiz.knowledge.presentation.quiz_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.app.ai.quiz.knowledge.domain.models.QuizQuestion
import org.example.app.ai.quiz.knowledge.domain.models.UserAnswer
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.dummyQuizQuestion
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.dummyUserAnswers
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun QuestionNavigationRow(
    modifier: Modifier,
    selectedTabNumber: Int,
    answers: List<UserAnswer>,
    questions: List<QuizQuestion>,
    onTabSelect: (Int) -> Unit
) {

    if (questions.isEmpty()) return

    ScrollableTabRow(
        modifier = modifier,
        edgePadding = 0.dp,
        selectedTabIndex = selectedTabNumber,
        tabs = {
            questions.forEachIndexed { idx, ele ->

                val containerColor = when {
                    answers.any { it.questionId == ele.id } -> {
                        MaterialTheme.colorScheme.secondaryContainer
                    }

                    else -> {
                        MaterialTheme.colorScheme.surface
                    }
                }

                Tab(
                    modifier = Modifier.background(containerColor),
                    selected = idx == selectedTabNumber,
                    onClick = {
                        onTabSelect(idx)
                    },
                    text = {
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp),
                            text = "${idx + 1}"
                        )
                    }
                )
            }
        }
    )
}


@Preview
@Composable
private fun QuestionNavigationRowPreview() {
    QuestionNavigationRow(
        modifier = Modifier,
        selectedTabNumber = 3,
        answers = dummyUserAnswers,
        questions = dummyQuizQuestion,
        onTabSelect = {}
    )
}