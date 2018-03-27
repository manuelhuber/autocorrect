import model.Row

class WordDistanceTree {

    private var lastInput: String = ""
    private val startingRow = generateStartingRow()

    fun printMatrix(word: String) {
        println(word)
        var row = startingRow
        row.costs.forEach { i: Int -> print(i.toString().padStart(2, '0') + " ") }
        for (char: Char in word) {
            println()
            row = row.next[char]!!
            row.costs.forEach { i: Int -> print(i.toString().padStart(2, '0') + " ") }
        }
    }

    fun distance(input: String, word2: String): Int {
        val valid = input.startsWith(lastInput)
        lastInput = input
        if (!valid) {
            startingRow.next.clear()
        }

        var previousRow = startingRow

        for (row: Int in 1 until word2.length + 1) {
            val char = word2[row - 1]
            if (!previousRow.next.containsKey(char)) {
                previousRow.next.put(char, generateRow(char, row, previousRow))
            }
            val thisRow = previousRow.next[char]
            fillRow(thisRow!!, input)
            previousRow = thisRow
        }
        return previousRow.costs[input.length]
    }

    private fun generateStartingRow(): Row {
        var costs = listOf(0..20).flatten()
        costs = costs.map { i -> i * getDeleteCost() }.toMutableList()
        return Row('†', costs, null)
    }

    private fun fillRow(thisRow: Row, rowWord: String) {
        calcCellRec(thisRow, rowWord, rowWord.length)
    }

    private fun calcCellRec(thisRow: Row, rowWord: String, column: Int): Int {
        val cachedValue = thisRow.costs[column]
        val cost = when {
            cachedValue != -1 -> thisRow.costs[column]
            else -> {
                val top = calcCellRec(thisRow.previousRow!!, rowWord, column) + getDeleteCost()
                val left = calcCellRec(thisRow, rowWord, column - 1) + getInsertCost()
                val diag = calcCellRec(thisRow.previousRow, rowWord, column - 1) + getLetterCost(thisRow.char, rowWord[column - 1])
                Math.min(Math.min(top, left), diag)
            }
        }
        thisRow.costs[column] = cost
        return cost
    }

    private fun generateRow(char: Char, row: Int, previousRow: Row): Row {
        val costs = mutableListOf(row * getInsertCost())
        for (i in 1..30) {
            costs.add(-1)

        }
        return Row(char, costs, previousRow)
    }

    fun calcCell(matrix: Array<IntArray>, row: Int, column: Int, charA: Char, charB: Char): Int {
        val top = matrix[row - 1][column] + getDeleteCost()
        val left = matrix[row][column - 1] + getInsertCost()
        val diag = matrix[row - 1][column - 1] + getLetterCost(charA, charB)
        return Math.min(Math.min(top, left), diag)
    }

}
