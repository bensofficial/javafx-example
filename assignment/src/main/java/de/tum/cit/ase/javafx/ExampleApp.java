package de.tum.cit.ase.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;

import java.net.URL;

import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.geometry.Pos;

public class ExampleApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField textField = new TextField();
        textField.setPromptText("Enter text here");

        Label charCountLabel = new Label("Character count: 0");
        Button countButton = new Button("Count Characters");

        countButton.setOnAction(event -> {
            charCountLabel.setText("Character count: " + textField.getText().length());
        });

        VBox root = new VBox(10, textField, countButton, charCountLabel);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Character Counter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
