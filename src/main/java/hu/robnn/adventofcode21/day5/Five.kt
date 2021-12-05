package hu.robnn.adventofcode21.day5

import hu.robnn.adventofcode21.Reader


fun main() {
    val five = Five()
    println(five.calcOverlappingLinePoints())
    println(five.calcOverlappingLinePoints(true))
}


class Five {

    fun calcOverlappingLinePoints(getDiagonals: Boolean = false): Int {
        val reader = Reader()
        val overlappingCoordinates = reader.readLines("day5_input1.txt").map { it.split(" -> ")
                .map { it.split(",") }}.map { getLineElements(it, getDiagonals) }
                .filter { it.isNotEmpty() }.flatten().groupBy { it }.filter { it.value.size >= 2 }
        return overlappingCoordinates.size
    }

    private fun getLineElements(lineCoordinates: List<List<String>>, getDiagonals: Boolean = false): List<Pair<Int, Int>> {
        val x1 = lineCoordinates[0][0].toInt()
        val x2 = lineCoordinates[1][0].toInt()
        val y1 = lineCoordinates[0][1].toInt()
        val y2 = lineCoordinates[1][1].toInt()
        if (x1 != x2 && y1 != y2 && !getDiagonals)
            return mutableListOf()
        val xRange = if (x1 < x2) (x1..x2).toList() else if (x1 > x2) (x1 downTo x2).toList() else mutableListOf(x1)
        val yRange = if (y1 < y2) (y1..y2).toList() else if (y1 > y2) (y1 downTo y2).toList() else mutableListOf(y1)
        return if (x1 == x2 || y1 == y2) xRange.map { x -> yRange.map { y -> Pair(x, y) } }.flatten() else
            xRange.mapIndexed { index, x -> Pair(x, yRange[index]) }
    }
}