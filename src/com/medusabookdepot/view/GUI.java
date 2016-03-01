package com.medusabookdepot.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUI extends Application implements GuiInterface {

	private static BorderPane root = new BorderPane();
	
	public void start(Stage primaryStage) throws IOException {
		
		Menu menu = new Menu();
		
		// Loading the custom font before loading the files which use it
		Font.loadFont(getClass().getResource("/Roboto-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("/Roboto-Bold.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("/Roboto-Medium.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("/Roboto-Black.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("/Roboto-Light.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("/Material-Design-Iconic-Font.ttf").toExternalForm(), 10);

		// Loading the movements pane, which will be the welcome page, as it's the most used one
	    URL movementsUrl = getClass().getResource("movements.fxml");
	    ScrollPane movements = FXMLLoader.load( movementsUrl );
	
	    // Constructing our scene using the static root
	    root.setTop(menu.getMenu());
	    root.setCenter(movements);
	    
	    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    
	    Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
	    scene.getStylesheets().add(getClass().getResource("/materialDesign.css").toExternalForm());
	    
	    primaryStage.setTitle("book-depot");
	    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
	    primaryStage.setScene(scene);
	    primaryStage.show();

	}
	
	public BorderPane getMainPane(){
		return root;
	}
	
	public void showPanel(String[] args) {
		launch(args);
	}

}