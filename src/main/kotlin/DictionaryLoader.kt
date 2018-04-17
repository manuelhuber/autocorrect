import java.io.BufferedReader
import java.io.File

class DictionaryLoader {
    /**
     * Loads the file with word counts to create a map with words and their number of uses
     */
    fun getDictionary(): Map<String, Int>? {
        val inputString = File("src/resources/count_big.txt")
                .inputStream()
                .bufferedReader()
                .use(BufferedReader::readText)
        val foldFun: (MutableMap<String, Int>, String) -> MutableMap<String, Int> = { dictionary, line ->
            val parsedLine = line.split('\t')
            if (parsedLine.size == 2) {
                dictionary[parsedLine[0]] = parsedLine[1].toInt()
            }
            dictionary
        }
        return inputString.lineSequence().fold(mutableMapOf(), foldFun)
    }

}