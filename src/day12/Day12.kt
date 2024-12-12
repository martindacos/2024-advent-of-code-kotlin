package day12

import readInput

fun main() {

    fun calculateVerticalPerimeter(positions: Set<Pair<Int, Int>>): Int {
        val perimeter = mutableListOf<Pair<Int, Int>>()

        // Define all possible directions (up, down, left, right, and diagonals)
        val directions = listOf(
            Pair(0, -1), // Left
            Pair(0, 1)  // Right
        )

        for (position in positions) {
            for (direction in directions) {
                val newRow = position.first + direction.first
                val newCol = position.second + direction.second

                if (!positions.contains(Pair(newRow, newCol))) {
                    perimeter.add(Pair(newRow, newCol))
                }
            }
        }

        val sorted = perimeter.sortedWith(compareBy({ it.second }, { it.first }))

        var count = 0

        for (i in sorted.indices) {
            if (!perimeter.contains(Pair(sorted[i].first + 1, sorted[i].second))) {
                count++
            }
//            } else if (i + 1 in sorted.indices) {
//                val next = Pair(sorted[i + 1].first, sorted[i + 1].second)
//                if (next == sorted[i]) count++
//            }
        }

        // Count occurrences and filter repeated pairs
        val repeatedPairs = sorted.groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .keys

        for (repeatedPair in repeatedPairs) {
            if (!repeatedPairs.contains(Pair(repeatedPair.first + 1, repeatedPair.second))) {
                count++
            }
        }

        return count
    }

    fun calculateHorizontalPerimeter(positions: Set<Pair<Int, Int>>): Int {
        val perimeter = mutableListOf<Pair<Int, Int>>()

        // Define all possible directions (up, down, left, right, and diagonals)
        val directions = listOf(
            Pair(-1, 0), // Up
            Pair(1, 0),  // Down
        )

        for (position in positions) {
            for (direction in directions) {
                val newRow = position.first + direction.first
                val newCol = position.second + direction.second

                if (!positions.contains(Pair(newRow, newCol))) {
                    perimeter.add(Pair(newRow, newCol))
                }
            }
        }

        val sorted = perimeter.sortedWith(compareBy({ it.first }, { it.second }))

        var count = 0
        for (i in sorted.indices) {
            if (!perimeter.contains(Pair(sorted[i].first, sorted[i].second + 1))) {
                count++
            }
//            } else if (i + 1 in sorted.indices) {
//                val next = Pair(sorted[i + 1].first, sorted[i + 1].second)
//                if (next == sorted[i]) count++
//            }
        }

        // Count occurrences and filter repeated pairs
//        val repeatedPairs = sorted.groupingBy { it }
//            .eachCount()
//            .filter { it.value > 1 }
//            .keys
//
//        for (repeatedPair in repeatedPairs) {
//            if (!repeatedPairs.contains(Pair(repeatedPair.first, repeatedPair.second + 1))) {
//                count++
//            }
//        }

        return count
    }

    fun exploreNeighbors(
        matrix: Array<Array<Char>>,
        position: Pair<Int, Int>
    ): List<Pair<Int, Int>> {
        val target = matrix[position.first][position.second]
        val adjacentValues = mutableListOf<Pair<Int, Int>>()

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
                adjacentValues.add(Pair(newRow, newCol))
            }
        }

        return adjacentValues
    }

    fun part1(lines: List<String>) {
        val matrix: Array<Array<Char>> = lines
            .map { it.toCharArray().toTypedArray() }
            .toTypedArray()

        // Getting all possible positions as a list of pairs
        val positions = matrix.indices.flatMap { row ->
            matrix[row].indices.map { col -> row to col }
        }

        var price = 0
        val exploredPositions = mutableSetOf<Pair<Int, Int>>()
        for (position in positions) {
            if (position in exploredPositions) {
                //println("Position already explored $position")
            } else {
                //println("Exploring $position with value ${matrix[position.first][position.second]}")
                exploredPositions.add(position)
                var keepExploring = true
                var toExplore = setOf(position)
                val localExploredPositions = mutableSetOf(position)

                while (keepExploring) {
                    //println("Remaining to explore $toExplore")
                    val newAdjacent = mutableListOf<Pair<Int, Int>>()
                    toExplore.forEach { p ->
                        run {
                            newAdjacent.addAll(exploreNeighbors(matrix, p))
                        }
                    }

                    if (newAdjacent.isNotEmpty() && !exploredPositions.containsAll(newAdjacent)) {
                        toExplore = newAdjacent.filter { !exploredPositions.contains(it) }.toSet()
                        exploredPositions.addAll(toExplore)
                        localExploredPositions.addAll(toExplore)
                    } else {
                        keepExploring = false
                        val localArea = localExploredPositions.count()
                        //val localPerimeter = calculateVerticalPerimeter(localExploredPositions).count()
                        val hPerimeter = calculateHorizontalPerimeter(localExploredPositions)
                        val vPerimeter = calculateVerticalPerimeter(localExploredPositions)
                        val localPrice = localArea * (hPerimeter + vPerimeter)

                        println("${matrix[position.first][position.second]} -> $localArea * ${(hPerimeter + vPerimeter)} = $localPrice")

                        price += localPrice
                    }
                }
            }
        }

        println(price)
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day12/Day_test")
    //part1(testInput)
    //part1(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day12/Day")
    //part1(input)
    part1(input)
}
