package day01

import processFile
import kotlin.math.abs

fun main() {
    fun part1(numbers: Pair<List<Int>, List<Int>>): Int {
        val sorted1 = numbers.first.sorted()
        val sorted2 = numbers.second.sorted()

        var result = 0
        for (i in sorted1.indices) {
            val abs = abs(sorted1[i] - sorted2[i])
            //println(abs)
            result += abs
        }

        println(result)
        return result
    }

    fun part2(numbers: Pair<List<Int>, List<Int>>): Int {
        val list1 = numbers.first
        val list2 = numbers.second

        var result = 0
        list1.forEachIndexed { index, value ->
            val count = list2.count{it == value}
            val value = count * value
            //println(value)
            result += value
        }

        println(result)
        return result
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = processFile("day01/Day01_test")
    part1(testInput)
    part2(testInput)

    // Read the input from the `src/Day01.txt` file.
    val input = processFile("day01/Day01")
    part1(input)
    part2(input)
}
