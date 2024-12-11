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

    fun blink(numbers: Map<Long, Long>): Map<Long, Long> {
        val newNumbers: MutableMap<Long, Long> = mutableMapOf()

        for (number in numbers) {
            val count = number.value
            if (number.key == 0L) {
                val currentCount = newNumbers.getOrDefault(1L, 0)
                newNumbers[1L] = currentCount + count
            } else {
                val evenParts = evenDigits(number.key)
                if (evenParts != null) {
                    val currentCount = newNumbers.getOrDefault(evenParts.first, 0)
                    newNumbers[evenParts.first] = currentCount + count
                    val currentCount2 = newNumbers.getOrDefault(evenParts.second, 0)
                    newNumbers[evenParts.second] = currentCount2 + count
                } else {
                    val currentCount = newNumbers.getOrDefault(number.key * 2024, 0)
                    newNumbers[number.key * 2024] = currentCount + count
                }
            }
        }

        return newNumbers
    }

    fun countSize(numbers: Map<Long, Long>): Long {
        return numbers.values.sum()
    }

    fun part1(numbers: List<Long>, iterations: Int) {
        var initialMap = numbers.map { it to 1L }.toMap()
        for (i in 0 until iterations) {
            initialMap = blink(initialMap)
        }

        println("Final size after $iterations iterations is ${countSize(initialMap)}")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readAndSplitBySpaces("day11/Day_test")
    //part1(testInput, 25)
    //part1(testInput, 75)

    // Read the input from the `src/Day.txt` file.
    val input = readAndSplitBySpaces("day11/Day")
    //part1(input, 25)
    part1(input, 75)
}
