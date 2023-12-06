package to.sciu.d05

import to.sciu.AdventDay
import to.sciu.d05.Helpers.calculateLocation
import to.sciu.d05.Helpers.getNextLookup

enum class MapType{
    SEED_TO_SOIL,
    SOIL_TO_FERT,
    FERT_TO_H20,
    H20_TO_LIGHT,
    LIGHT_TO_TEMP,
    TEMP_TO_HUM,
    HUM_TO_LOC;

    companion object {
        fun mapMapper(line: String): MapType =
            when (line) {
                "seed-to-soil map:" -> SEED_TO_SOIL
                "soil-to-fertilizer map:" -> SOIL_TO_FERT
                "fertilizer-to-water map:" -> FERT_TO_H20
                "water-to-light map:" -> H20_TO_LIGHT
                "light-to-temperature map:" -> LIGHT_TO_TEMP
                "temperature-to-humidity map:" -> TEMP_TO_HUM
                "humidity-to-location map:" -> HUM_TO_LOC
                else -> throw IllegalArgumentException("$line is not a valid map name.")
            }
    }
}

data class AlmanacRecord(val destIdx: Long, val srcIdx: Long, val range: Int)

object Helpers {
    fun getNextLookup(seed: Long, records: List<AlmanacRecord>): Long {
        var nextLookup: Long = -1
        for (record in records) {
            nextLookup = if (seed >= record.srcIdx && seed <= record.srcIdx + record.range) {
                val destOffset = seed - record.srcIdx
                record.destIdx + destOffset
            } else {
                -1
            }
            if (nextLookup >= 0) break
        }
        if (nextLookup == -1L) {
            nextLookup = seed
        }
        return nextLookup
    }

    fun calculateLocation(chapters: Map<MapType, List<AlmanacRecord>>, seed: Long): Long {
        var nextOffset: Long = getNextLookup(seed, chapters[MapType.SEED_TO_SOIL]!!)
        nextOffset = getNextLookup(nextOffset, chapters[MapType.SOIL_TO_FERT]!!)
        nextOffset = getNextLookup(nextOffset, chapters[MapType.FERT_TO_H20]!!)
        nextOffset = getNextLookup(nextOffset, chapters[MapType.H20_TO_LIGHT]!!)
        nextOffset = getNextLookup(nextOffset, chapters[MapType.LIGHT_TO_TEMP]!!)
        nextOffset = getNextLookup(nextOffset, chapters[MapType.TEMP_TO_HUM]!!)
        return getNextLookup(nextOffset, chapters[MapType.HUM_TO_LOC]!!)
    }
}
class Day5A: AdventDay() {

    data class Almanac(val seeds: List<Long>, val chapters: Map<MapType, List<AlmanacRecord>>)

    override fun testHere(inputLines: List<String>): String {

        val almanac = parseAlmanac(inputLines)

        var finalLocation: Long = -1
        for (seed in almanac.seeds) {
            val location = calculateLocation(almanac.chapters, seed)
            if (finalLocation < 0) finalLocation = location
            finalLocation = finalLocation.coerceAtMost(location)
        }

        return finalLocation.toString()
    }

    private fun parseAlmanac(inputLines: List<String>): Almanac {

        var i = 0

        val seedList: List<Long> = doSeedParse(inputLines[i])
        i++

        val chapters = mutableMapOf<MapType, List<AlmanacRecord>>()
        while(i < inputLines.size) {
            if(inputLines[i].first().isLetter()) {
                val mapType = MapType.mapMapper(inputLines[i])
                val recordData: MutableList<AlmanacRecord> = mutableListOf()

                i++
                while(i < inputLines.size && inputLines[i].first().isDigit()) {
                    val (x, y, z) = inputLines[i].split(" ")
                    recordData.add(AlmanacRecord(x.toLong(), y.toLong(), z.toInt()))
                    i++
                }
                chapters[mapType] = recordData
            }
        }

        return Almanac(seedList, chapters)
    }

    fun doSeedParse(line: String) = line
        .substringAfter(' ')
        .split(" ")
        .map { it.toLong() }

}

class Day5B: AdventDay() {
    data class Almanac(val seeds: List<LongRange>, val chapters: Map<MapType, List<AlmanacRecord>>)

    override fun testHere(inputLines: List<String>): String {

        val almanac = parseAlmanac(inputLines)

        var finalLocation: Long = -1
        for (seedRange in almanac.seeds) {
            for (seed in seedRange) {
                val location = calculateLocation(almanac.chapters, seed)
                if (finalLocation < 0) finalLocation = location
                finalLocation = finalLocation.coerceAtMost(location)
            }
        }

        return finalLocation.toString()
    }

    private fun parseAlmanac(inputLines: List<String>): Almanac {

        var i = 0

        val seedList: List<LongRange> = doSeedParse(inputLines[i])
        i++

        val chapters = mutableMapOf<MapType, List<AlmanacRecord>>()
        while(i < inputLines.size) {
            if(inputLines[i].first().isLetter()) {
                val mapType = MapType.mapMapper(inputLines[i])
                val recordData: MutableList<AlmanacRecord> = mutableListOf()

                i++
                while(i < inputLines.size && inputLines[i].first().isDigit()) {
                    val (x, y, z) = inputLines[i].split(" ")
                    recordData.add(AlmanacRecord(x.toLong(), y.toLong(), z.toInt()))
                    i++
                }
                chapters[mapType] = recordData
            }
        }

        return Almanac(seedList, chapters)
    }

    fun doSeedParse(line: String): List<LongRange> {
        val seedRangeBoundaries = line.substringAfter(' ').split(" ")
        val seeds = mutableListOf<LongRange>()

        var i = 0
        while (i < seedRangeBoundaries.size) {
            val seedRange = LongRange(seedRangeBoundaries[i].toLong(),
                (seedRangeBoundaries[i++].toLong() + seedRangeBoundaries[i++].toLong()))
            seeds.add(seedRange)
        }

        return seeds
    }

}
