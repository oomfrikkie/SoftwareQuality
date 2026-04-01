import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

/** <p>A slide. This class has drawing functionality.</p>
 * <p>Acts as the Leaf node in the Composite pattern:
 * it holds a collection of Presentable items and implements
 * Presentable itself so it can be treated uniformly.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideLeaf implements Presentable {
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;
	protected String title;
	private Vector<Presentable> slideItems;

	public SlideLeaf() {
		slideItems = new Vector<Presentable>();
	}

	// Add a presentable item to this slide
	public void append(Presentable anItem) {
		slideItems.addElement(anItem);
	}

	// Give the title of the slide
	public String getTitle() {
		return title;
	}

	// Change the title of the slide
	public void setTitle(String newTitle) {
		title = newTitle;
	}

	// Create a TextItem from a String and add it
	public void append(int level, String message) {
		append(new TextItem(level, message));
	}

	// Give a specific slide item by index
	public Presentable getSlideItem(int number) {
		return slideItems.elementAt(number);
	}

	// Give all slide items
	public Vector<Presentable> getSlideItems() {
		return slideItems;
	}

	// Give the number of items in this slide
	public int getSize() {
		return slideItems.size();
	}

	// Remove a presentable item from this slide
	public void remove(Presentable item) {
		slideItems.remove(item);
	}

	// Draw the slide in the given area
	public void draw(Graphics g, Rectangle area, ImageObserver view) {
		float scale = getScale(area);
		int y = area.y;
		Presentable titleItem = new TextItem(0, getTitle());
		Style style = Style.getStyle(0);
		titleItem.draw(area.x, y, scale, g, style, view);
		y += titleItem.getBoundingBox(g, view, scale, style).height;
		for (int number = 0; number < getSize(); number++) {
			Presentable item = slideItems.elementAt(number);
			int level = (item instanceof SlideItem) ? ((SlideItem) item).getLevel() : 0;
			style = Style.getStyle(level);
			item.draw(area.x, y, scale, g, style, view);
			y += item.getBoundingBox(g, view, scale, style).height;
		}
	}

	// Leaf implementation: draw this slide when used as a Presentable
	@Override
	public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
		Rectangle area = new Rectangle(x, y, (int)(WIDTH * scale), (int)(HEIGHT * scale));
		draw(g, area, observer);
	}

	// Leaf implementation: bounding box when used as a Presentable
	@Override
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
		return new Rectangle(0, 0, (int)(WIDTH * scale), (int)(HEIGHT * scale));
	}

	// Give the scale for drawing
	public float getScale(Rectangle area) {
		return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
	}
}
