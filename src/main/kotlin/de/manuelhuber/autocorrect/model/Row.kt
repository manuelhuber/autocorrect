package de.manuelhuber.autocorrect.model

class Row(val char: Char,
          val costs: MutableList<Int>,
          val previousRow: Row?,
          val next: MutableMap<Char, Row> = mutableMapOf(),
          var dirty: Boolean = false)
