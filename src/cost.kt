fun getInsertCost(): Int {
    return 3
}

fun getDeleteCost(): Int {
    return 3
}

fun getLetterCost(a: Char, b: Char): Int {
    return when (letterDistance(a, b)) {
        0 -> 0
        1 -> 1
        2 -> 2
        else -> 5
    }
}

val row1 = listOf('q', 'w', 'e', 'r', 't', 'z', 'u', 'i', 'o', 'p', 'ü')
val row2 = listOf('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'ö', 'ä')
val row3 = listOf('<', 'y', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.')
val rowOfRows = listOf(row1, row2, row3)

fun letterDistance(a: Char, b: Char): Int {
    val dataA = getData(a)
    val dataB = getData(b)
    val rowDistance = Math.abs(dataA.first - dataB.first)
    val columnDistance = Math.abs(dataA.second - dataB.second)
    return Math.max(rowDistance, columnDistance)
}

/**
 * returns a pair<rowNumber, columnNumber>
 */
fun getData(a: Char): Pair<Int, Int> {
    val row: List<Char> = rowOfRows.find { row -> row.indexOf(a) != -1 }!!
    return Pair(rowOfRows.indexOf(row), row.indexOf(a))
}