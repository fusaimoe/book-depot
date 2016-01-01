package com.medusabookdepot.view.viewImpl;
import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Menu extends Application {

	public Menu (){
		
		
	}
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		primaryStage.setTitle("Medusa");
		primaryStage.setScene(new Scene(root, screenSize.getWidth(), screenSize.getHeight()));
		root.getStylesheets().add("/materialDesign.css");
		primaryStage.show();
	}

}
