import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
// Import SlideLeaf, TextItem, BitmapItem, Presentable
import java.util.*;

public class JSONFileHandler implements PresentationFileHandler {
    @Override
    public void loadFile(Presentation p, String fn) {
        if (fn == null || !fn.toLowerCase().endsWith(".json")) {
            throw new IllegalArgumentException("Only .json files are supported by JSONFileHandler");
        }
        try {
            String content = new String(Files.readAllBytes(Paths.get(fn)));
            JSONObject obj = new JSONObject(content);
            p.setTitle(obj.optString("title", ""));
            JSONArray slides = obj.optJSONArray("slides");
            if (slides != null) {
                for (int i = 0; i < slides.length(); i++) {
                    JSONObject slideObj = slides.getJSONObject(i);
                    SlideLeaf slide = new SlideLeaf();
                    slide.setTitle(slideObj.optString("title", ""));
                    JSONArray items = slideObj.optJSONArray("items");
                    if (items != null) {
                        for (int j = 0; j < items.length(); j++) {
                            JSONObject itemObj = items.getJSONObject(j);
                            String kind = itemObj.optString("kind", "text");
                            int level = itemObj.optInt("level", 1);
                            if ("text".equals(kind)) {
                                String text = itemObj.optString("content", "");
                                slide.append(new TextItem(level, text));
                            } else if ("image".equals(kind)) {
                                String name = itemObj.optString("content", "");
                                slide.append(new BitmapItem(level, name));
                            }
                        }
                    }
                    p.addChild(slide);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading JSON: " + e.getMessage());
        }
    }

    @Override
    public void saveFile(Presentation p, String fn) {
        // Stub: JSON saving not implemented yet
        System.out.println("JSON saving not implemented yet");
    }
}
