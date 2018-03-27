import model.Row

class WordDistanceTree {

    private var lastInput: String = ""
    private val startingRow = Row('†', listOf(1..20).flatten().toMutableList(), null)
    private var previousRow: Row = startingRow

    fun distance(input: String, word2: String): Int {
        val valid = input.startsWith(lastInput)

        previousRow = startingRow

        for (row: Int in 1 until word2.length) {
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

    private fun fillRow(thisRow: Row, rowWord: String) {
        calcCellRec(thisRow, rowWord, rowWord.length)
    }

    private fun calcCellRec(thisRow: Row, rowWord: String, column: Int): Int {
        val cachedValue = thisRow.costs.getOrNull(column)
        val cost = when {
            column == 0 -> previousRow.costs[0] + 1
            cachedValue != null -> thisRow.costs[column]
            else -> {
                val top = calcCellRec(thisRow.previousRow!!, rowWord, column) + getDeleteCost()
                val left = calcCellRec(thisRow, rowWord, column - 1) + getInsertCost()
                val diag = calcCellRec(thisRow.previousRow, rowWord, column - 1) + getLetterCost(thisRow.char, rowWord[column - 1])
                Math.min(Math.min(top, left), diag)
            }
        }
        thisRow.costs.add(column, cost)
        return cost
    }

    private fun generateRow(char: Char, row: Int, previousRow: Row): Row {
        return Row(char, mutableListOf(row), previousRow)
    }

    fun calcCell(matrix: Array<IntArray>, row: Int, column: Int, charA: Char, charB: Char): Int {
        val top = matrix[row - 1][column] + getDeleteCost()
        val left = matrix[row][column - 1] + getInsertCost()
        val diag = matrix[row - 1][column - 1] + getLetterCost(charA, charB)
        return Math.min(Math.min(top, left), diag)
    }

}
