package hu.robnn.adventofcode21.day8

import hu.robnn.adventofcode21.Solver

fun main() {
    val concreteSolver = ConcreteSolver()
    concreteSolver.printSolved()
}

class ConcreteSolver: Solver {

    /**
         AAAA
        B    C
        B    C
         DDDD
        E    F
        E    F
         GGGG
     */
    enum class Segment {
        A, B, C, D, E, F, G
    }

    private val uniqueSizedNumbers = mutableListOf(1, 4, 7, 8)

    private val numbersToSegments = mapOf(
            0 to setOf(Segment.A, Segment.B, Segment.C, Segment.E, Segment.F, Segment.G),
            1 to setOf(Segment.C, Segment.F),
            2 to setOf(Segment.A, Segment.C, Segment.D, Segment.E,  Segment.G),
            3 to setOf(Segment.A, Segment.C, Segment.D, Segment.F, Segment.G),
            4 to setOf(Segment.B, Segment.C, Segment.D, Segment.F),
            5 to setOf(Segment.A, Segment.B, Segment.D, Segment.F, Segment.G),
            6 to setOf(Segment.A, Segment.B, Segment.D, Segment.E, Segment.F, Segment.G),
            7 to setOf(Segment.A, Segment.C, Segment.F),
            8 to setOf(Segment.A, Segment.B, Segment.C, Segment.D, Segment.E, Segment.F, Segment.G),
            9 to setOf(Segment.A, Segment.B, Segment.C, Segment.D, Segment.F, Segment.G),
    )

    override fun solvePart1(): String {
        val input = getInput().map { it.second }

        return input.map { it.filter { uniqueSizedNumbers.map { numbersToSegments[it]?.size }.toList().contains(it.length) }
                .size }.sum().toString()
    }

    override fun solvePart2(): String {
        val input = getInput()
        val uniqueNumbersSize = uniqueSizedNumbers.map { numbersToSegments[it]?.size }
        return input.map { row ->
            val uniqueLengths = row.first.filter { uniqueNumbersSize.contains(it.length) }.map {
                numbersToSegments.filter { (_, v) -> v.size == it.length }.map { it.key }.first() to it
            }.toMap()
            val stringToNumber = mutableMapOf<Set<String>, String>()
            val number1 = uniqueLengths[1]!!
            val number4 = uniqueLengths[4]!!
            val number7 = uniqueLengths[7]!!
            val number8 = uniqueLengths[8]!!

            stringToNumber[number1.chunked(1).toSet()] = "1"
            stringToNumber[number4.chunked(1).toSet()] = "4"
            stringToNumber[number7.chunked(1).toSet()] = "7"
            stringToNumber[number8.chunked(1).toSet()] = "8"

            val segmentCAndSegmentFCandidates = number1.chunked(1)
                    .intersect(number7.chunked(1))
            val segmentA = number7.chunked(1).minus(segmentCAndSegmentFCandidates).first()

            val twoThreeFive = row.first.filter { it.length == 5 }
            val number3 = twoThreeFive.filter { it.chunked(1).intersect(segmentCAndSegmentFCandidates).size == 2 }
                    .first() // 3as
            val segmentDAndSegmentGCandidates = number3.chunked(1).minus(segmentA).minus(segmentCAndSegmentFCandidates)
            val segmentD = segmentDAndSegmentGCandidates.intersect(number4.chunked(1)).first()
            val zeroSixNine = row.first.filter { it.length == 6 }
            val number0 = zeroSixNine.first {
                it.chunked(1).intersect(segmentCAndSegmentFCandidates).size == 2 && it
                        .chunked(1).minus(segmentD).size == 6
            }
            val number6 = zeroSixNine.minus(number0).filter { it.chunked(1).intersect(number1.chunked(1)).size == 1 }
                    .first()
            val number5 = twoThreeFive.minus(number3).filter {
                it.chunked(1).intersect(number6.chunked(1)).size == 5
            }.first()
            val number2 = twoThreeFive.minus(number5).minus(number3).first()
            val number9 = zeroSixNine.minus(number0).minus(number6).first()

            stringToNumber[number3.chunked(1).toSet()] = "3"
            stringToNumber[number0.chunked(1).toSet()] = "0"
            stringToNumber[number6.chunked(1).toSet()] = "6"
            stringToNumber[number5.chunked(1).toSet()] = "5"
            stringToNumber[number2.chunked(1).toSet()] = "2"
            stringToNumber[number9.chunked(1).toSet()] = "9"

            row.second.map {
                stringToNumber[it.chunked(1).toSet()]
            }.reduce { a, b -> "$a$b" }?.toInt()!!
        }.sum().toString()
    }

    private fun getInput() = readInput().map { it.split("|").map { it.trim() } }
            .map { it[0].split(" ") to it[1].split(" ") }

    override fun getInputName() = "day8_input1.txt"

}
