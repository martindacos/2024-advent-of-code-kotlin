package day05

import readInput

fun main() {

    fun part1(lines: List<String>) {
        //Rules
        var updates = false
        val rules = mutableListOf<Pair<Int, Int>>()
        val listOfUpdates = mutableListOf<List<Int>>()
        for (line in lines) {
            if (line.isEmpty()) {
                updates = true
                continue
            }
            if (updates) {
                listOfUpdates.add(line.split(",").map { it.trim().toInt() })
            } else {
                val element = line.split("|").map { it.trim().toInt() }.zipWithNext()
                rules.add(element.first())
            }
        }

        val validUpdates = mutableListOf<List<Int>>()
        for (list in listOfUpdates) {
            var isValid = true
            outloop@ for (i in 0 until list.size - 1) {
                for (j in i + 1 until list.size) {
                    val pair = Pair(list[j], list[i])
                    //println(pair) //OK
                    if (rules.contains(pair)) {
                        //println("invalid pair found $pair")
                        //println("list is invalid $list")
                        isValid = false
                        break@outloop
                    }
                }
            }
            if (isValid) {
                //println("list is valid $list")
                validUpdates.add(list)
            }
        }

        //Get the middle number and sum
        val sumOfMiddleElements = validUpdates
            .sumOf {
                val middleIndex = it.size / 2
                it[middleIndex]
            }

        println("sumOfMiddleElements = $sumOfMiddleElements")
    }

    fun part2(lines: List<String>) {
        //Rules
        var updates = false
        val rules = mutableListOf<Pair<Int, Int>>()
        val listOfUpdates = mutableListOf<MutableList<Int>>()
        for (line in lines) {
            if (line.isEmpty()) {
                updates = true
                continue
            }
            if (updates) {
                listOfUpdates.add(line.split(",").map { it.trim().toInt() }.toMutableList())
            } else {
                val element = line.split("|").map { it.trim().toInt() }.zipWithNext()
                rules.add(element.first())
            }
        }

        val invalidUpdates = mutableListOf<List<Int>>()
        for (list in listOfUpdates) {
            var isValid = true
            outloop@ for (i in 0 until list.size - 1) {
                for (j in i + 1 until list.size) {
                    val pair = Pair(list[j], list[i])
                    if (rules.contains(pair)) {
                        //println("invalid pair found $pair. Changing the order")
                        //println("list is invalid $list")

                        // Swap elements at pos1 and pos2
                        val temp = list[j]
                        list[j] = list[i]
                        list[i] = temp

                        isValid = false
                        //break@outloop
                    }
                }
            }
            if (!isValid) {
                //println("new list valid $list")
                invalidUpdates.add(list)
            }
        }

        //Get the middle number and sum
        val sumOfMiddleElements = invalidUpdates
            .sumOf {
                val middleIndex = it.size / 2
                it[middleIndex]
            }

        println("sumOfMiddleElements = $sumOfMiddleElements")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day05/Day_test")
    part1(testInput)
    part2(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day05/Day")
    part1(input)
    part2(input)
}
