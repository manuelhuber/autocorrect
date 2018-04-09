import model.Suggestion
import java.io.BufferedReader
import java.io.File
import java.util.*

/**
 * Generates corrections for the given word based on a dictionary
 */
class Autocorrect {
    private var dictionary: Map<String, Int> = loadDictionary()!!
    private val wordDistanceTree = WordDistanceTree()

    /**
     * Generates autoCorrect / autocomplete suggestions for the given input
     *
     * @param input word for which to suggests corrections / completions
     * @param m max cost
     * @param numberOfSuggestions
     * @return a list of Pair<suggestedWord, editDistance>
     */
    fun getSuggestions(input: String,
                       m: Double,
                       numberOfSuggestions: Int): List<Suggestion> {
        val date = Date()
        val value = dictionary.keys
                // Remove words that are too short
                .filter { s -> s.length > input.length || Math.abs(s.length - input.length) < m }
                // calculate the distances etc
                .map { s ->
                    val distance = wordDistanceTree.distance(input, s)
                    Suggestion(s, distance, dictionary[s]!!)
                }
                .sortedWith(compareBy<Suggestion> { it.distance }.thenByDescending { it.occurences })
                .take(numberOfSuggestions)

        println("Duration: " + (Date().time - date.time))
        return value
    }

    /**
     * Loads the file with word counts to create a map with words and their number of uses
     */
    private fun loadDictionary(): Map<String, Int>? {
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
