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

    // Data class for a state in the priority queue
    data class AllPathsState(
        val x: Int,
        val y: Int,
        val direction: String,
        val cost: Int,
        val path: List<Pair<Int, Int>> // Track the path to this state
    ) : Comparable<AllPathsState> {
        override fun compareTo(other: AllPathsState): Int = this.cost - other.cost
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

    // Function to find all shortest paths
    fun findAllBestPaths(grid: Array<Array<Char>>, startX: Int, startY: Int, endX: Int, endY: Int): Pair<Int, List<List<Pair<Int, Int>>>> {
        val rows = grid.size
        val cols = grid[0].size
        val visited = mutableMapOf<Triple<Int, Int, String>, Int>() // Store minimum cost for each state
        val pq = PriorityQueue<AllPathsState>()
        val bestPaths = mutableListOf<List<Pair<Int, Int>>>()
        var minCost = Int.MAX_VALUE

        // Initial state, facing East
        pq.add(AllPathsState(startX, startY, "E", 0, listOf(startX to startY)))

        while (pq.isNotEmpty()) {
            val current = pq.poll()

            // If we've already explored this state with a lower cost, skip it
            val stateKey = Triple(current.x, current.y, current.direction)
            if (visited[stateKey] != null && visited[stateKey]!! < current.cost) continue
            visited[stateKey] = current.cost

            // If we reached the target
            if (current.x == endX && current.y == endY) {
                if (current.cost < minCost) {
                    // New minimum cost found, clear previous paths
                    minCost = current.cost
                    bestPaths.clear()
                }
                if (current.cost == minCost) {
                    // Add this path as one of the best
                    bestPaths.add(current.path)
                }
                continue
            }

            // Try all possible actions
            for ((newDirection, delta) in directions) {
                val newX = current.x + delta.first
                val newY = current.y + delta.second


                // Check if the move is valid
                if (newX in 0 until rows && newY in 0 until cols && grid[newX][newY] != '#') {
                    val moveCost = if (current.direction == newDirection) 1 else 1000 + 1
                    val newCost = current.cost + moveCost
                    val newPath = current.path + (newX to newY)

                    // Add to the queue if not visited with a lower cost
                    if (visited[Triple(newX, newY, newDirection)] == null || visited[Triple(newX, newY, newDirection)]!! > newCost) {
                        pq.add(AllPathsState(newX, newY, newDirection, newCost, newPath))
                    }
                }
            }
        }

        return Pair(minCost, bestPaths)
    }

    fun findAllDifferentPoints(paths: List<List<Pair<Int, Int>>>): Set<Pair<Int, Int>> {
        if (paths.isEmpty()) return emptySet()

        // Start with the points from the first path
        var allPoints = paths.first().toSet()

        // Add the points from all other paths
        for (path in paths) {
            allPoints = allPoints.plus(path.toSet())
        }

        return allPoints
    }

    fun part1(matrix: Array<Array<Char>>) {
        printMatrix(matrix)
        val start = findFirstCharInMatrix(matrix, 'S')
        val end = findFirstCharInMatrix(matrix, 'E')

        val result = findShortestPath(matrix, start.first, start.second, end.first, end.second)

        println(result)
    }

    fun part2(matrix: Array<Array<Char>>) {
        printMatrix(matrix)
        val start = findFirstCharInMatrix(matrix, 'S')
        val end = findFirstCharInMatrix(matrix, 'E')

        val result = findAllBestPaths(matrix, start.first, start.second, end.first, end.second)
        val differentPoints = findAllDifferentPoints(result.second)
        println(differentPoints.size)
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readMatrix("day16/Day_test")
    //part1(testInput)
    //part2(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readMatrix("day16/Day")
    //part1(input)
    part2(input)
}
