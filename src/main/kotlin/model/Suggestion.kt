package model

class Suggestion(val word: String,
                 var score: Double,
                 var distance: Int,
                 var occurences : Int) {
    override fun toString(): String {
        return "(word='$word', score=$score, distance=$distance)"
    }
}
