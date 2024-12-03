package day03

import readInput

fun main() {
    fun part1(input: List<String>): Unit {
        // Regex to match "mul(number,number)"
        val regex = Regex("""mul\((\d+),(\d+)\)""")

        var total = 0
        input.forEach { line ->
            val result = regex.findAll(line).sumOf { match ->
                val (num1, num2) = match.destructured
                num1.toInt() * num2.toInt()
            }
            total += result
        }

        println(total)
    }

    fun part2(input: List<String>): Unit {
        // Regex to match "mul(number,number)"
        val mulRegex = Regex("""mul\((\d+),(\d+)\)""")
        val controlRegex = Regex("""do\(\)|don't\(\)""")

        var total = 0
        var mulEnabled = true

        input.forEach { line ->
            val matches = Regex("""mul\((\d+),(\d+)\)|do\(\)|don't\(\)""").findAll(line)

            for (match in matches) {
                //println(match.value)
                val mulMatch = mulRegex.matchEntire(match.value)
                val controlMatch = controlRegex.matchEntire(match.value)

                if (mulMatch != null && mulEnabled) {
                    // Extract numbers and calculate the product
                    val (num1, num2) = mulMatch.destructured
                    total += num1.toInt() * num2.toInt()
                } else if (controlMatch != null) {
                    // Update `mulEnabled` based on control instructions
                    val control = controlMatch.value
                    if (control == "do()")
                        mulEnabled = true
                    else if (control == "don't()")
                        mulEnabled = false
                }
            }
        }

        println(total)
    }

    // Or read a large test input from the `src/Day_test.txt` file:
    val testInput = readInput("day03/Day_test")
    part1(testInput)
    part2(testInput)

    // Read the input from the `src/Day.txt` file.
    val input = readInput("day03/Day")
    part1(input)
    part2(input)
}
