package hu.robnn.adventofcode21.day3

import hu.robnn.adventofcode21.Reader
import java.util.regex.Pattern


fun main() {
    val three = Three()
    val (gamma, epsilon) = three.calcGammaAndEpsilon()
    val (oxygen, component2) = three.calcOxygenAndCo2()
    println(gamma * epsilon)
    println(oxygen * component2)
}


class Three {
    fun calcGammaAndEpsilon(): Pair<Int, Int> {
        val reader = Reader()
        val bits = reader.readLines("day3_input1.txt").map { it.chunked(1) }
        // transpose the list
        val transposed = transposeList(bits)
        val gammaChars = transposed.map {
            getCharToKeep(it = it)
        }
        val epsilon = gammaChars.joinToString("") { if (it == "1") "0" else "1" } // epsilon is the inverse of gamma
        val gamma = gammaChars.joinToString("")

        return gamma.toInt(2) to epsilon.toInt(2)
    }

    fun calcOxygenAndCo2(): Pair<Int, Int> {
        val reader = Reader()
        val bits = reader.readLines("day3_input1.txt").map { it.chunked(1) }
        // transpose the list
        val transposed = transposeList(bits)
        val oxygen = recursiveRemove(0, transposed, "1", true).joinToString("").toInt(2)
        val co2 = recursiveRemove(0, transposed, "0", false).joinToString("").toInt(2)

        return oxygen to co2
    }

    private fun recursiveRemove(idx: Int, transposed: List<String>, ifEquals: String, mostCommon: Boolean): List<String> {
        val currentString = transposed[idx]
        val charToKeep = getCharToKeep(mostCommon, currentString, ifEquals)
        val charToDrop = if (charToKeep == "1") "0" else "1"
        val actualElements = Regex(Pattern.quote(charToDrop)).findAll(currentString).map { idx2 -> idx2.range.first }
            .toCollection(mutableListOf())
        val map = transposed.map {
            it.withIndex().filter { indexed -> !actualElements.contains(indexed.index) }.map { indexed -> indexed.value }.joinToString("")
        }
        val nextStringIdx = idx + 1
        return if (map.size == nextStringIdx || map[0].length == 1) {
            map
        } else {
            recursiveRemove(nextStringIdx, map, ifEquals, mostCommon)
        }
    }

    private fun getCharToKeep(mostCommon : Boolean = true, it: String, ifEquals: String = "1") =
        if (it.count { char -> char == '1' } > it.count { char -> char == '0' } && mostCommon ||
            it.count { char -> char == '1' } < it.count { char -> char == '0' } && !mostCommon)
            "1"
        else if (it.count { char -> char == '1' } == it.count { char -> char == '0' })
            ifEquals
        else
            "0"

    private fun transposeList(bits: List<List<String>>) = bits.reduce { a, b ->
        val concat = mutableListOf<String>()
        a.forEachIndexed { index, s ->
            concat.add(s + b[index])
        }
        concat
    }

}