package com.medusabookdepot.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class DepotsControl extends ScreenControl implements Initializable{
	
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
	
	
	public DepotsControl(){
		super();
	}
	
	/**
	 * Enter a "dinamic" quantity of buttons in the fxml code, for example, depending on the list of depots
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(int i=0; i<1; i++){
			hBox.getChildren().add(i+1, new Button("PDE"));
			hBox.getChildren().add(i+2, new Button("MEDUSA"));
		}
	}
	
}
