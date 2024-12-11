package day11

import readAndSplitBySpaces

fun main() {

    fun evenDigits(number: Long): Pair<Long, Long>? {
        // Convert the number to a string and take its absolute value to handle negatives
        val numStr = number.toString()
        if (numStr.length % 2 != 0) {
            return null
        }

        // Split the string into two equal parts
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
                    val evenDigits = evenDigits(number)
                    if (evenDigits != null) {
                        yield(evenDigits.first)
                        yield(evenDigits.second)
                    } else {
                        yield(number * 2024)
                    }
                }
            }
        }
    }

    fun countElements(sequence: Sequence<Long>): Long {
        var count = 0L
        sequence.forEach { _ -> count++ }
        return count
    }

    fun part1(numbers: List<Long>, iterations: Int) {
        var currentNumbers: Sequence<Long> = numbers.asSequence()
        for (i in 0 until iterations) {
            currentNumbers = blink(currentNumbers)

            println("Iteration $i has size ${countElements(currentNumbers)}")
            //println("Iteration $i completed")
        }

        //println(countElements(currentNumbers))
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readAndSplitBySpaces("day11/Day_test")
    //part1(testInput, 6)
    part1(testInput, 75)

    // Read the input from the `src/Day.txt` file.
    val input = readAndSplitBySpaces("day11/Day")
    //part1(input, 25)
    //part1(input, 75)
}
