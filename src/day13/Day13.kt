package day13

import readInput

fun main() {

    fun readRegex(line: String, regex: Regex): Pair<Long, Long> {
        val matchResult = regex.find(line)
        if (matchResult != null) {
            val (x, y) = matchResult.destructured
            //println("X: $x")
            //println("Y: $y")
            return Pair(x.toLong(), y.toLong())
        } else {
            //println("No match found")
        }

        return Pair(-1, -1)
    }

    fun solve(lines: List<String>, conversion: Long): Long {
        val target = readRegex(lines[2], "X=(\\d+), Y=(\\d+)".toRegex())
        val xTarget = target.first + conversion
        val yTarget = target.second + conversion

        // Button increments
        val aButton = readRegex(lines[0], "X\\+(\\d+), Y\\+(\\d+)".toRegex())
        val aX = aButton.first
        val aY = aButton.second
        val bButton = readRegex(lines[1], "X\\+(\\d+), Y\\+(\\d+)".toRegex())
        val bX = bButton.first
        val bY = bButton.second

        val costA = 3L

        fun areMultiples(): Boolean = when {
            aX * bY != aY * bX -> false
            aX * yTarget != aY * xTarget -> false
            bX * yTarget != bY * xTarget -> false
            else -> true
        }

        if (areMultiples()) { // Infinite Solutions
            if (aX != 0L) {
                val a = xTarget / aX
                if (xTarget % aX == 0L && a >= 0) {
                    return a * costA + 0L
                }
            }
            if (bX != 0L) {
                val b = xTarget / bX
                if (xTarget % bX == 0L && b >= 0) {
                    return 0L * costA + b
                }
            }
            return 0L
        }

        val delta = aX * bY - aY * bX
        if (delta != 0L) { // Unique solution
            val x = (xTarget * bY - yTarget * bX) / delta
            if ((xTarget * bY - yTarget * bX) % delta != 0L) {
                return 0L
            }
            val y = (aX * yTarget - aY * xTarget) / delta
            if ((aX * yTarget - aY * xTarget) % delta != 0L) {
                return 0L
            }
            if (x >= 0 && y >= 0) {
                return x * costA + y
            }
        }

        return 0L
    }

    fun part1(lines: List<String>, conversion: Long) {
        var totalTokens = 0L
        for (i in lines.indices step 4) {
            totalTokens += solve(listOf(lines[i], lines[i + 1], lines[i + 2]), conversion)
        }

        println("Total tokens: $totalTokens")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day13/Day_test")
    //part1(testInput, 0L)
    //part1(testInput, 10000000000000L)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day13/Day")
    //part1(input, 0L)
    part1(input, 10000000000000L)
}
