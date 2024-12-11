package day11

import readAndSplitBySpaces

fun main() {


    fun evenDigits(number: Long): Pair<Long, Long>? {
        val numStr = number.toString()
        if (numStr.length % 2 != 0) {
            return null
        }

        val mid = numStr.length / 2
        val part1 = numStr.substring(0, mid).toLong()
        val part2 = numStr.substring(mid).toLong()

        return Pair(part1, part2)
    }

    fun blinkChunk(numbers: List<Long>): List<Long> {
        val newNumbers = mutableListOf<Long>()
        for (number in numbers) {
            if (number == 0L) {
                newNumbers.add(1)
            } else {
                val evenParts = evenDigits(number)
                if (evenParts != null) {
                    newNumbers.add(evenParts.first)
                    newNumbers.add(evenParts.second)
                } else {
                    newNumbers.add(number * 2024)
                }
            }
        }
        return newNumbers
    }

    fun chunkedProcess(numbers: List<List<Long>>, chunkSize: Int): MutableList<List<Long>> {
        val chunkedResults: MutableList<List<Long>> = mutableListOf()

        for (number in numbers) {
            number.chunked(chunkSize).forEach { chunk ->
                val blinkResult = blinkChunk(chunk)
                chunkedResults.add(blinkResult)
            }
        }

        return chunkedResults
    }

    fun countAllListSizes(numbers: List<List<Long>>): Long {
        var counter = 0L

        for (number in numbers) {
            counter += number.count()
        }

        return counter
    }

    fun part1(numbers: List<Long>, iterations: Int, chunkSize: Int) {
        var currentNumbers: List<List<Long>> = mutableListOf(numbers)
        for (i in 0 until iterations) {
            currentNumbers = chunkedProcess(currentNumbers, chunkSize)
            //println("Iteration $i has size ${currentNumbers.size}")
            println("Iteration $i completed")
        }

        println("Final size: ${countAllListSizes(currentNumbers)}")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readAndSplitBySpaces("day11/Day_test")
    //part1(testInput, 6, 1000)
    part1(testInput, 75, 1000)

    // Read the input from the `src/Day.txt` file.
    val input = readAndSplitBySpaces("day11/Day")
    //part1(input, 25, 1000)
    //part1(input, 75)
}
