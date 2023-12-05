package to.sciu.d04

import to.sciu.AdventDay
import kotlin.math.pow
import kotlin.streams.toList

class DayFourA: AdventDay() {

    override fun testHere(inputLines: List<String>): String {

        val winnings = inputLines.map { line ->
            val (_, cardContents ) = line.split(": ")
            val (picks, wins) = cardContents.split(" | ")
                .stream()
                .map { it.chunked(3)
                    .map{ chunk -> chunk.trim().toInt() } }
                .toList()
            val winners = picks.intersect(wins.toSet())
            2.0.pow(winners.size - 1)
        }.fold(0) { x, y -> (x + y).toInt() }

        return winnings.toString()
    }

}

class DayFourB: AdventDay() {

    private data class Card(val picks: List<Int>, val wins: List<Int>)

    override fun testHere(inputLines: List<String>): String {

        val cards = parseCards(inputLines)
        val cardsToPlay = mutableMapOf<Int, Int>()
        for (i in 0 ..< cards.size) {
            cardsToPlay[i] = 1
        }

        // For each card, indexed...
        for (i in 0 ..< cardsToPlay.size) {
            // For each time we should play...
            for (j in 0 ..< cardsToPlay[i]!!) {
                // Calculate the winners
                val winners = cards[i]
                // If there are any
                if (winners.isNotEmpty()) {
                    //Add the winnings to the next batch of cards.
                    for (k in 1..winners.size) {
                        cardsToPlay[i + k] = cardsToPlay[i + k]!! + 1
                    }
                }
            }
        }

        return cardsToPlay.map { it.value }.sum().toString()
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

}
