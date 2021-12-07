package hu.robnn.adventofcode21.day7

import hu.robnn.adventofcode21.Reader
import kotlin.math.absoluteValue


fun main() {
    val seven = Seven()
    println(seven.calcLeastExpensiveAlignment())
    println(seven.calcLeastExpensiveAlignment(true))
}


class Seven {

    fun calcLeastExpensiveAlignment(shouldCalcIncrementally: Boolean = false): Int {
        val reader = Reader()
        val originalPositions = reader.readLines("day7_input1.txt").first().split(",").map { it.toInt() }
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
