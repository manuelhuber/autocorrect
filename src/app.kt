import java.util.*

fun main(args: Array<String>) {
    val foo = Autocomplete()
    while (true) {
        val a = readLine()
        val date = Date()
        println(foo.topThreeSuggestions(a!!))
        println("Duration: " + (Date().time - date.time))
    }
}