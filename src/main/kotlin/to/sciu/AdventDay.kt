package to.sciu

import java.nio.file.Path
import kotlin.io.path.readLines

abstract class AdventDay {

    data class TestResult(val result: String, val runtime: Long) {
        override fun toString(): String = "result=$result\n\truntime=${runtime}ms"
    }

    fun runMe(fileName: String = "input.txt"): TestResult {
        val startTime = System.currentTimeMillis()
        val input = getInputLines(getPath(), fileName)
        val result = testHere(input)
        val elapsedTime = System.currentTimeMillis() - startTime
        return TestResult(result, elapsedTime)
    }

    abstract fun testHere(inputLines: List<String>): String

    private fun getInputLines(path: String, fileName:String): List<String> {
        val path = Path.of("src", "test", "resources", path, fileName)
        return path.readLines().filterNot { it.startsWith("#") }.filterNot { it == "" }
    }
    private fun getPath() = this.javaClass.packageName.replace(".","/")

}
