import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class XMLFileHandlerTest {

    private Presentation presentation;
    private XMLFileHandler handler;

    @Before
    public void setUp() {
        presentation = new Presentation();
        handler = new XMLFileHandler();
    }

    @Test
    public void testLoadFile() {
        handler.loadFile(presentation, "test.xml"); // make sure test.xml exists
        assertNotNull(presentation.getTitle());
    }

    @Test
    public void testSaveFile() {
        presentation.setTitle("JUnit Test");
        handler.saveFile(presentation, "test_out.xml");

        assertEquals("JUnit Test", presentation.getTitle());
    }
}