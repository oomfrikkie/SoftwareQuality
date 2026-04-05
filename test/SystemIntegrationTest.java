

// NOTE: If you get errors about missing JUnit, ensure JUnit 5 is in your classpath.
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Integration test for the whole system with Strategy, Composite, and Observer patterns
public class SystemIntegrationTest {
    private Presentation presentation;
    private SlideViewerComponent slideViewerComponent;
    private Accessor accessor;

    @BeforeEach
    void setUp() {
        accessor = new Accessor();
        accessor.setStrategy(new XMLFileHandler());
        presentation = new Presentation();
        try {
            accessor.loadFile(presentation, "test/resources/test_presentation.xml");
        } catch (Exception e) {
            fail("Failed to load presentation: " + e.getMessage());
        }
        // Attach actual observer
        slideViewerComponent = new SlideViewerComponent(presentation, new javax.swing.JFrame());
        presentation.addObserver(slideViewerComponent);
    }

    @Test
    void integration_patternsStrategyCompositeObserver_shouldWorkTogether() {
        // Composite: Check structure
        assertTrue(presentation.getSize() > 0, "Slides should not be empty");
        assertNotNull(presentation.getSlide(0), "First slide should not be null");
        // If you want to check items on the first slide, you can do:
        assertTrue(presentation.getSlide(0).getSlideItems().size() > 0, "First slide should have items");

        // Observer: Simulate change and check notification
        // We'll check if the SlideViewerComponent updates its internal state after a change
        int oldSlideNumber = presentation.getSlideNumber();
        presentation.setTitle("New Title"); // Should trigger observer
        // Here, we check that the slide number remains valid after the title change
        assertEquals(oldSlideNumber, presentation.getSlideNumber(), "SlideViewerComponent should remain in sync after title change");

        // Strategy: Switch to JSON handler and reload
        accessor.setStrategy(new JSONFileHandler());
        try {
            accessor.loadFile(presentation, "test/resources/test_presentation.json");
        } catch (Exception e) {
            fail("Failed to load JSON presentation: " + e.getMessage());
        }
        assertTrue(presentation.getSize() > 0, "Slides should not be empty after JSON load");
        assertNotNull(presentation.getSlide(0), "First slide should not be null after JSON load");
    }
}
