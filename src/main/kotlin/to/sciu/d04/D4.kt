package to.sciu.d04

import to.sciu.AdventDay
import kotlin.math.pow
import kotlin.streams.toList

class DayFourA: AdventDay() {

    override fun testHere(inputLines: List<String>): String {

        return parseCards(inputLines).map {winners ->
            2.0.pow(winners.size - 1)
        }.fold(0){ x, y -> (x + y).toInt() }.toString()
    }

}

class DayFourB: AdventDay() {

    override fun testHere(inputLines: List<String>): String {

        val cards = parseCards(inputLines)
        val cardsToPlay = mutableMapOf(Pair(0,1)) // Initialize just the first card for one play.

        // For each card...
        for (i in 0 ..< cards.size) {
            // For each time we should play...
            for (j in 0 ..< cardsToPlay.getOrPut(i) { 1 }) {
                // If there are any winners
                val winners = cards[i]
                if (winners.isNotEmpty()) {
                    //Add the winnings to the next batch of cards.
                    for (k in 1..winners.size) {
                        cardsToPlay[i + k] = cardsToPlay.getOrDefault(i + k, 1) + 1
                    }
                }
            }
        }

        return cardsToPlay.map { it.value }.sum().toString()
    }
}

private fun parseCards(inputLines: List<String>): List<Set<Int>> = inputLines.map { line ->
    val (_, cardContents) = line.split(": ")
    val (picks, wins) = cardContents.split(" | ")
        .stream()
        .map {
            it.chunked(3)
                .map { chunk -> chunk.trim().toInt() }
        }
        .toList()
    picks.intersect(wins.toSet())
}.toList()
