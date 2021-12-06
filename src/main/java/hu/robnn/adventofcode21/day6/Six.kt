package hu.robnn.adventofcode21.day6

import hu.robnn.adventofcode21.Reader


fun main() {
    val six = Six()
    println(six.simulateLanternFishSpawn(80))
    println(six.simulateLanternFishSpawn(256))

}


class Six {

    fun simulateLanternFishSpawn(days: Int): Long {
        val reader = Reader()
        val originalLanternFishDays = reader.readLines("day6_input1.txt").first().split(",").map { it.toInt() }
        val ageToFishCount = originalLanternFishDays.groupBy { it }.map { it.key to it.value.size.toLong() }.toMap().toMutableMap()
        val afterDays = recursiveLanternfishSpawner(0, days, ageToFishCount)

        return afterDays.map { it.value }.reduce(Long::plus)
    }

    private fun recursiveLanternfishSpawner(currentDay: Int, maxDay: Int, lanternFish: MutableMap<Int, Long>): MutableMap<Int, Long> {
        return if (currentDay == maxDay) {
            lanternFish
        } else {
            val mapped = mutableMapOf<Int, Long>()
            lanternFish.forEach { (k, v) ->
                if (k == 0) {
                    mapped[8] = v
                    mapped[6] = (mapped[6] ?: 0) + v
                } else {
                    mapped[k - 1] = (mapped[k - 1] ?: 0) + v
                }
            }
            recursiveLanternfishSpawner(currentDay + 1, maxDay, mapped)
        }
    }
}
