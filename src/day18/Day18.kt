package day18

import printMatrix
import readInput
import java.util.PriorityQueue

fun main() {

    // Directions and their deltas
    val directions =
        listOf("N" to Pair(-1, 0), "E" to Pair(0, 1), "S" to Pair(1, 0), "W" to Pair(0, -1))

    // Data class for a state in the priority queue
    data class State(
        val x: Int,
        val y: Int,
        val direction: String,
        val cost: Int,
        val path: List<Pair<Int, Int>> // Track the path to this state
    ) : Comparable<State> {
        override fun compareTo(other: State): Int = this.cost - other.cost
    }

    // Function to find the shortest path
    fun findShortestPath(
        grid: Array<Array<Char>>,
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int
    ): State {
        val rows = grid.size
        val cols = grid[0].size
        val visited = mutableSetOf<Triple<Int, Int, String>>()
        val pq = PriorityQueue<State>()

        // Initial state, facing East
        pq.add(State(startX, startY, "E", 0, listOf(startX to startY)))

        while (pq.isNotEmpty()) {
            val current = pq.poll()

            // If we've reached the end, return the cost
            if (current.x == endX && current.y == endY) return current

            // Skip if this state was already visited
            if (visited.contains(Triple(current.x, current.y, current.direction))) continue
            visited.add(Triple(current.x, current.y, current.direction))

            // Try all possible actions
            for ((newDirection, delta) in directions) {
                val newX = current.x + delta.first
                val newY = current.y + delta.second
                val newPath = current.path + (newX to newY)

                // Check if the move is valid
                if (newX in 0 until rows && newY in 0 until cols && grid[newX][newY] != '#') {
                    val moveCost = 1
                    pq.add(State(newX, newY, newDirection, current.cost + moveCost, newPath))
                }
            }
        }

        // If we exhaust the queue, no path was found
        return State(startX, startY, "E", 0, listOf(startX to startY))
    }

    fun createCharMatrix(rows: Int, cols: Int, initialValue: Char): Array<Array<Char>> {
        return Array(rows) { Array(cols) { initialValue } }
    }

    fun putObstacles(matrix: Array<Array<Char>>, obstacles: List<Pair<Int, Int>>, toRead: Int) {
        for ((index, obstacle) in obstacles.withIndex()) {
            if (index > toRead - 1) return
            matrix[obstacle.second][obstacle.first] = '#'
        }
    }

    fun part1(lines: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>, toRead: Int) {
        val matrix = createCharMatrix(end.first + 1, end.second + 1, '.')
        val obstacles = lines.map { line ->
            val (first, second) = line.split(",").map { it.trim().toInt() }
            Pair(first, second)
        }
        putObstacles(matrix, obstacles, toRead)
        //printMatrix(matrix)

        val result = findShortestPath(matrix, start.first, start.second, end.first, end.second)

        println(result)
    }

    fun part2(
        lines: List<String>,
        start: Pair<Int, Int>,
        end: Pair<Int, Int>,
        toRead: Int
    ) {
        val matrix = createCharMatrix(end.first + 1, end.second + 1, '.')
        val obstacles = lines.map { line ->
            val (first, second) = line.split(",").map { it.trim().toInt() }
            Pair(first, second)
        }

        for (i in toRead + 1 until obstacles.size) {
            val matrixCopy = matrix.clone()
            putObstacles(matrixCopy, obstacles, i)
            val result = findShortestPath(matrixCopy, start.first, start.second, end.first, end.second)

            if (result.cost == 0) {
                println(obstacles[i - 1])
                printMatrix(matrixCopy)
                return
            }
        }
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day18/Day_test")
    //part1(testInput, Pair(0, 0), Pair(6, 6), 12)
    //part2(testInput, Pair(0, 0), Pair(6, 6), 12)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day18/Day")
    //part1(input, Pair(0, 0), Pair(70, 70), 1024)
    part2(input, Pair(0, 0), Pair(70, 70), 1024)
}
