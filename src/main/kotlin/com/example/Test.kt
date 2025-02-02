package com.example

fun main(){
    val prompt = "Create an algorithm for bubble sort in Kotlin. Do not include the main function. There should only be one function, which returns the array. You do not need to make use of this function, purely implement it. Do not write a comment on any lines."
    val openAiResponse = OpenAiService.getAnswer(prompt)
    val p = Parse(OpenAiService.codeToLines(openAiResponse))
    val parsed = p.parse()
    println(p)
    println(parsed)
}