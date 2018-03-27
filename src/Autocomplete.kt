import java.io.File
import java.io.InputStream

class Autocomplete {
    var dictionary: Map<String, Int> = loadDictionary()!!

    fun topThreeSuggestions(input: String): List<String> {
        val sortPairs = { pairA: Pair<String, Int>, pairB: Pair<String, Int> ->
            if (pairA.second == pairB.second) {
                dictionary[pairA.first]!! - dictionary[pairB.first]!!
            } else {
                pairA.second - pairB.second
            }
        }

        return dictionary.keys.filter { s -> Math.abs(s.length - input.length) < 4 }
                .map { s -> Pair(s, distance(s, input)) }
                .sortedWith(Comparator(sortPairs))
                .take(3)
                .map { pair -> pair.first }
    }


    private fun loadDictionary(): Map<String, Int>? {
        val inputStream: InputStream = File("src/resources/count_big.txt").inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }
        val dic: MutableMap<String, Int> = mutableMapOf()
        val foldFun: (MutableMap<String, Int>, String) -> MutableMap<String, Int> = { dictionary, line ->
            val parsedLine = line.split('\t')
            if (parsedLine.size != 2) {
                println(line)
            } else {
                dictionary[parsedLine[0]] = parsedLine[1].toInt()
            }
            dictionary
        }
        return inputString.lineSequence().fold(dic, foldFun)
    }
}
