package org.example.app.ai.quiz.knowledge.data.quiz_generator

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.font.encoding.GlyphList
import org.apache.pdfbox.text.PDFTextStripper
import org.example.app.ai.quiz.knowledge.domain.models.QuizQuestion
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import sun.security.krb5.Confounder.bytes
import java.io.ByteArrayInputStream
import kotlin.jvm.java
import kotlin.use

actual class QuizGenerator : KoinComponent {

    private val httpClient: HttpClient by inject()

    actual suspend fun generateQuestions(pdfText: String): List<QuizQuestion> {
        val apiKey = "AIzaSyAcNxjJCgwPaFvEz2O0c-RNbVDECFcn3n8"

        val prompt = """
            Generate at most 20 multiple-choice questions based on the following text in a valid JSON array format.
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

        return try {
            // Hitting Gemini REST API
            val response: GeminiResponse = httpClient.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent") {
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

            // Parse JSON array into QuizQuestion list
            Json.decodeFromString<List<QuizQuestion>>(jsonOutput)

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    actual suspend fun extractTextFromByteArray(byteArray: ByteArray): String {
        return try {

            val resource = GlyphList::class.java.getResource("/com/tom_roush/pdfbox/resources/glyphlist/glyphlist.txt")

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

// DTOs for Gemini REST API
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
