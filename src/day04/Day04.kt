package day04

import readInput

fun main() {

    fun part1(lines: List<String>) {
        val wordSearch = lines.toTypedArray()

        val word = "XMAS"
        val directions = listOf(
            Pair(0, 1),   // Horizontal (right)
            Pair(1, 0),   // Vertical (down)
            Pair(1, 1),   // Diagonal (down-right)
            Pair(-1, 1),  // Diagonal (up-right)
            Pair(0, -1),  // Horizontal (left)
            Pair(-1, 0),  // Vertical (up)
            Pair(1, -1),  // Diagonal (down-left)
            Pair(-1, -1)  // Diagonal (up-left)
        )

        var count = 0

        fun isValidPosition(x: Int, y: Int): Boolean {
            return x in wordSearch.indices && y in wordSearch[0].indices
        }

        fun findWord(x: Int, y: Int, dx: Int, dy: Int): Boolean {
            for (i in word.indices) {
                val nx = x + i * dx
                val ny = y + i * dy
                if (!isValidPosition(nx, ny) || wordSearch[nx][ny] != word[i]) return false
            }
            return true
        }

        for (x in wordSearch.indices) {
            for (y in wordSearch[0].indices) {
                for ((dx, dy) in directions) {
                    if (findWord(x, y, dx, dy)) {
                        count++
                    }
                }
            }
        }

        println("Total occurrences of '$word': $count")
    }

    fun part2(lines: List<String>) {
        val wordSearch = lines.toTypedArray()

        val word = "MAS"
        val directions = listOf(
            Pair(1, 1),   // Diagonal (down-right)
            Pair(-1, 1),  // Diagonal (up-right)
            Pair(1, -1),  // Diagonal (down-left)
            Pair(-1, -1)  // Diagonal (up-left)
        )

        val markedGrid = Array(wordSearch.size) { CharArray(wordSearch[0].length) { '.' } }
        var count = 0

        fun isValidPosition(x: Int, y: Int): Boolean {
            return x in wordSearch.indices && y in wordSearch[0].indices
        }

        fun findWord(x: Int, y: Int, dx: Int, dy: Int): Boolean {
            for (i in word.indices) {
                val nx = x + i * dx
                val ny = y + i * dy
                if (!isValidPosition(nx, ny) || wordSearch[nx][ny] != word[i]) return false
            }
            return true
        }

        fun markWord(x: Int, y: Int, dx: Int, dy: Int) {
            for (i in word.indices) {
                val nx = x + i * dx
                val ny = y + i * dy
                markedGrid[nx][ny] = wordSearch[nx][ny]
            }
        }

        for (x in wordSearch.indices) {
            for (y in wordSearch[0].indices) {
                for ((dx, dy) in directions) {
                    if (findWord(x, y, dx, dy)) {
                        count++
                        markWord(x, y, dx, dy)
                    }
                }
            }
        }

        // Output results
        println("Total occurrences of MAS: $count")
        println("Marked grid:")
        for (row in markedGrid) {
            println(row.joinToString(""))
        }
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day04/Day_test")
    //part1(testInput)
    part2(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day04/Day")
    //part1(input)
    //part2(input)
}
