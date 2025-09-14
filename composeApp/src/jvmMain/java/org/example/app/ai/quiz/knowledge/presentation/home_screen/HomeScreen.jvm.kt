package org.example.app.ai.quiz.knowledge.presentation.home_screen

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

@Composable
actual fun UploadBtn(
    isEnabled: Boolean,
    onPdfPicked: (ByteArray) -> Unit
) {


    val scope = rememberCoroutineScope()
    val context = LocalPlatformContext.current

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Pdf,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let { file ->
                    val pdfBytes = file.readByteArray(context)
                    onPdfPicked(pdfBytes)
                }
            }
        }
    )


    OutlinedButton(
        enabled = isEnabled,
        content = {
            Text("Upload Document")
        },
        onClick = {
            pickerLauncher.launch()
        }
    )

}