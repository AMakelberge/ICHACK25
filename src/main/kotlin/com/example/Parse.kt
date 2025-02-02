package com.example

import java.util.*
import kotlin.random.Random

class Parse(lines: List<String>) {
    val NUM_TO_REPLACE = 3
    val CONSTRUCTS_LIST = listOf("for", "if", "while", "map", "filter", "reduce")
    val lines = lines.filterNot { it.isBlank() }

    fun applyIndents(source: String, target: String): String {
        val indentation = source.takeWhile{it.isWhitespace()}
        return indentation + target
    }


    // Original, replaced, Map(lineNum to construct)
    fun parse(): Triple<List<String>, List<String>, Map<Int, String>> {
        val constructLines = lines.indices.zip(lines).filter{ (i, line) -> CONSTRUCTS_LIST.any{ line.contains(it) } }
        val takenLines = constructLines.shuffled(Random).take(NUM_TO_REPLACE).toList().sortedBy { it.first }
        val takenConstructs = takenLines.associate { (i, line) -> i to CONSTRUCTS_LIST.find { line.contains(it) }!! }
        val randomIndices = takenLines.map { it.first }
        val randomLines = takenLines.map { it.second }

        val singleString = lines.joinToString(separator = "\n")
        val bytes = singleString.toByteArray(Charsets.UTF_8)
        val encodedLines = Base64.getEncoder().encodeToString(bytes)

        val query = """This is my code which I have encoded in Base64: $encodedLines Decode it and give me comments for these lines: $randomLines Only return the comments, separated by a newline. The comments should describe what the line does. Make the comment short and use simplified language. Kotlin.""".trimIndent()

        val answer = OpenAiService.getAnswer(query)

        val comments = randomIndices.zip(OpenAiService.codeToLines(answer)).toMap()

        val indexToLine = comments.keys.associateWith { lines[it] }

        val replaced = lines.mapIndexed { i, line -> comments[i]?.let { applyIndents(line, it) } ?: line }

        println(lines.joinToString(separator = "\n"))
        println(replaced.joinToString("\n"))

        return Triple(lines, replaced, indexToLine)
    }

}
