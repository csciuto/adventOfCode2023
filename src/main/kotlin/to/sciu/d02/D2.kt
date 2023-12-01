package to.sciu.d02

import to.sciu.AdventDay
import kotlin.streams.toList

class DayTwoA: AdventDay() {

    override fun testHere(inputLines: List<String>): String {

        val maxRed = 12
        val maxGreen = 13
        val maxBlue = 14

        val games = parseInput(inputLines)

        val possibleGames = games.stream().filter{
            game -> game.rounds.none{
                round -> round.numRed > maxRed || round.numGreen > maxGreen || round.numBlue > maxBlue}}
            .toList()

        return possibleGames.sumOf{ it.id }.toString()
    }
}
class DayTwoB: AdventDay() {

    override fun testHere(inputLines: List<String>): String {

        val games = parseInput(inputLines)

        return games.fold(0) {
                power, game ->
                    // I don't like that this is O(3N) but games don't have many rounds, so it's
                    // not bad compared to manually updating all three maxes on each iteration.
                    val maxRed = game.rounds.maxOf { it.numRed }
                    val maxBlue = game.rounds.maxOf { it.numBlue }
                    val maxGreen = game.rounds.maxOf { it.numGreen }
                    power + (maxRed * maxGreen * maxBlue)
            }.toString()
    }
}

private fun parseInput(inputLines: List<String>): List<Game> {
    val parsedGames: MutableList<Game> = ArrayList()

    for (line in inputLines) {
        // Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        val nameAndRounds = line.split(": ")
        val roundList = nameAndRounds[1]
        val gameId = nameAndRounds[0].substringAfter(" ").toInt()
        val rounds = roundList.split("; ")

        val parsedRounds: MutableList<Round> = ArrayList()

        for (round in rounds) {
            val colors = round.split(", ")
            var redVal=0; var greenVal=0; var blueVal=0
            for (color in colors) {
                val parsedColor = color.split(" ")
                val numCubes = parsedColor[0].toInt()
                when(parsedColor[1]) {
                    "red" -> redVal = numCubes
                    "green" -> greenVal = numCubes
                    "blue" -> blueVal = numCubes
                }
            }
            parsedRounds.add(Round(redVal, greenVal, blueVal))
        }

        parsedGames.add(Game(gameId, parsedRounds))
    }
    return parsedGames
}

data class Round(val numRed: Int, val numGreen: Int, val numBlue: Int)
data class Game(val id: Int, val rounds: List<Round>)
