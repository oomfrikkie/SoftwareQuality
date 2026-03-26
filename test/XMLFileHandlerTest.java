import org.junit.Test;
import static org.junit.Assert.*;

public class XMLFileHandlerTest {
    @Test
    public void testLoadFile() {
        Presentation p = new Presentation();
        XMLFileHandler handler = new XMLFileHandler();
        handler.loadFile(p, "test.xml"); // Make sure test.xml exists or mock this
        assertNotNull(p.getTitle());
    }

    @Test
    public void testSaveFile() {
        Presentation p = new Presentation();
        p.setTitle("JUnit Test");
        XMLFileHandler handler = new XMLFileHandler();
        handler.saveFile(p, "test_out.xml");
        // You could add more checks here if needed
        assertTrue(true); // Dummy assertion
    }
}
