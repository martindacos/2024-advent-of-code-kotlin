package day16

import findFirstCharInMatrix
import printMatrix
import readMatrix
import java.util.PriorityQueue

fun main() {

    // Directions and their deltas
    val directions = listOf("N" to Pair(-1, 0), "E" to Pair(0, 1), "S" to Pair(1, 0), "W" to Pair(0, -1))

    // Data class for a state in the priority queue
    data class State(
        val x: Int,
        val y: Int,
        val direction: String,
        val cost: Int
    ) : Comparable<State> {
        override fun compareTo(other: State): Int = this.cost - other.cost
    }

    // Function to find the shortest path
    fun findShortestPath(grid: Array<Array<Char>>, startX: Int, startY: Int, endX: Int, endY: Int): Int {
        val rows = grid.size
        val cols = grid[0].size
        val visited = mutableSetOf<Triple<Int, Int, String>>()
        val pq = PriorityQueue<State>()

        // Initial state, facing East
        pq.add(State(startX, startY, "E", 0))

        while (pq.isNotEmpty()) {
            val current = pq.poll()

            // If we've reached the end, return the cost
            if (current.x == endX && current.y == endY) return current.cost

            // Skip if this state was already visited
            if (visited.contains(Triple(current.x, current.y, current.direction))) continue
            visited.add(Triple(current.x, current.y, current.direction))

            // Try all possible actions
            for ((newDirection, delta) in directions) {
                val newX = current.x + delta.first
                val newY = current.y + delta.second

                // Check if the move is valid
                if (newX in 0 until rows && newY in 0 until cols && grid[newX][newY] != '#') {
                    val moveCost = if (current.direction == newDirection) 1 else 1000 + 1
                    pq.add(State(newX, newY, newDirection, current.cost + moveCost))
                }
            }
        }

        // If we exhaust the queue, no path was found
        return -1
    }

    fun part1(matrix: Array<Array<Char>>) {
        printMatrix(matrix)
        val start = findFirstCharInMatrix(matrix, 'S')
        val end = findFirstCharInMatrix(matrix, 'E')

        val result = findShortestPath(matrix, start.first, start.second, end.first, end.second)

        println(result)
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readMatrix("day16/Day_test")
    //part1(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readMatrix("day16/Day")
    part1(input)
}
