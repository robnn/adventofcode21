package hu.robnn.adventofcode21.day1

import hu.robnn.adventofcode21.Reader

fun main() {
    val one = One()
    println(one.calcIncrease())
    println(one.calcIncreaseWithSlidingWindow())
}

class One {
    fun calcIncrease(): Int {
        val reader = Reader()
        val depths = reader.readLines("day1_input1.txt").map { it.toInt() }
        return countIncrease(depths)
    }

    fun calcIncreaseWithSlidingWindow(): Int {
        val reader = Reader()
        val depths = reader.readLines("day1_input1.txt").map { it.toInt() }
        val windows = mutableListOf<Int>()
        for (i in depths.indices) {
            if (i < depths.size - 2)
                windows.add(depths[i] + depths[i + 1] + depths[i + 2])
            else if (i < depths.size - 1)
                windows.add(depths[i] + depths[i + 1])
            else if (i < depths.size)
                windows.add(depths[i])
        }
        return countIncrease(windows)
    }

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