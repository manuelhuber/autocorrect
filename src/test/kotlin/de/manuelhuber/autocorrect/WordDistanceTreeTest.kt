import de.manuelhuber.autocorrect.WordDistanceTree
import de.manuelhuber.autocorrect.getDeleteCost
import de.manuelhuber.autocorrect.getInsertCost
import de.manuelhuber.autocorrect.getLetterCost
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WordDistanceTreeTest {

    @Test
    fun noDistance() {
        val tree = WordDistanceTree()
        assertEquals(0, tree.distance("ablaze", "ablaze"))
        assertEquals(0, tree.distance("", ""))
        assertEquals(0, tree.distance("x", "X"))
        assertEquals(0, getLetterCost('x', 'X'))
        assertEquals(0, tree.distance("X", "x"))
    }

    @Test
    fun missingLetter() {
        val tree = WordDistanceTree()
        assertEquals(getInsertCost(), tree.distance("blze", "blaze"))
        assertEquals(getInsertCost() * 2, tree.distance("abcde", "ade"))
    }

    @Test
    fun unnecessaryLetter() {
        val tree = WordDistanceTree()
        assertEquals(getDeleteCost(), tree.distance("balaze", "blaze"))
        assertEquals(getDeleteCost(), tree.distance("ama", "am"))
        assertEquals(getInsertCost() * 2, tree.distance("foobar", "fbar"))
    }

    @Test
    fun typo() {
        val tree = WordDistanceTree()
        assertEquals(getLetterCost('t', 'z'), tree.distance("ablate", "ablaze"))
        assertEquals(getLetterCost('r', 'z'), tree.distance("ablare", "ablaze"))
        assertEquals(getLetterCost('e', 'z'), tree.distance("ablaee", "ablaze"))
        assertEquals(getLetterCost('m', 'z'), tree.distance("ablame", "ablaze"))
    }

    @Test
    fun sameSame() {
        val tree = WordDistanceTree()
        assertEquals(
                tree.distance("amazingly", "admonishingly"),
                tree.distance("amazingly", "admonishingly")
        )
    }

    @Test
    fun statelessness() {
        val tree = WordDistanceTree()
        tree.distance("a", "admonish")
        tree.distance("am", "admonish")
        tree.distance("ama", "admonish")
        tree.distance("amaz", "admonish")
        tree.distance("amazi", "admonish")
        tree.distance("amazin", "admonish")
        tree.distance("amazing", "admonish")
        tree.distance("amazingl", "admonish")
        val controlTree = WordDistanceTree()
        assertEquals(tree.distance("amazingly", "admonish"),
                controlTree.distance("amazingly", "admonish"))
    }

    @Test
    fun statelessMissingLetter() {
        val tree = WordDistanceTree()
        tree.distance("a", "am")
        tree.distance("am", "am")
        val distance = tree.distance("ama", "am")
        assertEquals(getDeleteCost(), distance)

        val controlTree = WordDistanceTree()
        assertEquals(distance,
                controlTree.distance("ama", "am"))
    }

    @Test
    fun autocomplete() {
        val tree = WordDistanceTree()
        assertEquals(0, tree.distance("a", "ablaze"))
        assertEquals(0, tree.distance("ablaze", "ablazeingor"))
    }

    @Test
    fun autocompleteLetterMissing() {
        val tree = WordDistanceTree()
        assertEquals(getInsertCost(), tree.distance("ablz", "ablazeasdasdasdasdasdasdasdasd"))
    }

    @Test
    fun autocompleteTypo() {
        val tree = WordDistanceTree()
        assertEquals(getLetterCost('x', 'b'), tree.distance("axla", "ablazeasdasdasdasdasdasdasdasd"))
    }

    @Test
    fun autocompleteUnnecessaryLetter() {
        val tree = WordDistanceTree()
        // This could either be deletion (+free insert) or replace letters - the algorithm will take the lower one
        assertEquals(Math.min(getDeleteCost(), getLetterCost('l','a')), tree.distance("abll", "ablazeasdasdasdasdasdasdasdasd"))
    }

}
