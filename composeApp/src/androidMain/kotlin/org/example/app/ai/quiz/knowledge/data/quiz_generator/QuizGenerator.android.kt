package org.example.app.ai.quiz.knowledge.data.quiz_generator

import android.util.Log
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.font.encoding.GlyphList
import com.tom_roush.pdfbox.text.PDFTextStripper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.example.app.ai.quiz.knowledge.domain.models.QuizQuestion
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.ByteArrayInputStream
import kotlin.use

actual class QuizGenerator : KoinComponent {

    private val httpClient: HttpClient by inject()

    actual suspend fun generateQuestions(pdfText: String): List<QuizQuestion> {

        println("PDF TEXT: $pdfText")
        val apiKey = "AIzaSyAcNxjJCgwPaFvEz2O0c-RNbVDECFcn3n8"

        val prompt = """
            Generate exactly 20 (if not possible then some less, for any problem atleast dont return an empty array, 
            atleast include 1 question), multiple-choice questions based on the following text in a valid JSON array format.
            Each JSON object must have exactly these fields:
            {
                "id": 0 to n  ( first must be zero and then serially others)
                "question": "question text",
                "options": ["option1", "option2", "option3", "option4"],
                "correctAnswer": "exact correct option text"
            }
            Return ONLY the JSON array (e.g., [{"question":"...","options":["...","...","...","..."],"correctAnswer":"..."},...]).
            Do not include any additional text, explanations, markdown, or code fences (e.g., ```json
            Text:
            $pdfText
        """.trimIndent()


        println("Hello")

        return try {
            // Hitting Gemini REST API
            val response: GeminiResponse =
                httpClient.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent") {
                    contentType(ContentType.Application.Json)
                    url {
                        parameters.append("key", apiKey)
                    }
                    setBody(
                        mapOf(
                            "contents" to listOf(
                                mapOf(
                                    "parts" to listOf(
                                        mapOf("text" to prompt)
                                    )
                                )
                            )
                        )
                    )
                }.body()

            println("Response: $response")

            val rawOutput = response.candidates
                ?.firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text ?: "[]"

            // Clean up unwanted markdown/code fences
            val jsonOutput = rawOutput
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()

            println("Json Output: $jsonOutput")

            // Parse JSON array into QuizQuestion list
            Json.decodeFromString<List<QuizQuestion>>(jsonOutput)

        } catch (e: Exception) {
            println("Json Output: Error occured")
            e.printStackTrace()
            emptyList()
        }
    }

    actual suspend fun extractTextFromByteArray(byteArray: ByteArray): String {

        return try {

            val resource = GlyphList::class.java.getResource("/com/tom_roush/pdfbox/resources/glyphlist/glyphlist.txt")
            Log.d("PDFBox", "GlyphList resource: $resource")
            if (resource == null) {
                Log.e("PDFBox", "GlyphList resource not found")
            }

            ByteArrayInputStream(byteArray).use { inputStream ->
                val document = PDDocument.load(inputStream)
                val stripper = PDFTextStripper()
                val text = stripper.getText(document)
                document.close()
                text
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            ""
        }

    }
}

@kotlinx.serialization.Serializable
data class GeminiResponse(
    val candidates: List<Candidate>? = null
)

@kotlinx.serialization.Serializable
data class Candidate(
    val content: ContentPart? = null
)

@kotlinx.serialization.Serializable
data class ContentPart(
    val parts: List<Part>? = null
)

@kotlinx.serialization.Serializable
data class Part(
    val text: String? = null
)