fun getInsertCost(): Int {
    return 3
}

fun getDeleteCost(): Int {
    return 3
}

/**
 * Calculates the cost of changing letter a to b
 * The costs are
 * 0 if they are the same
 * 1 if they are 1 key away (horizontal, vertical or diagonal)
 * 5 otherwise
 */
fun getLetterCost(a: Char, b: Char): Int {
    val (rowA, colA) = getData(a)
    val (rowB, colB) = getData(b)
    val rowDistance = Math.abs(rowA - rowB)
    val columnDistance = Math.abs(colA - colB)
    return when (Math.max(rowDistance, columnDistance)) {
        0 -> 0
        1 -> 1
        else -> 5
    }
}

val row1 = listOf('q', 'w', 'e', 'r', 't', 'z', 'u', 'i', 'o', 'p', 'ü')
val row2 = listOf('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'ö', 'ä')
val row3 = listOf('<', 'y', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.')
val rowOfRows = listOf(row1, row2, row3)

/**
 * returns a pair<rowNumber, columnNumber>
 */
fun getData(a: Char): Pair<Int, Int> {
    val char = a.toLowerCase()
    val row: List<Char> = rowOfRows.find { row -> row.indexOf(char) != -1 }!!
    return Pair(rowOfRows.indexOf(row), row.indexOf(char))
}
