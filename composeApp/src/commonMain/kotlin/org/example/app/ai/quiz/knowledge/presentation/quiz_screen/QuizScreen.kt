package org.example.app.ai.quiz.knowledge.presentation.quiz_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.components.QuizSubmitButton
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.components.QuestionItem
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.components.QuestionNavigationRow
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.components.SubmitQuizDialog
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun QuizScreen(
    state: QuizScreenState,
    onTabSelected: (Int) -> Unit,
    onOptionSelect: (id: Int, option: String) -> Unit,
    onSubmit: () -> Unit,
) {

    val pagerState = rememberPagerState { state.quizQuestions.size }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect{
            onTabSelected(it)
        }
    }
    LaunchedEffect(state.currentQuestionIndex) {
        pagerState.animateScrollToPage(state.currentQuestionIndex)
    }

    var isSubmitDialogOpened by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {


        }
    ) { ip ->


        SubmitQuizDialog(
            isOpened = isSubmitDialogOpened,
            onDismissRequest = {isSubmitDialogOpened = false},
            onConfirmButtonClick =  {
                isSubmitDialogOpened = false
                onSubmit()
            }
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(ip)
        ) {

            QuestionNavigationRow(
                modifier = Modifier,
                selectedTabNumber = state.currentQuestionIndex,
                questions = state.quizQuestions,
                answers = state.userAnswers,
                onTabSelect = {
                    onTabSelected(it)
                }
            )

            HorizontalPager(
                modifier = Modifier.weight(1f),
                state = pagerState
            ) {

                QuestionItem(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(ip)
                        .padding(12.dp)
                        .verticalScroll(rememberScrollState()),
                    currentQuestionIdx = state.currentQuestionIndex,
                    questions = state.quizQuestions,
                    answers = state.userAnswers,
                    onOptionSelect = onOptionSelect
                )
            }

            QuizSubmitButton(
                modifier = Modifier
                    .fillMaxWidth(),
                isPrevBtnEnabled = state.currentQuestionIndex != 0,
                isNextBtnEnabled = state.currentQuestionIndex != state.quizQuestions.lastIndex,
                onPrevBtnClick = { onTabSelected((state.currentQuestionIndex - 1).coerceAtLeast(0)) },
                onNextBtnClick = {
                    onTabSelected(
                        (state.currentQuestionIndex + 1).coerceAtMost(state.quizQuestions.lastIndex)
                    )
                },
                onSubmitBtnClick = {isSubmitDialogOpened = true}
            )

        }


    }

}


@Preview
@Composable
private fun QuizScreenPrev() {

    QuizScreen(
        state = QuizScreenState(),
        onTabSelected = {},
        onOptionSelect = {_,_->},
        onSubmit = {}
    )

}



