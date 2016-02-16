package com.medusabookdepot.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class DepotsControl extends ScreenControl implements Initializable{
	
	public DepotsControl(){
		super();
	}
	
	@FXML
	private HBox hBox;
	@FXML
	private TableView<StandardBookImpl> depotsTable;
	@FXML
	private TableColumn<StandardBookImpl, String> quantityColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> isbnColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> titleColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> yearColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> pagesColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> serieColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> genreColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> authorColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> priceColumn;
	
	/**
	 * Enter a "dinamic" quantity of buttons in the fxml code, for example, depending on the list of depots
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(int i=2; i<6; i++){
			ToggleButton button = new ToggleButton("TOGGLE" + (i-1));
			hBox.getChildren().add(i, button);
		}
	}
	
}
