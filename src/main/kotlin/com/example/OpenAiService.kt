package com.example

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

object OpenAiService {
    // Replace with an environment variable or secure vault in production
    private const val OPENAI_API_KEY =
        "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"

    private val client = HttpClient(OkHttp)

    /**
     * Simple function to get a text completion from OpenAI.
     */
    fun getCompletion(prompt: String): String = runBlocking {
        val response: HttpResponse = client.post("https://api.openai.com/v1/completions") {
            header(HttpHeaders.Authorization, "Bearer $OPENAI_API_KEY")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(
                """{
                  "model": "text-davinci-003",
                  "prompt": "$prompt",
                  "max_tokens": 50,
                  "temperature": 0.7
                }""".trimIndent()
            )
        }

        // Parse the JSON response from OpenAI
        val jsonResponse = Json.parseToJsonElement(response.bodyAsText()) as? JsonObject
        val completion = jsonResponse
            ?.get("choices")
            ?.jsonArray
            ?.getOrNull(0)
            ?.jsonObject
            ?.get("text")
            ?.toString()
            ?: "No response"

        completion
    }
}
