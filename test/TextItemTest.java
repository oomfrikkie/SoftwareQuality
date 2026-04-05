import org.junit.Test;
import static org.junit.Assert.*;

public class TextItemTest {

    @Test
    public void constructor_givenLevelAndText_shouldStoreCorrectly() {
        TextItem item = new TextItem(2, "Hello World");
        assertEquals(2, item.getLevel());
        assertEquals("Hello World", item.getText());
    }

    @Test
    public void defaultConstructor_shouldReturnDefaultText() {
        TextItem item = new TextItem();
        assertEquals(0, item.getLevel());
        assertEquals("No Text Given", item.getText());
    }

    @Test
    public void getText_givenNullText_shouldReturnEmptyString() {
        TextItem item = new TextItem(0, null);
        assertEquals("", item.getText());
    }

    @Test
    public void toString_shouldContainLevelAndText() {
        TextItem item = new TextItem(1, "Test");
        String result = item.toString();
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Test"));
    }
}
