# Jabberpoint

Jabberpoint is a Java-based slide presentation tool — think of it as a lightweight PowerPoint that runs straight from the command line or a JAR file. It was originally written by Ian F. Darwin and later extended by Gert Florijn and Sylvia Stuurman. This version is part of a Software Quality course at NHL Stenden University of Applied Sciences, where the codebase was refactored to apply several well-known design patterns and brought up to modern testing and CI/CD standards.

---

## What it does

You can load a presentation from an XML or JSON file and navigate through the slides using the keyboard or the menu bar. Each slide can contain text items at different indent levels and bitmap images. If you launch it without any arguments, it shows a built-in demo presentation so you can see it in action straight away.

---

## How to run it

Make sure you have Java 17 or later installed. The `org.json` library is needed for JSON support — it lives in the `lib/` folder.

**Demo mode (no file needed):**
```bash
java -cp "lib/json-20230618.jar:bin" JabberPoint
```

**Load an XML presentation:**
```bash
java -cp "lib/json-20230618.jar:bin" JabberPoint myfile.xml
```

**Load a JSON presentation:**
```bash
java -cp "lib/json-20230618.jar:bin" JabberPoint myfile.json
```

You can also just run the prebuilt JAR from the `production/` folder:
```bash
java -jar production/jabberpoint.jar
```

### Keyboard shortcuts

| Key | Action |
|-----|--------|
| → / Page Down / Enter | Next slide |
| ← / Page Up | Previous slide |
| Q | Quit |

---

## Project structure

```
src/         Source code
test/        JUnit unit and integration tests
acceptance/  Acceptance build artifacts
production/  Production-ready JAR and dependencies
lib/         Third-party libraries (JUnit, org.json)
```

---

## Design patterns

The refactoring effort focused on three classic GoF patterns, each solving a real problem in the original code:

### Composite
`Presentation` acts as the composite node and `SlideLeaf` acts as the leaf. Both implement the `Presentable` interface, so the rest of the application can treat a whole presentation and a single slide the same way. This makes it straightforward to add new types of slide content without touching the rendering logic.

### Observer
`Presentation` maintains a list of observers and calls `notifyObservers()` whenever the current slide changes. `SlideViewerComponent` registers itself as an observer so the view always stays in sync with the model — no manual refresh calls needed anywhere.

### Strategy
`Accessor` delegates all file I/O to a `PresentationFileHandler`. At startup (or when the user opens a file), the right strategy is picked based on the file extension: `XMLFileHandler` for `.xml` files and `JSONFileHandler` for `.json` files. Swapping formats is a one-liner — just call `setStrategy()`.

---

## Testing

The test suite lives in `test/` and uses JUnit 4. There are focused unit tests for each major component and a system integration test that exercises all three patterns together:

| Test class | What it covers |
|---|---|
| `AccessorTest` | Strategy switching and file loading |
| `XMLFileHandlerTest` | XML parsing and error handling |
| `ObserverPatternTest` | Observer registration and notification |
| `PresentationCompositeTest` | Composite add/remove/navigate |
| `TextItemTest` | Text item rendering logic |
| `SystemIntegrationTest` | End-to-end flow with Strategy + Composite + Observer |

Run all tests manually:
```bash
javac -cp "lib/json-20230618.jar:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:bin" -d bin test/*.java
java -Djava.awt.headless=true -cp "lib/json-20230618.jar:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:bin" \
  org.junit.runner.JUnitCore AccessorTest XMLFileHandlerTest ObserverPatternTest PresentationCompositeTest SystemIntegrationTest TextItemTest
```

---

## CI/CD pipeline (DTAP)

Every push and pull request to `develop`, `main`, or `production` runs through a four-stage GitHub Actions pipeline:

1. **Development** — compiles the source, runs Checkstyle (Google style), executes the full JUnit test suite, and packages a JAR artifact.
2. **Test** — picks up the JAR from the previous stage and runs integration checks.
3. **Acceptance** — runs acceptance checks against the tested artifact.
4. **Production** — deploys the accepted artifact to the production location.

Each stage only starts if the previous one passed, so a broken build never reaches production.

---

## License

This software is distributed under the terms described in [COPYRIGHT.txt](COPYRIGHT.txt). Please read it before using or redistributing the code. Your use of the software constitutes acceptance of those terms.
