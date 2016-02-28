package com.medusabookdepot.view;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToolBar;

public class Menu {
	
	private final URL menuUrl = getClass().getResource("menu.fxml");
	private final ToolBar menu = FXMLLoader.load( menuUrl );
    
	public Menu() throws IOException{
		
	}
	/**
	 * Returns the menu
	 * @return menu
	 */
	public ToolBar getMenu(){
		return menu;
	}
}
