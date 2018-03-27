import java.util.*

fun main(args: Array<String>) {
    val autocomplete = Autocomplete()
    while (true) {
        val a = readLine()
        val date = Date()
        println(autocomplete.topThreeSuggestions(a!!, false))
        println("Duration: " + (Date().time - date.time))

        val date2 = Date()
        println(autocomplete.topThreeSuggestions(a, true))
        println("Duration: " + (Date().time - date2.time))
    }
}
