package hu.robnn.adventofcode21.day10

import hu.robnn.adventofcode21.Solver
import java.util.*

fun main() {
    val concreteSolver = ConcreteSolver()
    concreteSolver.printSolved()
}

class ConcreteSolver: Solver {

    private val openToCloseTokens = mapOf(
            "{" to "}",
            "<" to ">",
            "(" to ")",
            "[" to "]"
    )

    override fun solvePart1(): String {
        val score = mapOf(
                ")" to 3L,
                "]" to 57L,
                "}" to 1197L,
                ">" to 25137L
        )
        val input = readInput().map { it.chunked(1) }
        val lineToIllegalChar = getLinesToIllegalChars(input)
        return lineToIllegalChar.values.map { score[it]!! }.reduce { a, b -> a + b}.toString()
    }

    override fun solvePart2(): String {
        val score = mapOf(
                ")" to 1L,
                "]" to 2L,
                "}" to 3L,
                ">" to 4L
        )
        val input = readInput()
        val lineToIllegalChar = getLinesToIllegalChars(input.map { it.chunked(1) })
        val onlyIncompleteLines = input.minus(lineToIllegalChar.keys).map { it.chunked(1) }
        val lineToAutocompleteScore = mutableMapOf<String, Long>()
        onlyIncompleteLines.forEach { row ->
            val stack = Stack<String>()
            val toBeComplete = mutableListOf<String>()
            row.forEach inside@{
                if (openToCloseTokens.keys.contains(it)) {
                    stack.push(it)
                } else {
                    stack.pop()
                }
            }
            while (!stack.empty()) {
                toBeComplete.add(openToCloseTokens[stack.pop()]!!)
            }

            var totalScore = 0L
            toBeComplete.forEach {
                totalScore *= 5
                totalScore += score[it]!!
            }
            lineToAutocompleteScore[row.joinToString("")] = totalScore
        }

        return lineToAutocompleteScore.values.sorted()[(lineToAutocompleteScore.values.size) / 2].toString()
    }

    override fun getInputName() = "day10_input1.txt"

    private fun getLinesToIllegalChars(
            input: List<List<String>>): MutableMap<String, String> {
        val lineToIllegalChar = mutableMapOf<String, String>()
        input.forEach { row ->
            val stack = Stack<String>()
            row.forEach inside@{
                if (openToCloseTokens.keys.contains(it)) {
                    stack.push(it)
                } else {
                    val popped = stack.pop()
                    if (it != openToCloseTokens[popped]) {
                        lineToIllegalChar[row.joinToString("")] = it
                        return@inside
                    }
                }
            }
        }
        return lineToIllegalChar
    }
}
