package hu.robnn.adventofcode21.day1

import hu.robnn.adventofcode21.Solver

fun main() {
    val concreteSolver = ConcreteSolver()
    concreteSolver.printSolved()
}

class ConcreteSolver: Solver {
    override fun solvePart1(): String {
        val depths = readInput().map { it.toInt() }
        return countIncrease(depths).toString()
    }

    override fun solvePart2(): String {
        val depths = readInput().map { it.toInt() }
        val windows = mutableListOf<Int>()
        for (i in depths.indices) {
            if (i < depths.size - 2)
                windows.add(depths[i] + depths[i + 1] + depths[i + 2])
            else if (i < depths.size - 1)
                windows.add(depths[i] + depths[i + 1])
            else if (i < depths.size)
                windows.add(depths[i])
        }
        return countIncrease(windows).toString()
    }

    override fun getInputName() = "day1_input1.txt"

    private fun countIncrease(depths: List<Int>): Int {
        var counter = 0
        depths.forEachIndexed { index, it ->
            if (index != 0) {
                if (depths[index - 1] < it) {
                    counter++
                }
            }
        }
        return counter
    }
}