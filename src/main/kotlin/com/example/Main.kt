package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(Compression) {
            gzip {
                priority = 1.0
            }
            deflate {
                priority = 10.0
                minimumSize(1024) // Only compress responses over 1KB
            }
        }
        install(FreeMarker)

        routing {
            static("/static") {
                resources("static")
            }

            get("/") {
                val model = emptyMap<String, String>()
                call.respond(FreeMarkerContent("src/main/resources/templates/index.ftl", model))
            }

            get("/generate") {
                val prompt = call.request.queryParameters["prompt"]
                val openAiResponse = OpenAiService.getAnswer(prompt!!)
                val response = OpenAiService.codeToLines(openAiResponse)
                println(response)
                val p = Parse(response)
                val parsed = p.parse()

                println(parsed.second)
                println(parsed.third)

                val model = mapOf("lines" to parsed.second, "keywordMap" to parsed.third)
                call.respond(FreeMarkerContent("src/main/resources/templates/TEST.ftl", model))
            }

            post("/generate") {
                val formParameters = call.receiveParameters()
                val prompt = formParameters["prompt"]

                call.respondRedirect("/generate?prompt=$prompt")
            }

            get("/another") {
                val data = mapOf(
                    "message" to "Hello from another route!"
                )
                call.respond(FreeMarkerContent("src/main/resources/templates/additional-page.ftl", data))
            }

        }

    }.start(wait = true)
}
