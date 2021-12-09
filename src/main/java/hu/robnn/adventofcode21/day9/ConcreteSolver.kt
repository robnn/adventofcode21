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
        val lowPoints = getLowPointsWithBasins(matrix)

        return lowPoints.map { it.first + 1 }.sum().toString()
    }


    override fun solvePart2(): String {
        val input = readInput()
        val matrix = input.map { it.chunked(1).map { it.toInt() } }
        val lowPoints = getLowPointsWithBasins(matrix)

        return lowPoints.map { it.second }.sorted().reversed().chunked(3)[0].reduce { a, b -> a * b }.toString()
    }

    override fun getInputName() = "day9_input1.txt"

    private fun getLowPointsWithBasins(
            matrix: List<List<Int>>): MutableList<Pair<Int, Int>> {
        val lowPoints = mutableListOf<Pair<Int, Int>>()
        matrix.forEachIndexed { index1, row ->
            row.forEachIndexed { index2, num ->
                if (index1 == 0) {
                    if (index2 == 0) {
                        //left upper corner
                        if (num < matrix[index1][index2 + 1] && num < matrix[index1 + 1][index2]) {
                            lowPoints.add(num to calcBasinSize(true, false, true, false, index1, index2, matrix, index1, index2))
                        }
                    } else if (index2 == row.size - 1) {
                        //right upper corner
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1 + 1][index2]) {
                            lowPoints.add(num to calcBasinSize(true, false, false, true, index1, index2, matrix, index1, index2))
                        }
                    } else {
                        //first row
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1][index2 + 1] && num <
                                matrix[index1 + 1][index2]) {
                            lowPoints.add(num to calcBasinSize(true, false, true, true, index1, index2, matrix, index1, index2))
                        }
                    }
                } else if (index1 == matrix.size - 1) {
                    if (index2 == 0) {
                        //left lower corner
                        if (num < matrix[index1][index2 + 1] && num < matrix[index1][index2 - 1]) {
                            lowPoints.add(num to calcBasinSize(false, true, true, false, index1, index2, matrix, index1, index2))
                        }
                    } else if (index2 == row.size - 1) {
                        //right lower corner
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1][index2 - 1]) {
                            lowPoints.add(num to calcBasinSize(false, true, false, true, index1, index2, matrix, index1, index2))
                        }
                    } else {
                        //last row
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1][index2 + 1] && num <
                                matrix[index1 - 1][index2]) {
                            lowPoints.add(num to calcBasinSize(false, true, true, true, index1, index2, matrix, index1, index2))
                        }
                    }
                } else {
                    if (index2 == 0) {
                        //left edge
                        if (num < matrix[index1][index2 + 1] && num < matrix[index1 - 1][index2]
                                && num < matrix[index1 + 1][index2]) {
                            lowPoints.add(num to calcBasinSize(true, true, true, false, index1, index2, matrix, index1, index2))
                        }
                    } else if (index2 == row.size - 1) {
                        //right edge
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1 + 1][index2]
                                && num < matrix[index1 - 1][index2]) {
                            lowPoints.add(num to calcBasinSize(true, true, false, true, index1, index2, matrix, index1, index2))
                        }
                    } else {
                        //last row
                        if (num < matrix[index1][index2 - 1] && num < matrix[index1][index2 + 1] &&
                                num < matrix[index1 - 1][index2] && num < matrix[index1 + 1][index2]) {
                            lowPoints.add(num to calcBasinSize(true, true, true, true, index1, index2,  matrix, index1, index2))
                        }
                    }
                }
            }
        }
        return lowPoints
    }

    private fun calcBasinSize(index1RaiseAllowed: Boolean, index1DecreaseAllowed: Boolean, index2RaiseAllowed: Boolean,
                              index2DecreaseAllowed: Boolean, index1: Int, index2: Int,
                              matrix: List<List<Int>>,
                              startIndex1: Int, startIndex2: Int,
                              prevIndex1: Int = -1, prevIndex2: Int = -1,
    startIndexCount: Int = 0): Int {
        var size = 0

        val startIndexCount2 =  if (startIndex1 == index1 && startIndex2 == index2) {
            startIndexCount + 1
        } else {
            startIndexCount
        }
        if (startIndexCount2 == 2) {
            return size
        }
        println("current num of mat [$index1][$index2]: ${matrix[index1][index2]}")
        if (matrix[index1][index2] == 9) {
           return size
        } else {
            size++
        }

        if (index1RaiseAllowed) {
            size += calcBasinSize(matrix.size > index1 + 2, !(prevIndex1 == index1 - 1 && prevIndex2 == index2),
                    index2RaiseAllowed,
                    index2DecreaseAllowed, index1 + 1, index2, matrix, startIndex1, startIndex2, index1, index2, startIndexCount2)
        }
        if (index1DecreaseAllowed) {
            size += calcBasinSize(!(prevIndex1 == index1 + 1 && prevIndex2 == index2), 0 <= index1 - 2,
                    index2RaiseAllowed,
                    index2DecreaseAllowed, index1 - 1, index2, matrix, startIndex1, startIndex2, index1, index2, startIndexCount2)
        }
        if (index2RaiseAllowed) {
            size += calcBasinSize(index1RaiseAllowed, index1DecreaseAllowed, matrix[index1].size > index2 + 2,
                    !(prevIndex1 == index1 && prevIndex2 == index2 - 1), index1, index2 + 1, matrix, startIndex1,
                    startIndex2, index1, index2, startIndexCount2)
        }
        if (index2DecreaseAllowed) {
            size += calcBasinSize(index1RaiseAllowed, index1DecreaseAllowed, !(prevIndex1 == index1 && prevIndex2 ==
                    index2 + 1),
                    0 <= index2 - 2, index1, index2 - 1, matrix, startIndex1, startIndex2, index1, index2, startIndexCount2)
        }
        println("basinSizeCurrently $size")
        return size
    }

}
