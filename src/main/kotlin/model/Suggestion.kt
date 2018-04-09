package model

class Suggestion(val word: String,
                 var distance: Int,
                 var occurences: Int) {
    override fun toString(): String {
        return "$word \t distance=$distance \t occurrences=$occurences)"
    }
}
