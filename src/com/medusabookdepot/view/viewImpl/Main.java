package com.medusabookdepot.view.viewImpl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
  
	// Creating a static root to pass to the controller
	private static BorderPane root = new BorderPane();

	/**
	 * Just a root getter used by the controller
	 */
	public static BorderPane getRoot() {
		return root;
	}
	
	public void start(Stage primaryStage) throws IOException {
		
		// Loading the custom font before loading the files which use it
		Font.loadFont(Main.class.getResource("/Roboto-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(Main.class.getResource("/Roboto-Bold.ttf").toExternalForm(), 10);
		Font.loadFont(Main.class.getResource("/Roboto-Medium.ttf").toExternalForm(), 10);
		Font.loadFont(Main.class.getResource("/Roboto-Black.ttf").toExternalForm(), 10);
		Font.loadFont(Main.class.getResource("/Roboto-Light.ttf").toExternalForm(), 10);
		Font.loadFont(Main.class.getResource("/Material-Design-Iconic-Font.ttf").toExternalForm(), 10);
				
	    // Loading the main pane
	    URL menuUrl = getClass().getResource("menu.fxml");
	    ToolBar menu = FXMLLoader.load( menuUrl );
	
	    // Loading the movements pane, which will be the welcome page, as it's the most used one
	    URL movementsUrl = getClass().getResource("movements.fxml");
	    ScrollPane movements = FXMLLoader.load( movementsUrl );
	
	    // Constructing our scene using the static root
	    root.setTop(menu);
	    root.setCenter(movements);
	    
	    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    
	    Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
	    scene
	      .getStylesheets()
	      .add(getClass()
	      .getResource("materialDesign.css")
	      .toExternalForm());
	    
	    primaryStage.setScene(scene);
	    primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
}