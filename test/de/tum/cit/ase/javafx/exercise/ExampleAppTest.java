package de.tum.cit.ase.javafx.exercise;

import de.tum.cit.ase.eos.JavaFXTest;
import de.tum.cit.ase.eos.TestFXMacAnnotations;
import de.tum.in.test.api.MirrorOutput;
import de.tum.in.test.api.StrictTimeout;
import de.tum.in.test.api.WhitelistClass;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@WhitelistClass(ExampleAppTest.class)
@StrictTimeout(10)
@MirrorOutput
@TestFXMacAnnotations
public class ExampleAppTest extends JavaFXTest {
	private Button button;
	private TextField textField;
	private Label label;

	@Override
	public String getAppClassName() {
		return "de.tum.cit.ase.javafx.exercise.ExampleApp";
	}

	@BeforeEach
	public void initializeObjects() {
		button = getNodeOfType(Button.class, ".button");
		textField = getNodeOfType(TextField.class, ".text-field");
		label = getNodeOfType(Label.class, ".label");
	}

	@Test
	public void testInput() {
		String input = "Hello World";

		clickOn(textField).write(input);
		clickOn(button);
		verifyThat(label, hasText("Character count: " + input.length()));
	}
}
