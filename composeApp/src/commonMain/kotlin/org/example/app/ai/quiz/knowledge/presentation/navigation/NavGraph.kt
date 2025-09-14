package org.example.app.ai.quiz.knowledge.presentation.navigation


import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.example.app.ai.quiz.knowledge.domain.models.QuizResult
import org.example.app.ai.quiz.knowledge.presentation.home_screen.HomeScreen
import org.example.app.ai.quiz.knowledge.presentation.home_screen.HomeViewModel
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.QuizScreen
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.QuizViewModel
import org.example.app.ai.quiz.knowledge.presentation.result_screen.ResultScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ) {

        composable<Routes.HomeScreen> {

            val viewmodel = koinViewModel<HomeViewModel>()
            val state by viewmodel.state.collectAsStateWithLifecycle()

            HomeScreen(
                state = state,
                onUploadClick = { b ->
                    viewmodel.extractTextFromByteArray(b) {
                        println(it)
                    }
                },
                onQuizGenerateClick = {
                    viewmodel.generateQuiz(
                        onSuccess = {
                            navController.navigate(Routes.QuizScreen)
                        }
                    )
                }
            )
        }

        composable<Routes.QuizScreen> {

            val viewmodel = koinViewModel<QuizViewModel>()
            val state by viewmodel.state.collectAsStateWithLifecycle()

            QuizScreen(
                state = state,
                onTabSelected = viewmodel::onTabSelected,
                onOptionSelect = viewmodel::onOptionSelect,
                onSubmit = {
                    val result = viewmodel.onSubmit()
                    navController.navigate(
                        Routes.ResultScreen(
                            total = result.total,
                            correct = result.correct,
                            incorrect = result.incorrect,
                            unAttempted = result.unAttempted
                        )
                    ) {
                        popUpTo<Routes.QuizScreen>{inclusive = true}
                    }
                }
            )
        }

        composable<Routes.ResultScreen>(
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 1500, delayMillis = 300)
                ) + fadeIn()
            },
        ) {

            val args = it.toRoute<Routes.ResultScreen>()

            val result = QuizResult(
                total = args.total,
                correct = args.correct,
                incorrect = args.incorrect,
                unAttempted = args.unAttempted
            )

            ResultScreen(result = result)
        }


    }

}