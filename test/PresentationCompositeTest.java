import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Rectangle;

public class PresentationCompositeTest {

    @Test
    public void addChild_andGetSlide_shouldReturnCorrectSlides() {
        Presentation presentation = new Presentation();
        SlideLeaf slide1 = new SlideLeaf();
        slide1.setTitle("Slide 1");
        SlideLeaf slide2 = new SlideLeaf();
        slide2.setTitle("Slide 2");
        presentation.addChild(slide1);
        presentation.addChild(slide2);
        assertEquals(2, presentation.getSize());
        assertEquals("Slide 1", presentation.getSlide(0).getTitle());
        assertEquals("Slide 2", presentation.getSlide(1).getTitle());
    }

    @Test
    public void removeChild_givenSlide_shouldRemoveCorrectly() {
        Presentation presentation = new Presentation();
        SlideLeaf slide1 = new SlideLeaf();
        SlideLeaf slide2 = new SlideLeaf();
        presentation.addChild(slide1);
        presentation.addChild(slide2);
        presentation.removeChild(slide1);
        assertEquals(1, presentation.getSize());
        assertEquals(slide2, presentation.getSlide(0));
    }

    @Test
    public void append_givenItems_shouldAddToSlideLeaf() {
        SlideLeaf slide = new SlideLeaf();
        TextItem textItem = new TextItem(1, "Hello");
        BitmapItem bitmapItem = new BitmapItem(1, "image.png");
        slide.append(textItem);
        slide.append(bitmapItem);
        assertEquals(2, slide.getSize());
        assertEquals(textItem, slide.getSlideItem(0));
    }

    @Test
    public void setSlideNumber_givenIndex_shouldReturnCurrentSlide() {
        Presentation presentation = new Presentation();
        SlideLeaf slide1 = new SlideLeaf();
        SlideLeaf slide2 = new SlideLeaf();
        presentation.addChild(slide1);
        presentation.addChild(slide2);
        presentation.setSlideNumber(1);
        assertEquals(slide2, presentation.getCurrentSlide());
    }

    @Test
    public void getSlide_outOfBounds_shouldReturnNull() {
        Presentation presentation = new Presentation();
        assertNull(presentation.getSlide(-1));
        assertNull(presentation.getSlide(99));
    }

    @Test
    public void getSize_emptyPresentation_shouldReturnZero() {
        Presentation presentation = new Presentation();
        assertEquals(0, presentation.getSize());
    }

    @Test
    public void setTitle_andGetTitle_shouldMatchCorrectly() {
        Presentation presentation = new Presentation();
        presentation.setTitle("My Presentation");
        assertEquals("My Presentation", presentation.getTitle());
    }

    @Test
    public void clear_shouldResetAllSlides() {
        Presentation presentation = new Presentation();
        presentation.addChild(new SlideLeaf());
        presentation.addChild(new SlideLeaf());
        presentation.clear();
        assertEquals(0, presentation.getSize());
    }

    @Test
    public void appendLevelMessage_shouldAddTextItemToSlide() {
        SlideLeaf slide = new SlideLeaf();
        slide.append(1, "Test message");
        assertEquals(1, slide.getSize());
        assertTrue(slide.getSlideItem(0) instanceof TextItem);
    }

    @Test
    public void getSlideItems_shouldReturnAllItems() {
        SlideLeaf slide = new SlideLeaf();
        slide.append(new TextItem(1, "Item 1"));
        slide.append(new TextItem(2, "Item 2"));
        assertEquals(2, slide.getSlideItems().size());
    }

    @Test
    public void getScale_shouldReturnCorrectScale() {
        SlideLeaf slide = new SlideLeaf();
        Rectangle area = new Rectangle(0, 0, 600, 400);
        float scale = slide.getScale(area);
        assertEquals(0.5f, scale, 0.001f);
    }
}
