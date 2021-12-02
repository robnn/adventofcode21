package hu.robnn.adventofcode21.day2

import hu.robnn.adventofcode21.Reader


fun main() {
    val two = Two()
    val calcDepthAndPos = two.calcDepthAndPos()
    val calcDepthAndPosPart2 = two.calcDepthAndPosPart2()
    println(calcDepthAndPos.first * calcDepthAndPos.second)
    println(calcDepthAndPosPart2.first * calcDepthAndPosPart2.second)
}


class Two {
    fun calcDepthAndPos(): Pair<Int, Int> {
        val reader = Reader()
        val movements = reader.readLines("day2_input1.txt").map { it.split(" ") }.map { it[0] to it[1].toInt() }
        var depth = 0
        var pos = 0
        movements.forEach { (direction, distance) ->
            when(direction) {
                "forward" -> pos += distance
                "down" -> depth += distance
                "up" -> depth -= distance
             }
        }
        return depth to pos
    }

    fun calcDepthAndPosPart2(): Pair<Int, Int> {
        val reader = Reader()
        val movements = reader.readLines("day2_input1.txt").map { it.split(" ") }.map { it[0] to it[1].toInt() }
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
        return depth to pos
    }
}