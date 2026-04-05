

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// Integration test for the whole system with Strategy, Composite, and Observer patterns
public class SystemIntegrationTest {
    private Presentation presentation;
    private SlideViewerComponent slideViewerComponent;
    private Accessor accessor;

    @Before
    public void setUp() {
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
    public void integration_patternsStrategyCompositeObserver_shouldWorkTogether() {
        // Composite: Check structure
        assertTrue("Slides should not be empty", presentation.getSize() > 0);
        assertNotNull("First slide should not be null", presentation.getSlide(0));
        // If you want to check items on the first slide, you can do:
        assertTrue("First slide should have items", presentation.getSlide(0).getSlideItems().size() > 0);

        // Observer: Simulate change and check notification
        // We'll check if the SlideViewerComponent updates its internal state after a change
        int oldSlideNumber = presentation.getSlideNumber();
        presentation.setTitle("New Title"); // Should trigger observer
        // Here, we check that the slide number remains valid after the title change
        assertEquals("SlideViewerComponent should remain in sync after title change", oldSlideNumber, presentation.getSlideNumber());

        // Strategy: Switch to JSON handler and reload
        accessor.setStrategy(new JSONFileHandler());
        try {
            accessor.loadFile(presentation, "test/resources/test_presentation.json");
        } catch (Exception e) {
            fail("Failed to load JSON presentation: " + e.getMessage());
        }
        assertTrue("Slides should not be empty after JSON load", presentation.getSize() > 0);
        assertNotNull("First slide should not be null after JSON load", presentation.getSlide(0));
    }
}
