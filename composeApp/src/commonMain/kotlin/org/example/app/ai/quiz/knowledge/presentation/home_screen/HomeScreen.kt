package org.example.app.ai.quiz.knowledge.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun HomeScreen(
    state: HomeScreenState,
    onUploadClick: (pdfBytes: ByteArray) -> Unit,
    onQuizGenerateClick: () -> Unit,
) {
    Scaffold { ip ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
        ) {

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).size(50.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                UploadBtn(
                    isEnabled = !state.isLoading,
                    onPdfPicked = onUploadClick
                )

                OutlinedButton(
                    enabled = !state.isLoading && state.extractedText.isNotEmpty(),
                    content = {
                        Text("Generate Quiz")
                    },
                    onClick = {
                        onQuizGenerateClick()
                    }
                )

            }


        }

    }

}


@Composable
expect fun UploadBtn(isEnabled: Boolean, onPdfPicked: (ByteArray) -> Unit)

