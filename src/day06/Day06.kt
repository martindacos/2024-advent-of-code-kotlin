package day06

import readInput

fun main() {

    fun nextMove(direction: Char, position: Pair<Int, Int>): Pair<Int, Int> {
        return when (direction) {
            '^' -> Pair(position.first - 1, position.second)
            '>' -> Pair(position.first, position.second + 1)
            'v' -> Pair(position.first + 1, position.second)
            '<' -> Pair(position.first, position.second - 1)
            else -> {
                print("We did something wrong")
                Pair(-1, -1)
            }
        }
    }

    fun turnRight(direction: Char): Char {
        return when (direction) {
            '^' -> '>'
            '>' -> 'v'
            'v' -> '<'
            '<' -> '^'
            else -> {
                print("We did something wrong")
                '*'
            }
        }
    }

    fun isValid(matrix: Array<Array<Char>>, position: Pair<Int, Int>): Boolean {
        return position.first in matrix.indices && position.second in matrix[position.second].indices
    }

    fun notPresent(list: List<Pair<Int, Int>>, position: Pair<Int, Int>): Boolean {
        return !list.contains(position)
    }

    fun part1(lines: List<String>) {
        val matrix: Array<Array<Char>> = lines
            .map { it.toCharArray().toTypedArray() }
            .toTypedArray()

        // Element to find
        var direction = '^'
        var position = Pair(0, 0)
        val positions = mutableListOf<Pair<Int, Int>>()

        for (rowIndex in matrix.indices) {
            for (colIndex in matrix[rowIndex].indices) {
                if (matrix[rowIndex][colIndex] == direction) {
                    position = rowIndex to colIndex
                    positions.add(rowIndex to colIndex)
                }
            }
        }

        //println(matrix[position.first][position.second])

        var nextMove = nextMove(direction, position)
        //println(nextMove)

        while (isValid(matrix, nextMove)) {
            val nextChar = matrix[nextMove.first][nextMove.second]
            if (nextChar == '#') {
                //println("Turning right")
                direction = turnRight(direction)
            } else {
                position = nextMove
                if (notPresent(positions, position)) positions.add(position)
            }

            nextMove = nextMove(direction, position)
        }

        println(positions.size)
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day06/Day_test")
    part1(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day06/Day")
    part1(input)
}
