package day11

import readAndSplitBySpaces

fun main() {

    fun evenDigits(number: Long): List<Long> {
        // Convert the number to a string and take its absolute value to handle negatives
        val numStr = number.toString()
        if (numStr.length % 2 != 0) {
            return emptyList()
        }

        // Split the string into two equal parts
        val mid = numStr.length / 2
        val part1 = numStr.substring(0, mid).toLong()
        val part2 = numStr.substring(mid).toLong()

        return mutableListOf(part1, part2)
    }

    fun blink(numbers: List<Long>): MutableList<Long> {
        val newNumbers = mutableListOf<Long>()

        for (number in numbers) {
            if (number == 0L) {
                newNumbers.add(1)
            } else {
                val evenDigits = evenDigits(number)
                if (evenDigits.isNotEmpty()) {
                    newNumbers.addAll(evenDigits)
                } else {
                    newNumbers.add(number * 2024)
                }
            }
        }

        return newNumbers
    }

    fun part1(numbers: List<Long>, iterations: Int) {
        var newNumbers = numbers.toMutableList()
        for (i in 0 until iterations) {
            newNumbers = blink(newNumbers)
            println("Iteration $i has size ${newNumbers.size}")
        }

        println(newNumbers.size)
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readAndSplitBySpaces("day11/Day_test")
    //part1(testInput, 6)

    // Read the input from the `src/Day.txt` file.
    val input = readAndSplitBySpaces("day11/Day")
    //part1(input, 25)
    part1(input, 75)
}
