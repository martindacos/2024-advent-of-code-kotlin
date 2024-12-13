package day13

import readInput

fun main() {

    fun readRegex(line: String, regex: Regex): Pair<Int, Int> {
        val matchResult = regex.find(line)
        if (matchResult != null) {
            val (x, y) = matchResult.destructured
            //println("X: $x")
            //println("Y: $y")
            return Pair(x.toInt(), y.toInt())
        } else {
            //println("No match found")
        }

        return Pair(-1, -1)
    }

    fun solve(lines: List<String>): Int {
        val target = readRegex(lines[2], "X=(\\d+), Y=(\\d+)".toRegex())
        val xTarget = target.first
        val yTarget = target.second

        // Button increments
        val aButton = readRegex(lines[0], "X\\+(\\d+), Y\\+(\\d+)".toRegex())
        val aX = aButton.first
        val aY = aButton.second
        val bButton = readRegex(lines[1], "X\\+(\\d+), Y\\+(\\d+)".toRegex())
        val bX = bButton.first
        val bY = bButton.second

        // Costs per press
        val costA = 3
        val costB = 1

        var optimalA = -1
        var optimalB = -1
        var minCost = Int.MAX_VALUE

        // Iterate through possible values of a
        for (a in 0..(xTarget / aX)) {
            val remainingX = xTarget - a * aX
            val remainingY = yTarget - a * aY

            // Check if remaining values are divisible by Button B increments
            if (remainingX >= 0 && remainingY >= 0 && remainingX % bX == 0 && remainingY % bY == 0) {
                val b = remainingX / bX // Derive b from remainingX

                // Validate that b also satisfies the Y equation
                if (remainingY == b * bY) {
                    val cost = a * costA + b * costB
                    if (cost < minCost) {
                        minCost = cost
                        optimalA = a
                        optimalB = b
                    }
                }
            }
        }

        if (optimalA != -1 && optimalB != -1) {
            println("Optimal solution: A = $optimalA, B = $optimalB, Minimum Cost = $minCost")
            return minCost
        } else {
            println("No solution found")
            return 0
        }
    }

    fun part1(lines: List<String>) {
        var totalTokens = 0
        for (i in lines.indices step 4) {
            totalTokens += solve(listOf(lines[i], lines[i + 1], lines[i + 2]))
        }

        println("Total tokens: $totalTokens")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day13/Day_test")
    //part1(testInput)
    //part1(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day13/Day")
    part1(input)
    //part1(input)
}
