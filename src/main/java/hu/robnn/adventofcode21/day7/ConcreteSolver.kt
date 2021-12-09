package hu.robnn.adventofcode21.day7

import hu.robnn.adventofcode21.Solver
import kotlin.math.absoluteValue

fun main() {
    val concreteSolver = ConcreteSolver()
    concreteSolver.printSolved()
}

class ConcreteSolver: Solver {

    override fun solvePart1(): String {
        return calcLeastExpensiveAlignment().toString()
    }

    override fun solvePart2(): String {
        return calcLeastExpensiveAlignment(true).toString()
    }

    override fun getInputName() = "day7_input1.txt"

    private fun calcLeastExpensiveAlignment(shouldCalcIncrementally: Boolean = false): Int {
        val originalPositions = readInput().first().split(",").map { it.toInt() }
        val neededFuelCalculations = mutableListOf<Int>()
        originalPositions.forEach { x ->
            val neededFuelOnX = originalPositions.map { y -> x - y }
                    .map {
                        if (shouldCalcIncrementally)
                            (0..it.absoluteValue).reduce { a, b -> a + b }
                        else
                            it.absoluteValue
                    }.reduce { a, b -> a + b }
            neededFuelCalculations.add(neededFuelOnX)
        }

        return neededFuelCalculations.minOf { it }
    }
}
