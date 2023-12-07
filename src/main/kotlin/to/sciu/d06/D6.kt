package to.sciu.d06

import to.sciu.AdventDay

class Day6A: AdventDay() {
    override fun testHere(inputLines: List<String>): String {
        var maxTimes: List<Int> = listOf()
        var recordDistances: List<Int> = listOf()
        inputLines.forEach { line ->
            val (type, values) = line.split(":")
            if (type == "Time") {
                maxTimes = values.split(" ").filterNot { it.isBlank() }.map { it.toInt() }.toList()
            }
            if (type == "Distance") {
                recordDistances = values.split(" ").filterNot { it.isBlank() }.map { it.toInt() }.toList()
            }
        }
        return (0..<maxTimes.size).map {i ->
            calculateWaysToWin(maxTimes[i], recordDistances[i])
        }.fold(1){ x, y -> x * y }.toString()

    }

    private fun calculateWaysToWin(maxTime: Int, recordDistance: Int): Int {

        var lastTime = -1;
        val winningTimes: MutableList<Int> = mutableListOf()

        for (buttonTime in 0 .. maxTime) {
            val myDistance = (maxTime - buttonTime) * buttonTime
            if (myDistance > recordDistance) {
                winningTimes.add(myDistance)
                lastTime = myDistance
            } else if (myDistance < lastTime) { // We're beyond the optimal time
                break
            }
        }
        return winningTimes.size
    }
}

class Day6B: AdventDay() {
    override fun testHere(inputLines: List<String>): String {
        TODO("Not yet implemented")
    }
}
