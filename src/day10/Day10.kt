package day10

import readInput

fun main() {

    fun getAdjacentValues(
        matrix: Array<Array<Int>>,
        position: Pair<Int, Int>,
        target: Int
    ): List<Pair<Int, Pair<Int, Int>>> {
        val adjacentValues = mutableListOf<Pair<Int, Pair<Int, Int>>>()

        // Define all possible directions (up, down, left, right, and diagonals)
        val directions = listOf(
            Pair(-1, 0), // Up
            Pair(1, 0),  // Down
            Pair(0, -1), // Left
            Pair(0, 1)  // Right
        )

        for (direction in directions) {
            val newRow = position.first + direction.first
            val newCol = position.second + direction.second

            // Check if the new position is within bounds
            if (newRow in matrix.indices && newCol in matrix.indices && matrix[newRow][newCol] == target) {
                adjacentValues.add(Pair(target, Pair(newRow, newCol)))
            }
        }

        return adjacentValues
    }

    fun findPaths(
        matrix: Array<Array<Int>>,
        position: Pair<Int, Int>,
        pathList: MutableList<Pair<Int, Pair<Int, Int>>>
    ): List<Pair<Int, Pair<Int, Int>>> {
        val target = matrix[position.first][position.second] + 1
        if (target == 10) {
            //We reached the end in this position
            //return mutableListOf(Pair(9, position))
            return pathList
        }

        val nextPositions = getAdjacentValues(
            matrix,
            position,
            target
        )

        for (nextPosition in nextPositions) {
            pathList.add(nextPosition)
            findPaths(matrix, nextPosition.second, pathList)
        }

        return pathList
    }

    fun part1(lines: List<String>) {
        val matrix: Array<Array<Int>> = lines
            .map {
                it.toCharArray().map { c -> if (c == '.') -1 else c.digitToInt() }.toTypedArray()
            }.toTypedArray()

        val positions = mutableListOf<Pair<Int, Int>>()

        for (rowIndex in matrix.indices) {
            for (colIndex in matrix[rowIndex].indices) {
                if (matrix[rowIndex][colIndex] == 0) {
                    positions.add(Pair(rowIndex, colIndex)) // Add the position to the list.
                }
            }
        }

        var score = 0
        for (initPosition in positions) {
            val pathList = mutableListOf<Pair<Int, Pair<Int, Int>>>()
            findPaths(matrix, initPosition, pathList)

            val distinctEnds = pathList.filter { it.first == 9 }.distinct()
            println("Score for $initPosition is ${distinctEnds.size}")
            score += distinctEnds.size
        }

        println(score)
    }

    fun part2(lines: List<String>) {
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day10/Day_test")
    //part1(testInput)
    //art2(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day10/Day")
    part1(input)
    //part2(input)
}
