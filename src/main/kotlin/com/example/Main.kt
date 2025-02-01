package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.compression.*
import io.ktor.http.*
import io.ktor.server.html.*
import kotlinx.html.*
import java.io.File

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureHTTP()
        configureRouting()
    }.start(wait = true)
}

fun Application.configureHTTP() {
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // Only compress responses over 1KB
        }
    }
}

fun Application.configureRouting() {
    routing {
        // Serve static files (index.html, CSS, etc.) from resources/static
        static("/") {
            files("src/main/resources/static")
        }

        get("/") {
            val file = File("src/main/resources/static/index.html")
            call.respondFile(file)
        }

        // Example dynamic route that generates HTML on the server.
        // Demonstrates how you can integrate with the OpenAiService.
        get("/generate") {
            val prompt = "Hello from Kotlin on the server side!"
            val openAiResponse = OpenAiService.getCompletion(prompt)

            // Respond with server-side rendered HTML using Ktor’s HTML DSL
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title("Generated Content")
                    link(rel = "stylesheet", href = "css/style.css", type = "text/css")
                }
                body {
                    nav {
                        classes = setOf("navbar")
                        div("navbar-brand") {
                            +"My Education Tool"
                        }
                        ul("navbar-links") {
                            li {
                                a("/") { +"Home" }
                            }
                            li {
                                a("/pages/additional-page.html") { +"Another Page" }
                            }
                            li {
                                // current route
                                a("/generate") { +"Generate" }
                            }
                        }
                    }
                    main {
                        classes = setOf("container")
                        h1 { +"Dynamic Content from OpenAI" }
                        p {
                            +"Prompt: $prompt"
                        }
                        p {
                            // This is the text returned from OpenAI
                            +"Response: $openAiResponse"
                        }
                    }
                }
            }
        }
    }
}
