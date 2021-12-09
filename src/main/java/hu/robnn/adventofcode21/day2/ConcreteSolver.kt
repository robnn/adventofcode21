package hu.robnn.adventofcode21.day2

import hu.robnn.adventofcode21.Solver


fun main() {
    val concreteSolver = ConcreteSolver()
    concreteSolver.printSolved()
}

class ConcreteSolver: Solver {
    override fun solvePart1(): String {
        val movements = readInput().map { it.split(" ") }.map { it[0] to it[1].toInt() }
        var depth = 0
        var pos = 0
        movements.forEach { (direction, distance) ->
            when(direction) {
                "forward" -> pos += distance
                "down" -> depth += distance
                "up" -> depth -= distance
             }
        }
        return (depth * pos).toString()
    }

    override fun solvePart2(): String {
        val movements = readInput().map { it.split(" ") }.map { it[0] to it[1].toInt() }
        var aim = 0
        var depth = 0
        var pos = 0
        movements.forEach { (direction, value) ->
            when(direction) {
                "forward" -> { pos += value; depth += aim * value }
                "down" -> aim += value
                "up" -> aim -= value
            }
        }
        return (depth * pos).toString()
    }

    override fun getInputName() = "day2_input1.txt"
}