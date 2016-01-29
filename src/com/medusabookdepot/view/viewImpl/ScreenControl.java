/**
 * Switch Screen Controller Class, inherited by all fxml files
 */

package com.medusabookdepot.view.viewImpl;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

/**
 * Note that we load the panes with the FXMLLoader
 * on every use. This allows us to manipulate the
 * CSS between loads and have it take affect. 
 * 
 * Also, the panes should not save state internally.
 * Reloading the FXML forces better programming
 * design, because it is impossible to get lazy
 * and expect the panes to save their own state.
 */
public class ScreenControl {
	
	@FXML
	private Button movements;
	@FXML
	private Button addMovement;
	@FXML
	private Button depots;
	@FXML
	private Button books;
	@FXML
	private Button customers;
	@FXML
	private Button addCustomer;
	//private Button search;
	//private Button stats;
	//private Button more;

	@FXML
	void switchScreen(ActionEvent event) {
		try {
			
			URL paneMovementsUrl = getClass().getResource(((Control)event.getSource()).getId()+".fxml"); //*((Control)event.getSource()).getId()* get button name
			ScrollPane paneMovements = FXMLLoader.load( paneMovementsUrl );
      
			BorderPane border = Menu.getRoot();
			border.setCenter(paneMovements);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}