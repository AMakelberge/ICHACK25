//package com.example
//
//import freemarker.cache.ClassTemplateLoader
//import io.ktor.server.application.*
//import io.ktor.server.engine.*
//import io.ktor.server.freemarker.*
//import io.ktor.server.netty.*
//import io.ktor.server.response.*
//import io.ktor.server.routing.*
//
//fun main() {
//    embeddedServer(Netty, port = 8080) {
//        install(FreeMarker) {
//            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
//        }
//
//        routing {
//            get("/") {
//                val lines = listOf(
//                    "fun bubbleSort(arr: IntArray): IntArray {",
//                    "    val n = arr.size",
//                    "// The program should loop through every item in the array",
//                    "        for (j in 0 until n - i - 1) {",
//                    "            // Check if the item to the left is bigger than the one to the right",
//                    "                val temp = arr[j]",
//                    "                arr[j] = arr[j + 1]",
//                    "                arr[j + 1] = temp",
//                    "            }",
//                    "        }",
//                    "    }",
//                    "    return arr",
//                    "}"
//                )
//
//                // Replacing the options list with a map
//                val keywordMap = mapOf(
//                    3 to "for",
//                    5 to "if"
//                )
//
//                // Pass the map to FreeMarker
//                call.respond(FreeMarkerContent("TEST.ftl", mapOf("lines" to lines, "keywordMap" to keywordMap)))
//            }
//        }
//    }.start(wait = true)
//}

package com.example

import freemarker.cache.ClassTemplateLoader
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
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
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }

        routing {

            static("/static") {
                resources("static")
            }

            get("/") {
                val lines = listOf(
                    "fun bubbleSort(arr: IntArray): IntArray {",
                    "    val n = arr.size",
                    "    // The program should loop through every item in the array",
                    "        for (j in 0 until n - i - 1) {",
                    "            // Check if the item to the left is bigger than the one to the right",
                    "                val temp = arr[j]",
                    "                arr[j] = arr[j + 1]",
                    "                arr[j + 1] = temp",
                    "            }",
                    "        }",
                    "    }",
                    "    return arr",
                    "}"
                )

                // Define expected keyword placement
                val keywordMap = mapOf(
                    3 to "for",
                    5 to "if"
                )

                call.respond(FreeMarkerContent("TEST.ftl", mapOf("lines" to lines, "keywordMap" to keywordMap)))
            }
        }
    }.start(wait = true)
}
