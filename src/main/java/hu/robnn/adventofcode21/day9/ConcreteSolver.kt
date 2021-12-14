package hu.robnn.adventofcode21.day9

import hu.robnn.adventofcode21.Solver

fun main() {
    val concreteSolver = ConcreteSolver()
    concreteSolver.printSolved()
}

class ConcreteSolver: Solver {

    override fun solvePart1(): String {
        val input = readInput()
        val matrix = input.map { it.chunked(1).map { it.toInt() } }
        val lowPoints = getLowPoints(matrix)

        return lowPoints.map { it + 1 }.sum().toString()
    }

    /**
     * Mainly copied mbali's solution https://github.com/mbali/advent-of-code-2021/blob/main/src/Day09.kt
     */
    override fun solvePart2(): String {
        val input = readInput()
        val matrix = input.map { it.chunked(1).map { it.toInt() } }
        val basinMap = MutableList(matrix.size) { row ->
            MutableList(matrix[row].size) { -1 }
        }
        var basinIndex = 0
        do {
            val seed = matrix.indices.firstNotNullOfOrNull { row ->
                matrix[row].indices.firstNotNullOfOrNull { col ->
                    if (matrix[row][col] < 9 && basinMap[row][col] < 0) row to col
                    else null
                }
            }
            seed?.let { calcBasinSize(it.first, it.second, matrix, basinMap, basinIndex++) }
        } while (seed != null)
        return basinMap.flatMap { it.toList() }
                .filter { it > 0 }
                .groupingBy { it }
                .eachCount().values
                .sortedDescending()
                .take(3).reduce { a, b -> a * b }.toString()
    }

    override fun getInputName() = "day9_input1.txt"

    private fun getLowPoints(
            matrix: List<List<Int>>): MutableList<Int> {
        val lowPoints = mutableListOf<Int>()
        matrix.forEachIndexed { index1, row ->
            row.forEachIndexed { index2, num ->
                if (index1 == 0) {
                    if (index2 == 0) {
                        //left upper corner
                        if (num < matrix[index1][index2 + 1] && num < matrix[index1 + 1][index2]) {
                            lowPoints.add(num)
                        }
                    } else if (index2 == row.size - 1) {
                        //right upper corner
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1 + 1][index2]) {
                            lowPoints.add(num)
                        }
                    } else {
                        //first row
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1][index2 + 1] && num <
                                matrix[index1 + 1][index2]) {
                            lowPoints.add(num)
                        }
                    }
                } else if (index1 == matrix.size - 1) {
                    if (index2 == 0) {
                        //left lower corner
                        if (num < matrix[index1][index2 + 1] && num < matrix[index1][index2 - 1]) {
                            lowPoints.add(num)
                        }
                    } else if (index2 == row.size - 1) {
                        //right lower corner
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1][index2 - 1]) {
                            lowPoints.add(num)
                        }
                    } else {
                        //last row
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1][index2 + 1] && num <
                                matrix[index1 - 1][index2]) {
                            lowPoints.add(num)
                        }
                    }
                } else {
                    if (index2 == 0) {
                        //left edge
                        if (num < matrix[index1][index2 + 1] && num < matrix[index1 - 1][index2]
                                && num < matrix[index1 + 1][index2]) {
                            lowPoints.add(num)
                        }
                    } else if (index2 == row.size - 1) {
                        //right edge
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1 + 1][index2]
                                && num < matrix[index1 - 1][index2]) {
                            lowPoints.add(num)
                        }
                    } else {
                        //last row
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1][index2 + 1] &&
                                num < matrix[index1 - 1][index2] && num < matrix[index1 + 1][index2]) {
                            lowPoints.add(num)
                        }
                    }
                }
            }
        }
        return lowPoints
    }

    private val neigboursDirections = listOf(
            0 to 1,
            0 to -1,
            1 to 0,
            -1 to 0
    )

    private fun calcBasinSize(index1: Int, index2: Int, matrix: List<List<Int>>, basinMarks:
    MutableList<MutableList<Int>>, basinIndex: Int) {
        if (matrix[index1][index2] == 9 || basinMarks[index1][index2] >= 0) return
        basinMarks[index1][index2] = basinIndex
        neigboursDirections.map { it.first + index1 to it.second + index2 }
                .filter { it.first in matrix.indices && it.second in matrix[index1].indices }
                .forEach {
                    calcBasinSize(it.first, it.second, matrix, basinMarks, basinIndex)
                }
    }

}
