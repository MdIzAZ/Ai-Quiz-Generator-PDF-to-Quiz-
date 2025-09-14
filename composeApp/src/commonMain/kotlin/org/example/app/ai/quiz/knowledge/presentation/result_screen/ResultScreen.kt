package org.example.app.ai.quiz.knowledge.presentation.result_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.app.ai.quiz.knowledge.domain.models.QuizResult
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.min

@Composable
fun ResultScreen(
    result: QuizResult
) {

    Scaffold(
        topBar = {

        }
    ) { ip ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            QuizDashboardChart(
                totalQs = result.total,
                correctFraction = result.correct.toFloat() / result.total,
                incorrectFraction = result.incorrect.toFloat() / result.total,
                unattemptedFraction = result.unAttempted.toFloat() / result.total,
                modifier = Modifier.padding(16.dp)
            )

        }

    }

}


@Composable
fun QuizDashboardChart(
    totalQs: Int,
    correctFraction: Float,
    incorrectFraction: Float,
    unattemptedFraction: Float,
    modifier: Modifier = Modifier
) {
    // Validate fractions (sum <= 1.0)
    val total = correctFraction + incorrectFraction + unattemptedFraction
    val normalizedCorrect = if (total > 0f) correctFraction / total else 0f
    val normalizedIncorrect = if (total > 0f) incorrectFraction / total else 0f
    val normalizedUnattempted = if (total > 0f) unattemptedFraction / total else 0f

    // Colors for each category
    val correctColor = Color(0xFF28A745) // Green
    val incorrectColor = Color(0xFFDC3545) // Red
    val unattemptedColor = Color(0xFF6C757D) // Gray

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Canvas for the doughnut chart

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {


            Box(modifier = Modifier.size(500.dp)) {
                Canvas(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(200.dp) // Adjust size as needed
                        .padding(8.dp)
                )
                {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val radius = min(canvasWidth, canvasHeight) / 2
                    val strokeWidth = radius * 0.3f
                    val center = Offset(canvasWidth / 2, canvasHeight / 2)


                    val correctSweep = normalizedCorrect * 360f
                    val incorrectSweep = normalizedIncorrect * 360f
                    val unattemptedSweep = normalizedUnattempted * 360f


                    var startAngle = -90f

                    // Correct arc
                    if (normalizedCorrect > 0f) {
                        drawArc(
                            color = correctColor,
                            startAngle = startAngle,
                            sweepAngle = correctSweep,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeWidth)
                        )
                        startAngle += correctSweep
                    }

                    // Incorrect arc
                    if (normalizedIncorrect > 0f) {
                        drawArc(
                            color = incorrectColor,
                            startAngle = startAngle,
                            sweepAngle = incorrectSweep,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeWidth)
                        )
                        startAngle += incorrectSweep
                    }

                    // Unattempted arc
                    if (normalizedUnattempted > 0f) {
                        drawArc(
                            color = unattemptedColor,
                            startAngle = startAngle,
                            sweepAngle = unattemptedSweep,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeWidth)
                        )
                    }
                }

                Text(
                    text = "Total: $totalQs",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.labelLarge,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 24.sp
                )
            }


            // Legend
            Spacer(modifier = Modifier.height(16.dp))

            Column {

                LegendItem("Correct", correctFraction * 100f, correctColor)
                LegendItem("Incorrect", incorrectFraction * 100f, incorrectColor)
                LegendItem("Unattempted", unattemptedFraction * 100f, unattemptedColor)

            }

        }
    }
}

@Composable
private fun LegendItem(label: String, percentage: Float, color: Color) {
    Row(
        modifier = Modifier
//            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label: ${percentage.toInt()}%",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Preview
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        result = QuizResult(
            correct = 7,
            incorrect = 2,
            total = 10,
            unAttempted = 1
        )
    )
}




















