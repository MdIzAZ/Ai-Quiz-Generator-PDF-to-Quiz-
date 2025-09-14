package org.example.app.ai.quiz.knowledge.presentation.home_screen

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
actual fun UploadBtn(
    isEnabled: Boolean,
    onPdfPicked: (ByteArray) -> Unit
) {

    OutlinedButton(
        enabled = isEnabled,
        content = {
            Text("Upload Document")
        },
        onClick = {
//            onClick()
        }
    )
}