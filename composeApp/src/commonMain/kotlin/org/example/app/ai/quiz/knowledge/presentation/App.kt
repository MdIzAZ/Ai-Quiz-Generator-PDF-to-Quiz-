package org.example.app.ai.quiz.knowledge.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.example.app.ai.quiz.knowledge.presentation.navigation.NavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {

        val navController = rememberNavController()

        NavGraph(
            navController = navController
        )

    }
}