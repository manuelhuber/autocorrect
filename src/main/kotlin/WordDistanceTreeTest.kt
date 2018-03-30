import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WordDistanceTreeTest {

    @Test
    fun noDistance() {
        val tree = WordDistanceTree()
        Assertions.assertEquals(0, tree.distance("ablaze", "ablaze"))
        Assertions.assertEquals(0, tree.distance("", ""))
        Assertions.assertEquals(0, tree.distance("x", "X"))
        Assertions.assertEquals(0, tree.distance("X", "x"))
    }

    @Test
    fun missingLetter() {
        val tree = WordDistanceTree()
        Assertions.assertEquals(getInsertCost(), tree.distance("blze", "blaze"))
        Assertions.assertEquals(getInsertCost() * 2, tree.distance("abcde", "ade"))
    }

    @Test
    fun unnecessaryLetter() {
        val tree = WordDistanceTree()
        Assertions.assertEquals(getDeleteCost(), tree.distance("balaze", "blaze"))
        Assertions.assertEquals(getInsertCost() * 2, tree.distance("foobar", "fbar"))
    }

    @Test
    fun typo() {
        val tree = WordDistanceTree()
        Assertions.assertEquals(getLetterCost('t', 'z'), tree.distance("ablate", "ablaze"))
        Assertions.assertEquals(getLetterCost('r', 'z'), tree.distance("ablare", "ablaze"))
        Assertions.assertEquals(getLetterCost('e', 'z'), tree.distance("ablaee", "ablaze"))
        Assertions.assertEquals(getLetterCost('m', 'z'), tree.distance("ablame", "ablaze"))
    }

    @Test
    fun autocomplete() {
        val tree = WordDistanceTree()
        Assertions.assertEquals(0, tree.distance("a", "ablaze"))
        Assertions.assertEquals(0, tree.distance("ablaze", "ablazeingor"))
    }

    @Test
    fun autocompleteLetterMissing() {
        val tree = WordDistanceTree()
        Assertions.assertEquals(getInsertCost(), tree.distance("ablz", "ablazeasdasdasdasdasdasdasdasd"))
    }

    @Test
    fun autocompleteTypo() {
        val tree = WordDistanceTree()
        Assertions.assertEquals(getLetterCost('x', 'b'), tree.distance("axla", "ablazeasdasdasdasdasdasdasdasd"))
    }

    @Test
    fun autocompleteUnnecessaryLetter() {
        val tree = WordDistanceTree()
        // This could either be deletion (+free insert) or replace letters - the algorithm will take the lower one
        Assertions.assertEquals(getDeleteCost(), tree.distance("abll", "ablazeasdasdasdasdasdasdasdasd"))
    }

}
