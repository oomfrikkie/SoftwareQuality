import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XMLFileHandlerTest {

    private Presentation presentation;
    private XMLFileHandler handler;

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
        handler = new XMLFileHandler();
    }

    @Test
    void testLoadFile() {
        handler.loadFile(presentation, "test.xml"); // make sure test.xml exists
        assertNotNull(presentation.getTitle());
    }

    @Test
    void testSaveFile() {
        presentation.setTitle("JUnit Test");
        handler.saveFile(presentation, "test_out.xml");

        assertEquals("JUnit Test", presentation.getTitle());
    }
}