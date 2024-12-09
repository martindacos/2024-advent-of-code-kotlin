package day09

import readInput

fun main() {

    fun part1(lines: List<String>) {
        val line = lines.first().toMutableList()

        val result = mutableListOf<String>()
        var blockId = 0
        var block = true
        for (char in line) {
            if (block) {
                for (r in 0 until char.digitToInt()) result.add(blockId.toString())
                blockId++
            } else {
                for (r in 0 until char.digitToInt()) result.add(".")
            }
            block = !block
        }

        println("Result: $result")
        for (i in result.indices.reversed()) {
            if (result[i] != ".") {
                val indexOfFirstPoint = result.indexOfFirst { it == "." }
                if (indexOfFirstPoint >= i) {
                    break
                }
                result[indexOfFirstPoint] = result[i]
                result[i] = "."
            }
        }

        println("Transformed result: $result")

        var checkSum: Long = 0
        for (i in result.indices) {
            val char = result[i]
            if (char == ".") {break}
            checkSum += char.toLong() * i
        }

        println("Checksum: $checkSum")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day09/Day_test")
    //part1(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day09/Day")
    part1(input)
}
