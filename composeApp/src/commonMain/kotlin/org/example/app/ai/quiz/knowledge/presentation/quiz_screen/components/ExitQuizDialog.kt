package org.example.app.ai.quiz.knowledge.presentation.quiz_screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExitQuizDialog(
    modifier: Modifier = Modifier,
    isOpened: Boolean,
    title: String = "Exit Quiz",
    confirmButtonText: String = "Exit",
    dismissButtonText: String = "No",
    onConfirmButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    if (isOpened) {
        AlertDialog(
            title = { Text(text = title) },
            text = {
                Text(
                    text = "Are you sure, you want to exit from quiz ? " +
                            "Your progress will be lost ."
                )
            },
            confirmButton = {
                TextButton(
                    content = { Text(text = confirmButtonText) },
                    onClick = {
                        onConfirmButtonClick()
                    }
                )
            },
            dismissButton = {
                TextButton(
                    content = { Text(text = dismissButtonText) },
                    onClick = onDismissRequest
                )
            },
            onDismissRequest = onDismissRequest
        )
    }


}