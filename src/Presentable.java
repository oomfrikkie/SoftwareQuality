import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public interface Presentable {

    // Give the bounding box of this presentable item
    Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style);

    // Draw this presentable item
    void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer);
}
