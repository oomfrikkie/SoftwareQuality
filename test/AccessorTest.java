import org.junit.Test;
import static org.junit.Assert.*;

public class AccessorTest {

    @Test
    public void loadFile_withXMLFile_XMLFileHandler_shouldLoadPresentationCorrectly() {
        try {
            Presentation p = new Presentation();
            Accessor accessor = new Accessor();
            accessor.setStrategy(new XMLFileHandler());
            accessor.loadFile(p, "test/resources/test_presentation.xml");
            assertEquals("XML Test Presentation", p.getTitle());
            assertEquals(1, p.getSize());
            assertEquals("XML Slide 1", p.getSlide(0).getTitle());
        } catch (java.io.IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }
    }

    @Test
    public void loadFile_withJSONFile_JSONFileHandler_shouldNotThrowException() {
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
    @Test
    public void loadFile_withUnsupportedHtmlFile_XMLFileHandler_shouldThrowOrFail() {
        try {
            Presentation p = new Presentation();
            Accessor accessor = new Accessor();
            // Use XMLFileHandler for a .html file to simulate unsupported type
            accessor.setStrategy(new XMLFileHandler());
            accessor.loadFile(p, "test/resources/test_presentation.html");
            // If no exception, fail the test
            fail("Expected an exception for unsupported file type");
        } catch (Exception e) {
            // Pass if any exception is thrown
            assertTrue(true);
        }
    }

    @Test
    public void getDemoAccessor_shouldReturnNonNullAccessor() {
        Accessor accessor = Accessor.getDemoAccessor();
        assertNotNull(accessor);
    }

    @Test
    public void saveAndReload_xmlFile_shouldPreserveTitle() {
        try {
            Presentation p = new Presentation();
            p.setTitle("Save Test");
            Accessor accessor = new Accessor();
            accessor.setStrategy(new XMLFileHandler());
            accessor.saveFile(p, "test_save_output.xml");

            Presentation loaded = new Presentation();
            accessor.loadFile(loaded, "test_save_output.xml");
            assertEquals("Save Test", loaded.getTitle());
        } catch (java.io.IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }
    }
}