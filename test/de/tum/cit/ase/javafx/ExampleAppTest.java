package de.tum.cit.ase.javafx;

import de.tum.in.test.api.jupiter.Public;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import org.junit.jupiter.api.Test;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static de.tum.cit.ase.javafx.HelperClass.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.testfx.api.FxAssert.verifyThat;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static org.testfx.matcher.control.LabeledMatchers.hasText;

@TestAnnotations
@Public
public class ExampleAppTest extends ApplicationTest {
	private static final String APP_CLASS_NAME = "de.tum.cit.ase.javafx.ExampleApp";
	private static final Logger LOG = LoggerFactory.getLogger(ExampleAppTest.class);

	private Button button;
	private TextField textField;
	private Label label;

	@Override
	public void start(Stage stage) {
		Object app = createInstance(APP_CLASS_NAME, null);
		invokeMethod(app, "start", false, new Class<?>[]{Stage.class}, stage);

		var buttonOptional = lookup(".button").tryQueryAs(Button.class);
		if (buttonOptional.isEmpty()) {
			fail("Button not found");
		}
		button = buttonOptional.get();

		var textFieldOptional = lookup(".text-field").tryQueryAs(TextField.class);
		if (textFieldOptional.isEmpty()) {
			fail("TextField not found");
		}
		textField = textFieldOptional.get();

		var labelOptional = lookup(".label").tryQueryAs(Label.class);
		if (labelOptional.isEmpty()) {
			fail("Label not found");
		}
		label = labelOptional.get();
	}

	@Test
	public void testNoInput() {
		assertEquals("Enter text here", textField.getPromptText());

		verifyThat(button, hasText("Count Characters"));
		verifyThat(label, hasText("Character count: 0"));

		clickOn(button);

		verifyThat(label, hasText("Character count: 0"));
	}

	@Test
	public void testInput() {
		String input = "Hello, World!";

		clickOn(textField).write(input);
		clickOn(button);
		verifyThat(label, hasText("Character count: " + input.length()));

        clickOn(textField).eraseText(input.length());

        clickOn(textField).write("a");
        clickOn(button);
        verifyThat(label, hasText("Character count: 1"));

		clickOn(textField).eraseText(1);

		clickOn(textField).write("");
		clickOn(button);
		verifyThat(label, hasText("Character count: 0"));
	}

	private void captureAndSaveScreenshot(String testCaseName) {
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
