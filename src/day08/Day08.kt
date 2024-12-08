package day08

import readInput

fun main() {

    fun isValid(matrix: Array<Array<Char>>, position: Pair<Int, Int>): Boolean {
        return position.first in matrix.indices && position.second in matrix.indices
    }

    fun part1(lines: List<String>) {
        val matrix: Array<Array<Char>> = lines
            .map { it.toCharArray().toTypedArray() }
            .toTypedArray()

        val charPositions = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val char = matrix[i][j]
                if (char != '.') charPositions.computeIfAbsent(char) { mutableListOf() }.add(Pair(i, j))
            }
        }

        val charsAppearingTwice = charPositions
            .filterValues { it.size >= 2 }
        val antinodes = mutableSetOf<Pair<Int, Int>>()

        charsAppearingTwice.forEach { char,list ->
            for (i in list.indices) {
                val pair = list[i]
                for (j in i + 1 until list.size) {
                    val nextPair = list[j]
                    //println("$char -> $nextPair - $pair")

                    //Difference
                    val difference = Pair(nextPair.first - pair.first, nextPair.second - pair.second)

                    val nextPairAntinode = Pair(nextPair.first + difference.first, nextPair.second + difference.second)
                    val pairAntinode = Pair(pair.first - difference.first, pair.second - difference.second)

                    if (isValid(matrix, nextPairAntinode)) antinodes.add(nextPairAntinode)
                    if (isValid(matrix, pairAntinode)) antinodes.add(pairAntinode)
                }
            }
        }

        println(antinodes.size)
    }

    fun part2(lines: List<String>) {
        val matrix: Array<Array<Char>> = lines
            .map { it.toCharArray().toTypedArray() }
            .toTypedArray()

        val charPositions = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val char = matrix[i][j]
                if (char != '.') charPositions.computeIfAbsent(char) { mutableListOf() }.add(Pair(i, j))
            }
        }

        val printMatrix: Array<Array<Char>> = matrix.map { it.copyOf() }.toTypedArray()
        val charsAppearingTwice = charPositions
            .filterValues { it.size >= 2 }
        val antinodes = mutableSetOf<Pair<Int, Int>>()

        charsAppearingTwice.forEach { char,list ->
            for (i in list.indices) {
                val pair = list[i]
                antinodes.add(pair)
                for (j in i + 1 until list.size) {
                    val nextPair = list[j]
                    //println("$char -> $nextPair - $pair")

                    //Difference
                    val difference = Pair(nextPair.first - pair.first, nextPair.second - pair.second)

                    var nextPairAntinode = Pair(nextPair.first + difference.first, nextPair.second + difference.second)
                    while (isValid(matrix, nextPairAntinode)) {
                        antinodes.add(nextPairAntinode)
                        printMatrix[nextPairAntinode.first][nextPairAntinode.second] = '#'
                        nextPairAntinode = Pair(nextPairAntinode.first + difference.first, nextPairAntinode.second + difference.second)
                    }

                    var pairAntinode = Pair(pair.first - difference.first, pair.second - difference.second)
                    while (isValid(matrix, pairAntinode)) {
                        antinodes.add(pairAntinode)
                        printMatrix[pairAntinode.first][pairAntinode.second] = '#'
                        pairAntinode = Pair(pairAntinode.first - difference.first, pairAntinode.second - difference.second)
                    }
                }
            }
        }

        println(antinodes.size)
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day08/Day_test")
    part1(testInput)
    part2(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day08/Day")
    part1(input)
    part2(input)
}
