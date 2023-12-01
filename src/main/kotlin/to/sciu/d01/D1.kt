package to.sciu.d01

import to.sciu.AdventDay

class DayOneA: AdventDay() {

    override fun testHere(inputLines: List<String>): String {
        val endingContents = inputLines.stream()
            .mapToInt { word -> "${word.first { char -> !char.isLetter() }}${word.last { char -> !char.isLetter() }}".toInt()}
            .sum()
        return endingContents.toString()
    }
}

class DayOneB: AdventDay() {

    private val numberMap: Map<String, String> = hashMapOf("one" to "1", "two" to "2", "three" to "3", "four" to "4",
        "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9",
        "1" to "1", "2" to "2", "3" to "3", "4" to "4", "5" to "5", "6" to "6", "7" to "7", "8" to "8", "9" to "9")

    override fun testHere(inputLines: List<String>): String {
        val endingContents = inputLines.stream()
            .mapToInt{ word ->
                    val firstWord = numberMap[word.findAnyOf(numberMap.keys)!!.second]
                    val lastWord = numberMap[word.findLastAnyOf(numberMap.keys)!!.second]
                    val finalWord = "$firstWord$lastWord"
                    finalWord.toInt()
                }
            .sum()
        return endingContents.toString()
    }
}
