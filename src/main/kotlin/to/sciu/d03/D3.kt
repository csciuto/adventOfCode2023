package to.sciu.d03

import to.sciu.AdventDay

abstract class BaseDayThree: AdventDay() {
    override fun testHere(inputLines: List<String>): String {

        // Convert to something mutable.
        val mutableList = mutableListOf<CharArray>()
        for (str in inputLines) {
            mutableList.add(str.toCharArray())
        }

        var totalValue = 0

        for (lineIndex in 0 ..< mutableList.size) {
            val currentLine = mutableList[lineIndex]

            for (idxInLine in 0 ..< currentLine.size) {
                if (currentLine[idxInLine].isDigit() || currentLine[idxInLine] == '.') {
                    // Keep on truckin'
                } else {
                    totalValue += tallyAdjacentNumbers(mutableList, lineIndex, idxInLine)
                }
            }
        }

        return totalValue.toString()
    }

    private fun tallyAdjacentNumbers(inputLines: List<CharArray>, lineIndex: Int, symbolIdx: Int): Int {
        val newNumbers = mutableListOf<Int>();

        // We only check this if there's a row above us
        if (lineIndex > 0) {
            // if we aren't on the left-hand side of the map
            if (symbolIdx >= 1) {
                newNumbers += checkNorthWest(inputLines, lineIndex, symbolIdx)
            }
            // if we're on the left-hand side of the map, OR, we didn't already find a digit to the upper left
            if (symbolIdx == 0 || !inputLines[lineIndex - 1][symbolIdx - 1].isDigit()) {
                newNumbers += checkNorth(inputLines, lineIndex, symbolIdx)
            }
            // if there's still a space to check to our upper right and above us wasn't a digit (and already done)
            if (symbolIdx < inputLines[lineIndex].size - 1 && !inputLines[lineIndex - 1][symbolIdx].isDigit()) {
                newNumbers += checkNorthEast(inputLines, lineIndex, symbolIdx)
            }
        }

        // if we aren't on the left-hand side of the map
        if (symbolIdx >= 1) {
            newNumbers += checkWest(inputLines, lineIndex, symbolIdx)
        }
        // if we aren't on the right-hand side of the map
        if (symbolIdx < inputLines[lineIndex].size - 1) {
            newNumbers += checkEast(inputLines, lineIndex, symbolIdx)
        }

        // We only check this if there's a row below us
        if (lineIndex < inputLines.size - 1) {
            // if we aren't on the left-hand side of the map
            if (symbolIdx >= 1) {
                newNumbers += checkSouthWest(inputLines, lineIndex, symbolIdx)
            }
            // if we're on the left-hand side of the map, OR, we didn't already find a digit to the upper left
            if (symbolIdx == 0 || !inputLines[lineIndex + 1][symbolIdx - 1].isDigit()) {
                newNumbers += checkSouth(inputLines, lineIndex, symbolIdx)
            }
            // if there's still a space to check to our upper right and above us wasn't a digit (and already done)
            if (symbolIdx < inputLines[lineIndex].size - 1 && !inputLines[lineIndex + 1][symbolIdx].isDigit()) {
                newNumbers += checkSouthEast(inputLines, lineIndex, symbolIdx)
            }
        }

        return doFinalTally(newNumbers)

    }

    private fun checkNorthWest(inputLines: List<CharArray>, lineIndex: Int, symbolIdx: Int): List<Int> {
        val foundNumbers = mutableListOf<Int>()
        val lineIdxAboveUs = lineIndex - 1
        var northWestMarch = symbolIdx - 1

        if (inputLines[lineIdxAboveUs][northWestMarch].isDigit()) {
            //go backwards till we either hit a non-digit or a wall.
            northWestMarch--
            while (northWestMarch >= 0 && inputLines[lineIdxAboveUs][northWestMarch].isDigit()) {
                northWestMarch--
            }
            northWestMarch++ // Since we went one too far
            //northWestMarch now starts the number.

            var northEastMarch = symbolIdx
            if (inputLines[lineIdxAboveUs][symbolIdx].isDigit()) {
                // If North is a digit, keep going East until a wall or a non-digit.
                northEastMarch++
                while (northEastMarch < inputLines[lineIdxAboveUs].size
                    && inputLines[lineIdxAboveUs][northEastMarch].isDigit()
                ) {
                    northEastMarch++
                }
            }

            foundNumbers.add(String(inputLines[lineIdxAboveUs]).substring(northWestMarch, northEastMarch).toInt())
            for (i in northWestMarch ..< northEastMarch) {
                inputLines[lineIdxAboveUs][i] = 'x'
            }
        }
        return foundNumbers
    }

