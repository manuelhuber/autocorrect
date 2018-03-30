package model

class Suggestion(val word: String,
                 var score: Double,
                 var distance: Int,
                 var occurences: Int) {
    override fun toString(): String {
        return "$word \t score=${String.format("%.2f", score)} \t distance=$distance \t occurrences=$occurences)"
    }
}
