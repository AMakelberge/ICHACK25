package com.example

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object OpenAiService {
    // Replace with an environment variable or secure vault in production
    private const val OPENAI_API_KEY =
        "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"

    private val client = HttpClient(OkHttp)

    /**
     * Simple function to get a text completion from OpenAI.
     */
    fun getCode(prompt: String): String = runBlocking {

        val requestBody = """
            {
              "model": "gpt-4",
              "messages": [
                {
                  "role": "system",
                  "content": "You are a helpful assistant."
                },
                {
                  "role": "user",
                  "content": "Comment every single line of code. Return the algorithm in Kotlin. Only return the algorithm, do not give any other response. $prompt"
                }
              ],
              "max_tokens": 50,
              "temperature": 0.7
            }
        """.trimIndent()


        val response: HttpResponse = client.post("https://api.openai.com/v1/chat/completions") {
            header(HttpHeaders.Authorization, "Bearer $OPENAI_API_KEY")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(requestBody)
        }


        val status = response.status
        val body = response.bodyAsText()
        println("OpenAI HTTP status: ${response.status}")
        println("OpenAI raw body: ${response.bodyAsText()}")

        if (status != HttpStatusCode.OK) {
            error("OpenAI GPT-4 error: $status\n$body")
        }

        // Parse the JSON response.
        // GPT-4's structure is the same as GPT-3.5-turbo (Chat Completions).
        val jsonResponse = Json.parseToJsonElement(body).jsonObject
        val choicesArray = jsonResponse["choices"]?.jsonArray
        if (choicesArray.isNullOrEmpty()) {
            return@runBlocking "No choices returned"
        }

        // The model's text is found in choices[0].message.content
        val firstChoice = choicesArray[0].jsonObject
        val messageObject = firstChoice["message"]?.jsonObject
        val contentText = messageObject?.get("content")?.jsonPrimitive?.content

        return@runBlocking contentText ?: throw Exception("No response from ChatGPT")
    }

    fun codeToLines(code: String): List<String> {
        var result = code.trim()

        result = result.removePrefix("```kotlin")
        result = result.removePrefix("```")
        result = result.removeSuffix("```")

        return result.lineSequence()
            .map { it.trimEnd() }
            .toList()

    }

    fun getAnswer(prompt: String): String = runBlocking {

        val requestBody = """
            {
              "model": "gpt-4",
              "messages": [
                {
                  "role": "system",
                  "content": "You only output code, nothing else. You take everything said completely literally. You program in Kotlin."
                },
                {
                  "role": "user",
                  "content": "$prompt"
                }
              ],
              "max_tokens": 500,
              "temperature": 0.2
            }
        """.trimIndent()


        val response: HttpResponse = client.post("https://api.openai.com/v1/chat/completions") {
            header(HttpHeaders.Authorization, "Bearer $OPENAI_API_KEY")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(requestBody)
        }


        val status = response.status
        val body = response.bodyAsText()

        if (status != HttpStatusCode.OK) {
            error("OpenAI GPT-4 error: $status\n$body")
        }

        // Parse the JSON response.
        // GPT-4's structure is the same as GPT-3.5-turbo (Chat Completions).
        val jsonResponse = Json.parseToJsonElement(body).jsonObject
        val choicesArray = jsonResponse["choices"]?.jsonArray
        if (choicesArray.isNullOrEmpty()) {
            return@runBlocking "No choices returned"
        }

        // The model's text is found in choices[0].message.content
        val firstChoice = choicesArray[0].jsonObject
        val messageObject = firstChoice["message"]?.jsonObject
        val contentText = messageObject?.get("content")?.jsonPrimitive?.content

        return@runBlocking contentText ?: throw Exception("No response from ChatGPT")
    }

}
