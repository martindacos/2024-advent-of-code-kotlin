import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

fun readMatrix(name: String) = readInput(name).map { it.toCharArray().toTypedArray() }
    .toTypedArray()

fun printMatrix(matrix: Array<Array<Char>>) {
    matrix.forEach { println(it.joinToString("")) }
    println()
}

fun findFirstCharInMatrix(matrix: Array<Array<Char>>, char: Char): Pair<Int, Int> {
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            if (matrix[i][j] == char) {
                return i to j
            }
        }
    }
    return Pair(-1, -1)
}

fun readAndSplitBySpaces(name: String): List<Long> {
    val file = Path("src/$name.txt").toFile()
    val numbers = mutableListOf<Long>()

    file.forEachLine { line ->
        val elements = line.split("\\s+".toRegex()).map { it.toLong() }
        numbers.addAll(elements)
    }

    return numbers
}

fun processFile(name: String): Pair<List<Int>, List<Int>> {
    val file = Path("src/$name.txt").toFile()
    val beforeSpace = mutableListOf<Int>()
    val afterSpace = mutableListOf<Int>()

    file.forEachLine { line ->
        val parts = line.split("\\s+".toRegex()) // Split on one or more whitespace characters
        if (parts.size == 2) { // Ensure the line has exactly two parts
            beforeSpace.add(parts[0].toIntOrNull() ?: 0) // Convert and handle invalid numbers
            afterSpace.add(parts[1].toIntOrNull() ?: 0)
        }
    }

    return Pair(beforeSpace, afterSpace)
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
