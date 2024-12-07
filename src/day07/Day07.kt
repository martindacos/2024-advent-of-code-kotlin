package day07

import readInput

fun main() {

    fun evaluateExpression(expression: String): Long {
        val tokens = expression.split(" ")
        var result = tokens[0].toLong()

        var i = 1
        while (i < tokens.size) {
            val operator = tokens[i]
            val nextNumber = tokens[i + 1].toInt()

            result = when (operator) {
                "+" -> result + nextNumber
                "*" -> result * nextNumber
                else -> throw IllegalArgumentException("Unknown operator: $operator")
            }

            i += 2
        }

        return result
    }

    fun part1(lines: List<String>) {
        val operators = listOf('*', '+')
        var finalResult: Long = 0

        for (line in lines) {
            val parts = line.split(":")

            val testValue = parts[0].trim().toLong()
            val numbers = parts[1].trim()
                .split(" ")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }

            val expressions = mutableListOf<String>()

            fun helper(expression: String, remaining: List<Int>) {
                if (remaining.isEmpty()) {
                    expressions.add(expression)
                    return
                }

                for (operator in operators) {
                    val nextNumber = remaining.first()
                    val newExpression = "$expression $operator $nextNumber"
                    helper(newExpression, remaining.drop(1))
                }
            }

            val firstNumber = numbers.first()
            helper(firstNumber.toString(), numbers.drop(1))

            for (expr in expressions) {
                val result = evaluateExpression(expr)
                //println("$expr = $result")
                if (result == testValue) {
                    finalResult += result
                    break
                }
            }
        }

        println(finalResult)
    }

    fun part2(lines: List<String>) {
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day07/Day_test")
    part1(testInput)
    //part2(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day07/Day")
    part1(input)
    //part2(input)
}
