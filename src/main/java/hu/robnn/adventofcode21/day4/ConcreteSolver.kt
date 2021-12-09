package hu.robnn.adventofcode21.day4

import hu.robnn.adventofcode21.Solver
import hu.robnn.adventofcode21.transpose


fun main() {
    val concreteSolver = ConcreteSolver()
    concreteSolver.printSolved()
}


class ConcreteSolver: Solver {
    class BoardNumber(val num: Int,
                      var marked: Boolean)

    class Board(private val numbersInRows: List<List<BoardNumber>>,
                private val numbersInColumns: List<List<BoardNumber>>) {
        fun numberPassed(num: Int): Boolean {
            var marked = false
            numbersInRows.forEach { it.forEach { if (it.num == num) { it.marked = true; marked = true } } }
            numbersInColumns.forEach { it.forEach { if (it.num == num) { it.marked = true; marked = true } } }
            return marked
        }

        fun isWinnerBoard(): Boolean =
            numbersInRows.any { it.all { it.marked }} || numbersInColumns.any { it.all { it.marked }}

        fun getUnmarkedNums(): List<Int> =
                numbersInColumns.map { it.filter { !it.marked }.map { it.num } }.flatten()
    }

    override fun solvePart1(): String {
        val (firstWinnerNum, firstUnmarked) = calcBingo(true)
        return (firstWinnerNum * firstUnmarked.reduce {a, b -> a+b }).toString()
    }

    override fun solvePart2(): String {
        val (lastWinnerNum, lastUnmarked) = calcBingo(false)
        return (lastWinnerNum * lastUnmarked.reduce {a, b -> a+b }).toString()
    }

    override fun getInputName() = "day4_input1.txt"

    private fun calcBingo(firstWinningBoard: Boolean): Pair<Int, List<Int>> {
        val lines = readInput()
        val numbers = lines[0].split(",").map { it.toInt() }

        val matrices = lines.drop(2).joinToString().split(", ,").map { it.split(",")
                .map { it.split(" ").filter { it.isNotBlank() } } }
        val boards = matrices.map {
            val normal = it.map { row -> row.map { num -> BoardNumber(num.toInt(), false) } }
            val transpose = transpose(it).map { row -> row.map { num -> BoardNumber(num.toInt(), false) } }
            Board(normal, transpose)
        }

        val winningBoards = mutableListOf<Pair<Int, List<Int>>>()
        numbers.forEach { num ->
            boards.forEach { board ->
                if (!board.isWinnerBoard()) {
                    val markedAny = board.numberPassed(num)
                    if (markedAny) {
                        if (board.isWinnerBoard()) {
                            winningBoards.add(num to board.getUnmarkedNums())
                        }
                    }
                }
            }
        }
        return if (firstWinningBoard) {
            winningBoards.first()
        } else {
            winningBoards.last()
        }
    }
}