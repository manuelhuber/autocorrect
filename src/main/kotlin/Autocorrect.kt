import model.Suggestion
import java.util.*

/**
 * Generates corrections for the given word based on a dictionary
 */
class Autocorrect(dictionaryLoader: DictionaryLoader) {
    private var dictionary: Map<String, Int> = dictionaryLoader.getDictionary()!!
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
                       numberOfSuggestions: Int,
                       slow: Boolean): List<Suggestion> {
        val date = Date()
        val value = dictionary.keys
                // Remove words that are too short - longer words will be kept to allow for autocomplete
                .filter { word -> word.length > input.length || Math.abs(word.length - input.length) < m }
                .map { word ->
                    // calculate the distances etc
                    val distance = if (slow) distance(input, word) else wordDistanceTree.distance(input, word)
                    Suggestion(word, distance, dictionary[word]!!)
                }
                .sortedWith(compareBy<Suggestion> { it.distance }.thenByDescending { it.occurences })
                .take(numberOfSuggestions)

        println("Duration: " + (Date().time - date.time))
        return value
    }

}
