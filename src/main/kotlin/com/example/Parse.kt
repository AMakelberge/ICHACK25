package com.example

import java.util.*
import kotlin.random.Random

class Parse(private val lines: List<String>) {
    val NUM_TO_REPLACE = 3
    val CONSTRUCTS_LIST = listOf("for", "if", "while", "map", "filter", "reduce")

    // Original, replaced, Map(lineNum to line)
    fun parse(): Triple<List<String>, List<String>, Map<Int, String>> {
        val constructLines = (0 until lines.size).zip(lines).filter{ (i, line) -> CONSTRUCTS_LIST.any{ line.contains(it) } }
        val takenLines = constructLines.shuffled(Random).take(NUM_TO_REPLACE).toList()
        val randomIndices = takenLines.map { it.first }

        val singleString = lines.joinToString(separator = "\n")
        val bytes = singleString.toByteArray(Charsets.UTF_8)
        val encodedLines = Base64.getEncoder().encodeToString(bytes)

        val query = """This is my code which I have encoded in Base64: $encodedLines Decode it and give me comments for lines: $randomIndices Only return the comments, separated by a newline. The comments should be hints as to what to write on the line.""".trimIndent()

        val answer = OpenAiService.getAnswer(query)

        val comments = randomIndices.zip(OpenAiService.codeToLines(answer)).toMap()

        val indexToLine = comments.keys.map{ it to lines[it] }.toMap()

        val replaced = lines.mapIndexed { i, line -> comments[i] ?: line }

        return Triple(lines, replaced, indexToLine)
    }

}
