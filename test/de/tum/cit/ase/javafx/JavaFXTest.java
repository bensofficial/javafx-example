package de.tum.cit.ase.javafx;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit5.ApplicationTest;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static de.tum.cit.ase.javafx.HelperClass.createInstance;
import static de.tum.cit.ase.javafx.HelperClass.invokeMethod;

@TestFXAnnotations
@TestFXMacAnnotations
public abstract class JavaFXTest extends ApplicationTest {

    private static final Logger LOG = LoggerFactory.getLogger(JavaFXTest.class);

    public abstract String getAppClassName();

    @Override
    public final void start(Stage stage) {
        Object app = createInstance(getAppClassName(), null);
        invokeMethod(app, "start", false, new Class<?>[]{Stage.class}, stage);
    }

    protected void captureAndSaveScreenshot(String testCaseName) {
        Scene scene = robotContext().getWindowFinder().listWindows().get(0).getScene();
        Bounds bounds = scene.getRoot().localToScreen(scene.getRoot().getBoundsInLocal());
        Rectangle2D region = new Rectangle2D(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
        Image screenshot = robotContext().getCaptureSupport().captureRegion(region);

        saveScreenshot(testCaseName, screenshot);
    }

    private void saveScreenshot(String testCaseName, Image screenshot) {
        final String screenshotPath = "screenshots";
        final String fileExtension = "png";

        File screenshotFile = new File(screenshotPath + "/" + normalizeFileName(testCaseName, fileExtension));
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(screenshot, null), fileExtension, screenshotFile);
        } catch (IOException e) {
            LOG.error("Failed to save screenshot", e);
        }
    }

    private String normalizeFileName(String fileName, String fileExtension) {
        fileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        return fileName.endsWith("." + fileExtension) ? fileName : fileName + "." + fileExtension;
    }
}
