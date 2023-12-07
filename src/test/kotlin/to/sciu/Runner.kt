package to.sciu

import to.sciu.d01.DayOneA
import to.sciu.d01.DayOneB
import to.sciu.d02.DayTwoA
import to.sciu.d02.DayTwoB
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import to.sciu.d03.DayThreeA
import to.sciu.d03.DayThreeB
import to.sciu.d04.DayFourA
import to.sciu.d04.DayFourB
import to.sciu.d05.Day5A
import to.sciu.d05.Day5B
import to.sciu.d06.Day6A
import to.sciu.d06.Day6B

class AdventRunner {

    @Test
    fun `December 1 Part 1`() {
        assertEquals("55621", DayOneA().runMe().result)
    }

    @Test
    fun `December 1 Part 2`() {
        assertEquals("53592", DayOneB().runMe().result)
    }

    @Test
    fun `December 2 Part 1`() {
        assertEquals("2486", DayTwoA().runMe().result)
    }

    @Test
    fun `December 2 Part 2`() {
        assertEquals("87984", DayTwoB().runMe().result)
    }

    @Test
    fun `December 3 Part 1`() {
        assertEquals("532331", DayThreeA().runMe().result)
    }

    @Test
    fun `December 3 Part 2`() {
        assertEquals("82301120", DayThreeB().runMe().result)
    }

    @Test
    fun `December 4 Part 1`() {
        assertEquals("25174", DayFourA().runMe().result)
    }

    @Test
    fun `December 4 Part 2`() {
        assertEquals("6420979", DayFourB().runMe().result)
    }

    @Test
    fun `December 5 Part 1`() {
        assertEquals("510109797", Day5A().runMe().result)
    }

    @Test
    @Disabled("This test takes minutes to run...")
    fun `December 5 Part 2`() {
        assertEquals("9622622", Day5B().runMe().result)
    }

    @Test
    fun `December 6 Part 1`() {
        assertEquals("800280", Day6A().runMe().result)
    }

    @Test
    fun `December 6 Part 2`() {
        assertEquals("288", Day6B().runMe("sample.txt").result)
    }
}
