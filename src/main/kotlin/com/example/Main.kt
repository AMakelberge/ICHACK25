package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

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
            static("/") {
                files("src/main/resources/templates")
            }

            get("/") {
                val file = File("src/main/resources/templates/index.ftl")
                call.respondFile(file)

                val model = mapOf("hello" to "world")
                call.respond(FreeMarkerContent("index.ftl", model))
            }

            get("/generate") {
                val prompt = "Bubble Sort Algorithm"
                val openAiResponse = OpenAiService.getCode(prompt)
                val response = OpenAiService.codeToLines(openAiResponse)
                println(response)

                val model = mapOf("response" to response)
                call.respond(FreeMarkerContent("generate.ftl", model))
            }
        }

    }.start(wait = true)
}
