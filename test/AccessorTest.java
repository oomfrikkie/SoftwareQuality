import org.junit.Test;
import static org.junit.Assert.*;

public class AccessorTest {

    @Test
    public void testXMLFileHandlerStrategy() {
        try {
            Presentation p = new Presentation();
            Accessor accessor = new Accessor();
            accessor.setStrategy(new XMLFileHandler());
            accessor.saveFile(p, "test_out.xml");
            assertTrue(true);
        } catch (java.io.IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }
    }

    @Test
    public void testJSONFileHandlerStrategy() {
        try {
            Presentation p = new Presentation();
            Accessor accessor = new Accessor();
            accessor.setStrategy(new JSONFileHandler());
            accessor.saveFile(p, "test_out.json");
            assertTrue(true);
        } catch (java.io.IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }
    }
}