    private fun checkNorth(inputLines: List<CharArray>, lineIndex: Int, symbolIdx: Int): List<Int> {
        val foundNumbers = mutableListOf<Int>()
        val lineIdxAboveUs = lineIndex - 1
        var northMarch = symbolIdx

        if (inputLines[lineIdxAboveUs][northMarch].isDigit()) {
            //go forwards till we either hit a non-digit or a wall.
            northMarch++
            while (northMarch < inputLines[lineIdxAboveUs].size && inputLines[lineIdxAboveUs][northMarch].isDigit()) {
                northMarch++
            }
            northMarch-- // Since we went one too far
            //northMarch now ends the number.
            foundNumbers.add(String(inputLines[lineIdxAboveUs]).substring(symbolIdx, northMarch + 1).toInt())
            for (i in symbolIdx ..< northMarch + 1) {
                inputLines[lineIdxAboveUs][i] = 'x'
            }
        }
        return foundNumbers
    }

    private fun checkNorthEast(inputLines: List<CharArray>, lineIndex: Int, symbolIdx: Int): List<Int> {
        val foundNumbers = mutableListOf<Int>()
        val lineIdxAboveUs = lineIndex - 1

        var northEastMarch = symbolIdx + 1

        if (inputLines[lineIdxAboveUs][northEastMarch].isDigit()) {
            //go forwards till we either hit a non-digit or a wall.
            northEastMarch++
            while (northEastMarch < inputLines[lineIdxAboveUs].size && inputLines[lineIdxAboveUs][northEastMarch].isDigit()) {
                northEastMarch++
            }
            northEastMarch-- // Since we went one too far
            //northEastMarch now ends the number.
            foundNumbers.add(String(inputLines[lineIdxAboveUs]).substring(symbolIdx + 1, northEastMarch + 1).toInt())
            for (i in symbolIdx + 1 ..< northEastMarch + 1) {
                inputLines[lineIdxAboveUs][i] = 'x'
            }
        }
        return foundNumbers
    }

    private fun checkSouthWest(inputLines: List<CharArray>, lineIndex: Int, symbolIdx: Int): List<Int> {
        val foundNumbers = mutableListOf<Int>()
        val lineIdxBelowUs = lineIndex + 1
        var southWestMarch = symbolIdx - 1

        if (inputLines[lineIdxBelowUs][southWestMarch].isDigit()) {
            //go backwards till we either hit a non-digit or a wall.
            southWestMarch--
            while (southWestMarch >= 0 && inputLines[lineIdxBelowUs][southWestMarch].isDigit()) {
                southWestMarch--
            }
            southWestMarch++ // Since we went one too far
            //southWestMarch now starts the number.

            var southEastMarch = symbolIdx
            if (inputLines[lineIdxBelowUs][symbolIdx].isDigit()) {
                // If South is a digit, keep going East until a wall or a non-digit.
                southEastMarch++
                while (southEastMarch < inputLines[lineIdxBelowUs].size
                    && inputLines[lineIdxBelowUs][southEastMarch].isDigit()
                ) {
                    southEastMarch++
                }
            }

            foundNumbers.add(String(inputLines[lineIdxBelowUs]).substring(southWestMarch, southEastMarch).toInt())
            for (i in southWestMarch ..< southEastMarch) {
                inputLines[lineIdxBelowUs][i] = 'x'
            }
        }
        return foundNumbers
    }

