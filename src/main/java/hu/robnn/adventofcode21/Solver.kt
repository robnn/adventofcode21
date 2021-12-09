package hu.robnn.adventofcode21

interface Solver {
    fun readInput(): List<String> = Reader().readLines(getInputName())
    fun solvePart1(): String
    fun solvePart2(): String
    fun printSolved() {
        println(solvePart1())
        println(solvePart2())
    }
    fun getInputName(): String
}