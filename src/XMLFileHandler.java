public class XMLFileHandler implements PresentationFileHandler {
    @Override
    public void loadFile(Presentation p, String fn) {
        if (!fn.toLowerCase().endsWith(".xml")) {
            throw new IllegalArgumentException("Unsupported file type for XMLFileHandler: " + fn);
        }
        try {
            new XMLAccessor().loadFile(p, fn);
        } catch (Exception e) {
            System.err.println("Error loading XML: " + e.getMessage());
        }
    }

    @Override
    public void saveFile(Presentation p, String fn) {
        try {
            new XMLAccessor().saveFile(p, fn);
        } catch (Exception e) {
            System.err.println("Error saving XML: " + e.getMessage());
        }
    }
}
