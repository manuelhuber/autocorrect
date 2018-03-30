import java.io.BufferedReader
import java.io.File

/**
 * Generates corrections for the given word based on a dictionary
 */
class Autocorrect {
    private var dictionary: Map<String, Int> = loadDictionary()!!
    private val wordDistanceTree = WordDistanceTree()

    /**
     * Generates 3 suggestions for the given input
     * @return a list of Pair<suggestedWord, editDistance>
     */
    fun topThreeSuggestions(input: String): List<Pair<String, Int>> {
        val sortPairs = { pairA: Pair<String, Int>, pairB: Pair<String, Int> ->
            val (wordA, costA) = pairA
            val (wordB, costB) = pairB
            // When the words are equally as expensive compare by frequency
            if (costA == costB) dictionary[wordA]!! - dictionary[wordB]!!
            else costA - costB
        }

        return dictionary.keys
                .filter { s -> Math.abs(s.length - input.length) < 4 }
                .map { s -> Pair(s, wordDistanceTree.distance(input, s)) }
                .sortedWith(Comparator(sortPairs))
                .take(3)
    }

    /**
     * Loads the file with word counts to create a map with words and their number of uses
     */
    private fun loadDictionary(): Map<String, Int>? {
        val inputString = File("src/resources/count_big.txt")
                .inputStream()
                .bufferedReader()
                .use(BufferedReader::readText)
        val dic: MutableMap<String, Int> = mutableMapOf()
        val foldFun: (MutableMap<String, Int>, String) -> MutableMap<String, Int> = { dictionary, line ->
            val parsedLine = line.split('\t')
            if (parsedLine.size == 2) {
                dictionary[parsedLine[0]] = parsedLine[1].toInt()
            }
            dictionary
        }
        return inputString.lineSequence().fold(dic, foldFun)
    }
}
