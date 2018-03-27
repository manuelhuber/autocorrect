fun distance(word1: String, word2: String): Int {
    val rowWord = word1.toLowerCase()
    val columnWord = word2.toLowerCase()
    val matrix = Array(rowWord.length + 1, { IntArray(columnWord.length + 1) })
    for (row: Int in 0 until rowWord.length + 1) {
        for (column: Int in 0 until columnWord.length + 1) {
            when {
                row == 0 -> matrix[0][column] = column * getInsertCost()
                column == 0 -> matrix[row][0] = row * getDeleteCost()
                else -> {
                    matrix[row][column] = calcCell(matrix, row, column, rowWord[row - 1], columnWord[column - 1])
                }
            }
        }
    }
    printMatrix(matrix)
    return matrix[rowWord.length][columnWord.length]
}

fun calcCell(matrix: Array<IntArray>, row: Int, column: Int, charA: Char, charB: Char): Int {
    val top = matrix[row - 1][column] + getDeleteCost()
    val left = matrix[row][column - 1] + getInsertCost()
    val diag = matrix[row - 1][column - 1] + getLetterCost(charA, charB)
    return Math.min(Math.min(top, left), diag)
}

fun printMatrix(matrix: Array<IntArray>) {
    for (row: Int in 0 until matrix.size) {
        println()
        for (column: Int in 0 until matrix[0].size) {
            print(matrix[row][column].toString().padStart(2, '0'))
            print(' ')
        }
    }
    println()

}