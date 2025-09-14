package org.example.app.ai.quiz.knowledge.presentation.quiz_screen.components

import aiquizgenerator.composeapp.generated.resources.Res
import aiquizgenerator.composeapp.generated.resources.ic_next
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

@Composable
fun QuizSubmitButton(
    modifier: Modifier = Modifier,
    isPrevBtnEnabled: Boolean,
    isNextBtnEnabled: Boolean,
    onPrevBtnClick:()->Unit,
    onNextBtnClick:()->Unit,
    onSubmitBtnClick:()->Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedIconButton(
            onClick = onPrevBtnClick,
            enabled = isPrevBtnEnabled,
            content = {
                Icon(
                    modifier = Modifier
                        .graphicsLayer { scaleX = -1f },
                    painter = painterResource(Res.drawable.ic_next),
                    contentDescription = "Previous"
                )
            }
        )

        Button(
            modifier = Modifier.padding(horizontal = 30.dp),
            onClick = onSubmitBtnClick,
            content = {
                Text(modifier = Modifier.padding(10.dp), text = "Submit")
            }
        )

        OutlinedIconButton(
            onClick = onNextBtnClick,
            enabled = isNextBtnEnabled,
            content = {
                Icon(
                    painter = painterResource(Res.drawable.ic_next),
                    contentDescription = "Next"
                )
            }
        )

    }

}