import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Presentation maintains the slides in the presentation.</p>
 * <p>Acts as the Composite node in the Composite pattern:
 * it holds a collection of Presentable children (SlideLeaf instances) and
 * implements Presentable itself so it can be treated uniformly.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation implements Presentable {
	private String showTitle; // title of the presentation
	private List<Presentable> childs = null; // the slides as Presentable children
	private int currentSlideNumber = 0; // the slidenumber of the current slide
	private SlideViewerComponent slideViewComponent = null; // the viewcomponent of the slides

	public Presentation() {
		slideViewComponent = null;
		clear();
	}

	public Presentation(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
		clear();
	}

	public int getSize() {
		return childs.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}

	public void setShowView(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
	}

	// give the number of the current slide
	public int getSlideNumber() {
		return currentSlideNumber;
	}

	// change the current slide number and signal it to the window
	public void setSlideNumber(int number) {
		currentSlideNumber = number;
		if (slideViewComponent != null) {
			slideViewComponent.update(this, getCurrentSlide());
		}
	}

	// go to the previous slide unless at the beginning of the presentation
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
		}
	}

	// go to the next slide unless at the end of the presentation
	public void nextSlide() {
		if (currentSlideNumber < (childs.size() - 1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	// Delete the presentation to be ready for the next one
	void clear() {
		childs = new ArrayList<Presentable>();
		setSlideNumber(-1);
	}

	// Composite: add a child Presentable (e.g. a SlideLeaf)
	public void addChild(Presentable child) {
		childs.add(child);
	}

	// Composite: return the currently active child
	public Presentable getChild() {
		return getCurrentSlide();
	}

	// Composite: remove a child Presentable
	public void removeChild(Presentable child) {
		childs.remove(child);
	}

	// Get a slide with a certain slide number
	public SlideLeaf getSlide(int number) {
		if (number < 0 || number >= getSize()) {
			return null;
		}
		return (SlideLeaf) childs.get(number);
	}

	// Give the current slide
	public SlideLeaf getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	// Composite implementation: bounding box delegates to a single slide size
	@Override
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
		return new Rectangle(0, 0, (int)(SlideLeaf.WIDTH * scale), (int)(SlideLeaf.HEIGHT * scale));
	}

	// Composite implementation: draw delegates to the current slide
	@Override
	public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
		SlideLeaf current = getCurrentSlide();
		if (current != null) {
			current.draw(x, y, scale, g, style, observer);
		}
	}

	public void exit(int n) {
		System.exit(n);
	}
}
