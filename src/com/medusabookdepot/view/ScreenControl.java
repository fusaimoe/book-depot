/**
 * Switch Screen Controller Class, inherited by all fxml files
 */

package com.medusabookdepot.view;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 */
public class ScreenControl extends GUI{
	
	@FXML
	private Button movements, addMovement, depots, books, customers, addCustomer, addBook, addDepot, statistics, email;
	
	@FXML
	private VBox vBoxFields;
	
	@FXML
	public void switchScreen(ActionEvent event) {
		try {
			
			URL paneMovementsUrl = getClass().getResource(((Control)event.getSource()).getId()+".fxml"); //*((Control)event.getSource()).getId()* get button name
			ScrollPane paneMovements = FXMLLoader.load( paneMovementsUrl );

			this.getMainPane().setCenter(paneMovements);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to clear all the textFields after add button has been pressed
	 */
    @SuppressWarnings("rawtypes")
	public void clear(){
    	for(Node node: vBoxFields.getChildren()){
    		if (node instanceof TextField) {
    	        ((TextField)node).clear();
    	    }
    		if (node instanceof ChoiceBox) {
    	        ((ChoiceBox)node).getSelectionModel().clearSelection();
    	    }
    		if (node instanceof ComboBox) {
    	        ((ComboBox)node).getSelectionModel().clearSelection();
    	    }
    	}
    }
}