package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val intList: List<List<Int>> = input.map { str ->
            str.split(" ").map { it.trim().toInt() }
        }

        val incremental = mutableListOf<List<Int>>()
        val decremental = mutableListOf<List<Int>>()
        intList.forEach { list ->
            when {
                list == list.sorted() -> incremental.add(list)
                list == list.sortedDescending() -> decremental.add(list)
            }
        }

        var safe = 0
        incremental.forEach { list ->
            var isSafe = true
            list.forEachIndexed { index, value ->
                if (index != list.lastIndex) {
                    val nextValue: Int = list[index + 1]
                    if (nextValue - value == 0  || nextValue - value > 3) {
                        isSafe = false
                        //println(list + " is not safe")
                        return@forEachIndexed
                    }
                }
            }
            if (isSafe) {
                //println(list + " is safe")
                safe += 1
            }
        }

        decremental.forEach { list ->
            var isSafe = true
            list.forEachIndexed { index, value ->
                if (index != list.lastIndex) {
                    val nextValue: Int = list[index + 1]
                    if (value - nextValue == 0  || value - nextValue > 3) {
                        isSafe = false
                        //println(list + " is not safe")
                        return@forEachIndexed
                    }
                }
            }
            if (isSafe) {
                //println(list + " is safe")
                safe += 1
            }
        }

        println(safe)
        return safe
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day02/Day_test")
    part1(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day02/Day")
    part1(input)
}
