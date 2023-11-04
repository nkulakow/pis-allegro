import org.junit.jupiter.api.Test;

import static org.example.Main.isNumberEven;
import static org.junit.jupiter.api.Assertions.*;

public class MySimpleTest {
    @Test
    void testAdd() {
        assertEquals(5, 5, "Test to check if JUnit works fine.");
    }

    @Test
    void Number4IsEven() {
        assertTrue(isNumberEven(4), "Test to check if isNumberEven return true for 4");}

    @Test
    void Number5IsOdd() {
        assertFalse(isNumberEven(5), "Test to check if isNumberEven return false for 5");}
}
