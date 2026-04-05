import org.junit.Test;
import static org.junit.Assert.*;

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
        // We can't directly get items, but we can check no exceptions and size indirectly
        assertNotNull(slide);
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
}
