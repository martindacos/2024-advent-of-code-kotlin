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

    fun blink(numbers: Sequence<Long>): Sequence<Long> {
        return sequence {
            for (number in numbers) {
                if (number == 0L) {
                    yield(1)
                } else {
                    val evenParts = evenDigits(number)
                    if (evenParts != null) {
                        yield(evenParts.first)
                        yield(evenParts.second)
                    } else {
                        yield(number * 2024)
                    }
                }
            }
        }
    }

    fun recursiveCount(numbers: Sequence<Long>, iterations: Int): Int {
        if (iterations == 0) {
            return numbers.count() // Base case: count the elements lazily
        }
        println("Iteration $iterations")

        val nextNumbers = blink(numbers)
        return recursiveCount(nextNumbers, iterations - 1) // Recursive step: process next iteration
    }

    fun part1(numbers: List<Long>, iterations: Int) {
        val initialSequence = numbers.asSequence()
        val finalCount = recursiveCount(initialSequence, iterations)

        println("Final size after $iterations iterations: $finalCount")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readAndSplitBySpaces("day11/Day_test")
    //part1(testInput, 25)
    part1(testInput, 75)

    // Read the input from the `src/Day.txt` file.
    val input = readAndSplitBySpaces("day11/Day")
    //part1(input, 25, 1000)
    //part1(input, 75)
}
