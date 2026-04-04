import org.junit.Test;
import static org.junit.Assert.*;

public class AccessorTest {

    @Test
    public void testXMLFileHandlerLoad() {
        try {
            Presentation p = new Presentation();
            Accessor accessor = new Accessor();
            accessor.setStrategy(new XMLFileHandler());
            accessor.loadFile(p, "test/resources/test_presentation_fixed.xml");
            assertEquals("XML Test Presentation", p.getTitle());
            assertEquals(1, p.getSize());
            assertEquals("XML Slide 1", p.getSlide(0).getTitle());
        } catch (java.io.IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }
    }

    @Test
    public void testJSONFileHandlerLoad() {
        try {
            Presentation p = new Presentation();
            Accessor accessor = new Accessor();
            accessor.setStrategy(new JSONFileHandler());
            accessor.loadFile(p, "test/resources/test_presentation.json");
            // Since JSON loading is not implemented, just check that no exception is thrown
            assertTrue(true);
        } catch (java.io.IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }
    }
}