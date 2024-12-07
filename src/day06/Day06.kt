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
        return position.first in matrix.indices && position.second in matrix.indices
    }

    fun notPresent(list: List<Pair<Int, Int>>, position: Pair<Int, Int>): Boolean {
        return !list.contains(position)
    }

    fun solve(matrix: Array<Array<Char>>): Pair<Pair<Int, Int>, MutableList<Pair<Int, Int>>> {
        // Element to find
        var direction = '^'
        var position = Pair(0, 0)
        var initialPosition = Pair(0, 0)
        val positions = mutableListOf<Pair<Int, Int>>()

        for (rowIndex in matrix.indices) {
            for (colIndex in matrix[rowIndex].indices) {
                if (matrix[rowIndex][colIndex] == direction) {
                    position = rowIndex to colIndex
                    initialPosition = position
                    positions.add(rowIndex to colIndex)
                }
            }
        }

        //println(matrix[position.first][position.second])

        //println(initialPosition)
        //position = nextMove(direction, position)
        //println(nextMove)

        var nextMove = position
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
        return Pair(initialPosition, positions)
    }

    fun part1(lines: List<String>): List<Pair<Int, Int>> {
        val matrix: Array<Array<Char>> = lines
            .map { it.toCharArray().toTypedArray() }
            .toTypedArray()

        val pair = solve(matrix)
        var initialPosition = pair.first
        val positions = pair.second

        return positions - initialPosition
    }

    fun solve2(
        matrix: Array<Array<Char>>,
        changedPosition: Pair<Int, Int>
    ): Int {
        // Element to find
        var direction = '^'
        var position = Pair(0, 0)
        var initialPosition = Pair(0, 0)
        val positions = mutableListOf<Pair<Int, Int>>()

        for (rowIndex in matrix.indices) {
            for (colIndex in matrix[rowIndex].indices) {
                if (matrix[rowIndex][colIndex] == direction) {
                    position = rowIndex to colIndex
                    initialPosition = position
                    positions.add(rowIndex to colIndex)
                }
            }
        }

        //println(matrix[position.first][position.second])

        //println(initialPosition)
        //position = nextMove(direction, position)
        //println(nextMove)

        var nextMove = position
        var loop = 0
        val printMatrix: Array<Array<Char>> = matrix.map { it.copyOf() }.toTypedArray()
        val obstacleList = mutableListOf<Pair<Pair<Int, Int>, Char>>()

        while (isValid(matrix, nextMove)) {
            //println("Trying nextMove ... $nextMove")
            val nextChar = matrix[nextMove.first][nextMove.second]

            if (nextMove == changedPosition) loop++
            if (loop == 2) {
                //println("Position making a loop $changedPosition")
                break
            }

            if (obstacleList.contains(Pair(nextMove, direction))) {
                loop = 2
                break
            }

            if (nextChar == '#') {
                //println("Turning right")
                obstacleList.add(Pair(nextMove, direction))
                direction = turnRight(direction)
            } else {
                position = nextMove
                printMatrix[position.first][position.second] = '-'
                if (notPresent(positions, position)) positions.add(position)
            }

            nextMove = nextMove(direction, position)
        }

        //println(positions.size)
        return if (loop == 2) 1 else 0
    }

    fun part2(lines: List<String>) {
        val matrix: Array<Array<Char>> = lines
            .map { it.toCharArray().toTypedArray() }
            .toTypedArray()

        val allPositions = part1(lines)
        var totalCount = 0

        for (position in allPositions) {
            //println("Changing position $position")

            val copiedMatrix: Array<Array<Char>> = matrix.map { it.copyOf() }.toTypedArray()
            copiedMatrix[position.first][position.second] = '#'

            //println("Checking position $position")
            totalCount += solve2(copiedMatrix, position)
        }

        println(totalCount)
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day06/Day_test")
    //part1(testInput)
    //part2(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day06/Day")
    //part1(input)
    part2(input)
}
