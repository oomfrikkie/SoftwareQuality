import org.junit.Test;
import static org.junit.Assert.*;

public class ObserverPatternTest {

    static class TestObserver implements Observer {
        int updateCount = 0;
        Presentation lastPresentation = null;

        public void update(Presentation p) {
            updateCount++;
            lastPresentation = p;
        }
    }

    @Test
    public void addObserver_shouldBeNotifiedOnSlideChange() {
        Presentation p = new Presentation();
        TestObserver obs = new TestObserver();
        p.addObserver(obs);
        p.addChild(new SlideLeaf());
        p.setSlideNumber(0);
        assertEquals(1, obs.updateCount);
    }

    @Test
    public void removeObserver_shouldNotBeNotified() {
        Presentation p = new Presentation();
        TestObserver obs = new TestObserver();
        p.addObserver(obs);
        p.removeObserver(obs);
        p.addChild(new SlideLeaf());
        p.setSlideNumber(0);
        assertEquals(0, obs.updateCount);
    }

    @Test
    public void multipleObservers_allShouldBeNotified() {
        Presentation p = new Presentation();
        TestObserver obs1 = new TestObserver();
        TestObserver obs2 = new TestObserver();
        p.addObserver(obs1);
        p.addObserver(obs2);
        p.setSlideNumber(0);
        assertEquals(1, obs1.updateCount);
        assertEquals(1, obs2.updateCount);
    }

    @Test
    public void notifyObservers_shouldPassCorrectPresentation() {
        Presentation p = new Presentation();
        TestObserver obs = new TestObserver();
        p.addObserver(obs);
        p.setSlideNumber(0);
        assertEquals(p, obs.lastPresentation);
    }

    @Test
    public void nextSlide_shouldNotifyObservers() {
        Presentation p = new Presentation();
        TestObserver obs = new TestObserver();
        p.addObserver(obs);
        p.addChild(new SlideLeaf());
        p.addChild(new SlideLeaf());
        p.setSlideNumber(0);
        p.nextSlide();
        assertEquals(2, obs.updateCount);
    }

    @Test
    public void prevSlide_shouldNotifyObservers() {
        Presentation p = new Presentation();
        TestObserver obs = new TestObserver();
        p.addObserver(obs);
        p.addChild(new SlideLeaf());
        p.addChild(new SlideLeaf());
        p.setSlideNumber(1);
        p.prevSlide();
        assertEquals(2, obs.updateCount);
    }

    @Test
    public void prevSlide_atFirstSlide_shouldNotNotify() {
        Presentation p = new Presentation();
        TestObserver obs = new TestObserver();
        p.addObserver(obs);
        p.addChild(new SlideLeaf());
        p.setSlideNumber(0);
        p.prevSlide();
        assertEquals(1, obs.updateCount);
    }

    @Test
    public void nextSlide_atLastSlide_shouldNotNotify() {
        Presentation p = new Presentation();
        TestObserver obs = new TestObserver();
        p.addObserver(obs);
        p.addChild(new SlideLeaf());
        p.setSlideNumber(0);
        p.nextSlide();
        assertEquals(1, obs.updateCount);
    }

    @Test
    public void noObservers_setSlideNumber_shouldNotCrash() {
        Presentation p = new Presentation();
        p.addChild(new SlideLeaf());
        p.setSlideNumber(0);
        assertEquals(0, p.getSlideNumber());
    }

    @Test
    public void addSameObserverTwice_shouldBeNotifiedTwicePerChange() {
        Presentation p = new Presentation();
        TestObserver obs = new TestObserver();
        p.addObserver(obs);
        p.addObserver(obs);
        p.setSlideNumber(0);
        assertEquals(2, obs.updateCount);
    }

    @Test
    public void addNullObserver_shouldNotCrash() {
        Presentation p = new Presentation();
        p.addObserver(null);
        p.addChild(new SlideLeaf());
        p.setSlideNumber(0);
        assertEquals(0, p.getSlideNumber());
    }
}
