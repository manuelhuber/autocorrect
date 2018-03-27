package util

fun <T> MutableMap<Char, T>.getOrFill(key: Char, defaultValue: T): T {
    if (this.containsKey(key)) return this[key]!!
    this.put(key, defaultValue)
    return defaultValue
}
