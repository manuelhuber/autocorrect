import model.Row
import util.fillMeUp
import util.getOrFill

/**
 * Calculates the distance ( =cost) between two words
 * Caches the results of the last distance for increased performance when querying for the same word (or an extension
 * of the previous word)
 */
class WordDistanceTree {

    private var lastInput: String = ""
    private val startingRow = Row('†', mutableListOf(), null)

    /**
     * Calculates the distance between 2 words
     * @param input this will be the base for the cache - if you calculate multiple distances for the same word, use
     * this param for the constant word. Can only contain letters
     * @param word a string containing only letters
     */
    fun distance(input: String, word: String): Int {
        if (input.isEmpty()) return word.length * getInsertCost()
        // Invalidate the cache if the input doesn't match
        if (!input.startsWith(lastInput)) {
            startingRow.next.clear()
        }

        var previousRow = startingRow
        // Make sure the first row is as long as the word
        previousRow.costs.fillMeUp(input.length, { index -> index * getDeleteCost() })

        // Calculate the values row by row
        for (row: Int in 1 until word.length + 1) {
            val char = word[row - 1]
            val thisRow = previousRow.next.getOrPut(char, { generateRow(char, row, previousRow) })
            fillRow(thisRow, input)
            previousRow = thisRow
        }

        lastInput = input
        // Take the last value in the last row (since that's where the cost is)
        return previousRow.costs[input.length]
    }

    /**
     * Makes sure the entire row is complete
     */
    private fun fillRow(thisRow: Row, rowWord: String) {
        calcCellRecursively(thisRow, rowWord, rowWord.length)
    }

    /**
     * Calculates the cell if need be
     * If the cell is already filled with a valid value we don't calculate anything
     */
    private fun calcCellRecursively(thisRow: Row, rowWord: String, column: Int): Int {
        val costs = thisRow.costs

        // -1 means no value has been calculated yet
        val cachedValue = costs.getOrFill(column, { -1 })
        val rowNumber = thisRow.costs[0]
        // This is the area of cells that is no longer valid since they were calculated with an insert cost of 0
        val isInZeroInsertCostArea = rowNumber > rowWord.length && column >= rowWord.length
        val useCache = cachedValue != -1 && !isInZeroInsertCostArea

        costs[column] =
                if (useCache) cachedValue
                else {
                    // If the input is complete make insertion costs zero to allow for word prediction
                    val insertCost = if (column == rowWord.length) 0 else getInsertCost()
                    val top = calcCellRecursively(thisRow.previousRow!!, rowWord, column) + insertCost
                    val left = calcCellRecursively(thisRow, rowWord, column - 1) + getDeleteCost()
                    val diag = calcCellRecursively(thisRow.previousRow, rowWord, column - 1) +
                            getLetterCost(thisRow.char, rowWord[column - 1])
                    Math.min(Math.min(top, left), diag)
                }
        return costs[column]
    }

    /**
     * Generates a new row and initializes the costs array correctly
     */
    private fun generateRow(char: Char, row: Int, previousRow: Row): Row {
        val costs = mutableListOf(row * getInsertCost())
        return Row(char, costs, previousRow)
    }

}
