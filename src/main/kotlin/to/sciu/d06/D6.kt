package to.sciu.d06

import to.sciu.AdventDay

class Day6A: AdventDay() {
    override fun testHere(inputLines: List<String>): String {
        var maxTimes: List<Long> = listOf()
        var recordDistances: List<Long> = listOf()
        inputLines.forEach { line ->
            val (type, values) = line.split(":")
            if (type == "Time") {
                maxTimes = values.split(" ").filterNot { it.isBlank() }.map { it.toLong() }.toList()
            }
            if (type == "Distance") {
                recordDistances = values.split(" ").filterNot { it.isBlank() }.map { it.toLong() }.toList()
            }
        }
        return (0..<maxTimes.size).map {i ->
            calculateWaysToWin(maxTimes[i], recordDistances[i])
        }.fold(1L){ x, y -> x * y }.toString()
    }
}

class Day6B: AdventDay() {
    override fun testHere(inputLines: List<String>): String {
        var maxTime: Long = -1
        var recordDistance: Long = -1
        inputLines.forEach { line ->
            val (type, values) = line.split(":")
            if (type == "Time") {
                maxTime = String(values.toList().filter{ i -> i != ' '}.toCharArray()).toLong()
            }
            if (type == "Distance") {
                recordDistance = String(values.toList().filter{ i -> i != ' '}.toCharArray()).toLong()
            }
        }
        return calculateWaysToWin(maxTime, recordDistance).toString()
    }
}

private fun calculateWaysToWin(maxTime: Long, recordDistance: Long): Int {

    var lastTime = -1L
    val winningTimes: MutableList<Long> = mutableListOf()

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
