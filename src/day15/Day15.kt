package day15

import readInput

fun main() {

    fun parseInput(allLines: List<String>): Pair<Array<CharArray>, List<Char>> {
        // Split the input into lines
        val lines = allLines.filter { it.isNotBlank() }

        // The matrix of chars is all lines up to the end of the grid
        val matrixLines = lines.takeWhile { it.all { char -> char != '<' && char != '>' && char != '^' && char != 'v' } }

        // Parse the matrix
        val matrix = matrixLines.map { it.toCharArray() }.toTypedArray()

        // The last lines as a concatenated list of chars
        val commandLines = lines.drop(matrixLines.size)
        val lastLine = commandLines.joinToString("").toList()

        return Pair(matrix, lastLine)
    }

    fun printMatrix(matrix: Array<CharArray>) {
        matrix.forEach { println(it.joinToString("")) }
        println()
    }

    fun findAtPosition(matrix: Array<CharArray>): Pair<Int, Int> {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == '@') {
                    return i to j
                }
            }
        }
        return Pair(-1, -1)
    }

    fun findObstacles(matrix: Array<CharArray>): Int {
        var totalCoordinates = 0
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 'O') {
                    totalCoordinates += 100 * i + j
                }
            }
        }
        return totalCoordinates
    }

    fun moveObject(matrix: Array<CharArray>, x: Int, y: Int, dx: Int, dy: Int): Boolean {
        val newX = x + dx
        val newY = y + dy

        // Ensure the move is within bounds and not into a wall (#)
        if (newX in matrix.indices && newY in matrix[0].indices) {
            when (matrix[newX][newY]) {
                '.' -> {
                    // Move the object
                    matrix[newX][newY] = matrix[x][y]
                    matrix[x][y] = '.'
                    return true
                }
                'O' -> {
                    // Try to move the obstacle
                    if (moveObject(matrix, newX, newY, dx, dy)) {
                        matrix[newX][newY] = matrix[x][y]
                        matrix[x][y] = '.'
                        return true
                    }
                }
            }
        }
        return false
    }

    fun part1(lines: List<String>) {
        val (matrix, movements) = parseInput(lines)

        var position = findAtPosition(matrix)

        val directions = mapOf(
            '^' to (-1 to 0),
            'v' to (1 to 0),
            '<' to (0 to -1),
            '>' to (0 to 1)
        )

        for (move in movements) {
            val (dx, dy) = directions[move] ?: continue
            val (x, y) = position

            val newX = x + dx
            val newY = y + dy

            // Ensure the move is within bounds and not into a wall (#)
            if (newX in matrix.indices && newY in matrix[0].indices && matrix[newX][newY] != '#') {
                if (moveObject(matrix, x, y, dx, dy)) {
                    position = newX to newY
                }
            }
        }

        printMatrix(matrix)
        val findObstacles = findObstacles(matrix)
        println("findObstacles: $findObstacles")
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day15/Day_test")
    //part1(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day15/Day")
    part1(input)
}
