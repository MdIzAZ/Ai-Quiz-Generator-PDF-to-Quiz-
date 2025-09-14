package org.example.app.ai.quiz.knowledge.presentation.home_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun UploadBtn(
    isEnabled: Boolean,
    onPdfPicked: (ByteArray) -> Unit
) {


    val activity = LocalContext.current

    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val bytes = activity.contentResolver.openInputStream(it)?.readBytes()
            bytes?.let {
                b -> onPdfPicked(b)
            }
        }
    }


    OutlinedButton(
        enabled = isEnabled,
        content = {
            Text("Upload Document")
        },
        onClick = {
            pdfLauncher.launch("application/pdf")
        }
    )

}