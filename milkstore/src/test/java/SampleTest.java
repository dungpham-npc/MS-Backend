import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Test
    public void testAddition() {
        int result = 2 + 4;
        assertEquals(5, result, "2 + 3 should equal 5");
    }

    @Test
    public void testStringConcatenation() {
        String result = "Hello" + " " + "World";
        assertEquals("Hello World", result, "Concatenation failed");
    }
}

