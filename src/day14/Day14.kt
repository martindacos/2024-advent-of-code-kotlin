package day14

import readInput

fun main() {

    fun readRobot(line: String): Pair<Pair<Long, Long>, Pair<Long, Long>> {
        val match = """p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""".toRegex().matchEntire(line)
        if (match != null) {
            val (x, y, vX, vY) = match.destructured
            return Pair(Pair(x.toLong(), y.toLong()), Pair(vX.toLong(), vY.toLong()))
        } else {
            //println("No match found")
        }

        return Pair(Pair(-1, -1), Pair(-1, -1))
    }

    fun move(
        position: Long,
        velocity: Long,
        space: Long
    ): Long {
        return if (position + velocity < 0) {
            space + position + velocity
        } else if (position + velocity >= space) {
            position + velocity - space
        } else {
            position + velocity
        }
    }

    fun moveRobot(
        position: Pair<Long, Long>,
        velocity: Pair<Long, Long>,
        space: Pair<Long, Long>
    ): Pair<Long, Long> {
        val newX = move(position.first, velocity.first, space.first)
        val newY = move(position.second, velocity.second, space.second)

        //println("Moving $position with $velocity to ($newX, $newY)")
        return Pair(newX, newY)
    }

    fun robotInQuadrant(
        position: Pair<Long, Long>,
        quadrant: Pair<Pair<Long, Long>, Pair<Long, Long>>
    ): Boolean {
        val (x1, y1) = quadrant.first
        val (x2, y2) = quadrant.second
        return position.first in x1..x2 && position.second in y1..y2
    }

    fun printMatrix(width: Int, height: Int, positions: List<Pair<Long, Long>>) {
        // Create an empty grid filled with dots
        val grid = Array(height) { CharArray(width) { '.' } }

        // Mark positions in the grid with '#'
        for ((x, y) in positions) {
            if (x in 0 until width && y in 0 until height) {
                val current = grid[y.toInt()][x.toInt()]
                if (current == '.') {
                    grid[y.toInt()][x.toInt()] = '1'
                } else {
                    val newValue = current.digitToInt() + 1
                    grid[y.toInt()][x.toInt()] = newValue.digitToChar()
                }
            }
        }

        // Print the grid row by row
        for (row in grid) {
            println(row.concatToString())
        }
    }

    fun solve(
        robotPositions: MutableList<Pair<Pair<Long, Long>, Pair<Long, Long>>>,
        space: Pair<Long, Long>
    ): Int {
        for (j in robotPositions.indices) {
            val newPosition = moveRobot(robotPositions[j].first, robotPositions[j].second, space)
            robotPositions[j] = Pair(newPosition, robotPositions[j].second)
        }

        //printMatrix(space.first.toInt(), space.second.toInt(), robotPositions.map { it.first })

        val midCol = space.first / 2
        val midRow = space.second / 2
        val quadrantBounds = listOf(
            Pair(0L to 0L, (midCol - 1) to (midRow - 1)), // Top-left
            Pair((midCol + 1) to 0L, (space.first - 1) to (midRow - 1)), // Top-right
            Pair(0L to (midRow + 1), (midCol - 1) to (space.second - 1)), // Bottom-left
            Pair(
                (midCol + 1) to (midRow + 1),
                (space.first - 1) to (space.second - 1)
            ) // Bottom-right
        )

        var safetyFactor = 1
        for (quadrantBound in quadrantBounds) {
            var robotsInQuadrant = 0
            for (robot in robotPositions) {
                if (robotInQuadrant(robot.first, quadrantBound)) {
                    //println("$robot is in $quadrantBound")
                    robotsInQuadrant++
                }
            }

            safetyFactor *= robotsInQuadrant
        }

        //println("safetyFactor: $safetyFactor")

        return safetyFactor
    }

    fun part1(lines: List<String>, space: Pair<Long, Long>, iterations: Int): Int {
        val robotPositions = mutableListOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()
        for (line in lines) {
            robotPositions.add(readRobot(line))
        }
        for (i in 0 until iterations) {
            solve(robotPositions, space)
        }

        return robotPositions.size
    }

    fun part2(lines: List<String>, space: Pair<Long, Long>) {
        var time = 1
        var lowestEntropyTime = 0
        var timeSinceLowerEntropy = 0

        val robotPositions = mutableListOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()
        for (line in lines) {
            robotPositions.add(readRobot(line))
        }

        //var lowestEntropy = solve(robotPositions, space)
        var lowestEntropy = Integer.MAX_VALUE
        while (timeSinceLowerEntropy < 11_000) {
            val entropy = solve(robotPositions, space)
            if (entropy < lowestEntropy) {
                lowestEntropy = entropy
                lowestEntropyTime = time
                println("resetting entrypoint at: $timeSinceLowerEntropy")
                timeSinceLowerEntropy = 0
            } else {
                timeSinceLowerEntropy++
            }
            if (time == 6355) {
                printMatrix(space.first.toInt(), space.second.toInt(), robotPositions.map { it.first })
            }
            time++
            //println("timeSinceLowerEntropy: $timeSinceLowerEntropy")
        }

        println("lowestEntropy time: $lowestEntropyTime")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day14/Day_test")
    //part1(testInput, Pair(11L, 7L), 100)
    //part2(testInput, Pair(11L, 7L))

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day14/Day")
    //part1(input, Pair(101L, 103L), 100)
    part2(input, Pair(101L, 103L))
}
