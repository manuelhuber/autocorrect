package de.manuelhuber.autocorrect.util

/**
 * Get the value at the given index or fill the list with values up to the index (and return the value at the index)
 * @param defaultValue a function that generates a value based on the index
 */
fun <T> MutableList<T>.getOrFill(index: Int, defaultValue: (index: Int) -> T): T {
    if (index < this.size) return this[index]!!
    this.fillMeUp(index, defaultValue)
    return this[index]
}

/**
 * Fills the function up to the given index with values
 * @param index the maximum index to which the list should be filled
 * @param value a function that generates a value based on the index
 */
fun <T> MutableList<T>.fillMeUp(index: Int, value: (index: Int) -> T) {
    if (size > index) return
    if (index > this.size) this.fillMeUp(index - 1, value)
    this.add(index, value(index))

}