    private fun checkSouth(inputLines: List<CharArray>, lineIndex: Int, symbolIdx: Int): List<Int> {
        val foundNumbers = mutableListOf<Int>()
        val lineIdxBelowUs = lineIndex + 1
        var southMarch = symbolIdx

        if (inputLines[lineIdxBelowUs][southMarch].isDigit()) {
            //go forwards till we either hit a non-digit or a wall.
            southMarch++
            while (southMarch < inputLines[lineIdxBelowUs].size && inputLines[lineIdxBelowUs][southMarch].isDigit()) {
                southMarch++
            }
            southMarch-- // Since we went one too far
            //southMarch now ends the number.
            foundNumbers.add(String(inputLines[lineIdxBelowUs]).substring(symbolIdx, southMarch + 1).toInt())
            for (i in symbolIdx ..< southMarch + 1) {
                inputLines[lineIdxBelowUs][i] = 'x'
            }
        }
        return foundNumbers
    }

    private fun checkSouthEast(inputLines: List<CharArray>, lineIndex: Int, symbolIdx: Int): List<Int> {
        val foundNumbers = mutableListOf<Int>()
        val lineIdxBelowUs = lineIndex + 1

        var southEastMarch = symbolIdx + 1

        if (inputLines[lineIdxBelowUs][southEastMarch].isDigit()) {
            //go forwards till we either hit a non-digit or a wall.
            southEastMarch++
            while (southEastMarch < inputLines[lineIdxBelowUs].size && inputLines[lineIdxBelowUs][southEastMarch].isDigit()) {
                southEastMarch++
            }
            southEastMarch-- // Since we went one too far
            //southEastMarch now ends the number.
            foundNumbers.add(String(inputLines[lineIdxBelowUs]).substring(symbolIdx + 1, southEastMarch + 1).toInt())
            for (i in symbolIdx + 1 ..< southEastMarch + 1) {
                inputLines[lineIdxBelowUs][i] = 'x'
            }
        }
        return foundNumbers
    }

    private fun checkEast(inputLines: List<CharArray>, lineIndex: Int, symbolIdx: Int): List<Int> {
        val foundNumbers = mutableListOf<Int>()
        var eastMarch = symbolIdx + 1

        if (inputLines[lineIndex][eastMarch].isDigit()) {
            //go forwards till we either hit a non-digit or a wall.
            eastMarch++
            while (eastMarch < inputLines[lineIndex].size && inputLines[lineIndex][eastMarch].isDigit()) {
                eastMarch++
            }
            eastMarch-- // Since we went one too far
            //northEastMarch now ends the number.
            foundNumbers.add(String(inputLines[lineIndex]).substring(symbolIdx + 1, eastMarch + 1).toInt())
            for (i in symbolIdx + 1 ..< eastMarch + 1) {
                inputLines[lineIndex][i] = 'x'
            }
        }
        return foundNumbers
    }

    private fun checkWest(inputLines: List<CharArray>, lineIndex: Int, symbolIdx: Int): List<Int> {
        val foundNumbers = mutableListOf<Int>()
        var westMarch = symbolIdx - 1

        if (inputLines[lineIndex][westMarch].isDigit()) {
            //go backwards till we either hit a non-digit or a wall.
            westMarch--
            while (westMarch >= 0 && inputLines[lineIndex][westMarch].isDigit()) {
                westMarch--
            }
            westMarch++ // Since we went one too far
            //southWestMarch now starts the number.

            var southEastMarch = symbolIdx
            if (inputLines[lineIndex][symbolIdx].isDigit()) {
                // If South is a digit, keep going East until a wall or a non-digit.
                southEastMarch++
                while (southEastMarch < inputLines[lineIndex].size
                    && inputLines[lineIndex][southEastMarch].isDigit()
                ) {
                    southEastMarch++
                }
            }

            foundNumbers.add(String(inputLines[lineIndex]).substring(westMarch, southEastMarch).toInt())
            for (i in westMarch ..< southEastMarch) {
                inputLines[lineIndex][i] = 'x'
            }
        }
        return foundNumbers
    }

    abstract fun doFinalTally(tallies: List<Int>): Int
}

class DayThreeA: BaseDayThree() {
    override fun doFinalTally(tallies: List<Int>): Int {
        return tallies.sum()
    }
}

class DayThreeB: BaseDayThree() {
    override fun doFinalTally(tallies: List<Int>): Int {
        if (tallies.size == 2) {
            return tallies.reduce{x, y -> x * y}
        } else {
            return 0
        }
    }
}
