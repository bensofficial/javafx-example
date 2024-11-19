module ExampleApp {
	exports de.tum.cit.ase.javafx;

	requires javafx.controls;
	requires javafx.graphics;

	opens de.tum.cit.ase.javafx to javafx.controls, javafx.graphics;
}
