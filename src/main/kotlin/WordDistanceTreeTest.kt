import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WordDistanceTreeTest {
    @Test
    fun distance() {
        val tree = WordDistanceTree()
        Assertions.assertEquals(0, tree.distance("ablaze", "ablaze"))
        Assertions.assertEquals(3, tree.distance("ablaze", "blaze"))
    }

}
