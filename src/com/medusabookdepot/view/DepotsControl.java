package com.medusabookdepot.view;

import java.util.Map.Entry;

import com.medusabookdepot.controller.DepotsController;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

public class DepotsControl extends ScreenControl {
	
	private final DepotsController depotsController = new DepotsController();
    private final Alert alert = new Alert(AlertType.WARNING);
    
	public DepotsControl(){
		super();
		alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());
	}
	
	@FXML
	private HBox hBox;
	@FXML
	private TableView<Entry<StandardBookImpl,Integer>> depotsTable;

	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> quantityColumn;
	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> isbnColumn;
	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> titleColumn;
	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> yearColumn;
	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> pagesColumn;
	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> serieColumn;
	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> genreColumn;
	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> authorColumn;
	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> priceColumn;
	@FXML
	private TextField nameField;
	@FXML
	private TextField searchField;

	public void initialize() {
		
		final ObservableList<Entry<StandardBookImpl,Integer>> data = FXCollections.observableArrayList();
		
		int i=2;
		for(DepotImpl depot : depotsController.getDepots()){
			ToggleButton button = new ToggleButton(depot.getName());
			hBox.getChildren().add(i, button);
			i++;
		}
		
        quantityColumn.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().titleProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().yearProperty().asString());
        pagesColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().yearProperty().asString());
        serieColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().serieProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().genreProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().authorProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().priceProperty().asString());
		
        //Lo zero serve per indicare di quale depot deve mostrare i libri. In questo caso mostra solo i libri del depot  
        // che si trova in posizione 0 della lista. Ovvero il primo depot che ho aggiunto. 
        //Lo zero dovrà essere modificato in modo tale che mostri solo i libri del depot indicato dal bottone che l'utente
        //ha premuto. Al momento non ho idee.
        data.addAll(depotsController.getDepots().get(0).getBooks().entrySet());
        
        depotsTable.setItems(data);
	}

}
