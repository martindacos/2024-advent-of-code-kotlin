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

    fun part2(lines: List<String>) {
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
                val targetToMove = result[i]
                //Find all the indices that we want to move
                val indices = result.indices.filter { result[it] == targetToMove }

                //Find consecutive spaces to move all the indices
                val pattern = List(indices.size) { "." }
                for (j in 0..i - pattern.size) {
                    val subList = result.subList(j, j + pattern.size)
                    if (subList == pattern) {
                        //indexOfLastMove = j
                        for (j2 in j until pattern.size + j) result[j2] = targetToMove
                        for (index in indices) result[index] = "."
                        break
                    }
                }
            }
        }

        println("Transformed result: $result")

        var checkSum: Long = 0
        for (i in result.indices) {
            val char = result[i]
            if (char == ".") continue
            checkSum += char.toLong() * i
        }

        println("Checksum: $checkSum")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day09/Day_test")
    //part1(testInput)
    //art2(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day09/Day")
    //part1(input)
    part2(input)
}
