package com.medusabookdepot.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.medusabookdepot.controller.DepotsController;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class DepotsControl extends ScreenControl {
	
	// Reference to the controller
	private final DepotsController depotsController = new DepotsController();
	
	// Aler panel to manage exceptions
    private final Alert alert = new Alert(AlertType.WARNING);
    
    // List of books & quantity to view in the table
    private final ObservableList<Entry<StandardBookImpl,Integer>> data = FXCollections.observableArrayList();
    
    // ToggleGroup to have just one toggleButton selected at a time
    private final ToggleGroup buttonsGroup = new ToggleGroup();
    private final List<ToggleButton> buttonsList = new ArrayList<>();
    
	public DepotsControl(){
		super();
		
		// Creating a button for each depot and adding such button to the group and to the list
		for(DepotImpl depot : depotsController.getDepots()){
			ToggleButton button = new ToggleButton(depot.getName());
			button.setToggleGroup(buttonsGroup);
			buttonsList.add(button);
			button.setUserData(depot.getName());
		}
		
		// Adding CSS to the Alert panel
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
	
	@FXML
	private Button delete;

	public void initialize() {
		
		// Adding the buttons created in the constructor to the hBox, after the title
		int hBoxPos=2; // hBoxPos is 2 because I need to add buttons after the title, which is in pos 1
		for(ToggleButton button : buttonsList){	
			hBox.getChildren().add(hBoxPos, button);
			hBoxPos++; // the other buttons will be added after the one I just added
		}
		
		// Initializing the table
        quantityColumn.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().titleProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().yearProperty().asString());
        pagesColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().yearProperty().asString());
        serieColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().serieProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().genreProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().authorProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().priceProperty().asString());
		
        // Method which handle the selection of a depot
        this.filter();
        
        // Selecting the first depot of the list for the first time the user opens the screen
        buttonsList.get(0).setSelected(true);
        
        // Putting data into the table
        depotsTable.setItems(data);
	}

	/**
	 * Called when the user selects a toggle-button, it filters depots so in the table there are only the books from the selected depot
	 */
	private void filter(){
		
		buttonsGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(newValue==null){
					// When there are no buttons toggled, the table is empty
					data.clear();
				} else{
					// The table is cleaned
					data.clear();
					// Data is added to the table, relatively to the toggle button selected
					data.addAll(depotsController.searchDepot((String)buttonsGroup.getSelectedToggle().getUserData()).findFirst().get().getBooks().entrySet());
				}
				update();
			}
		});
	}
	
	/**
	 * This method need to be called every time the user opens one of the depots 
	 * page to disable/enable the delete button 
	 * 
	 */
	private void update(){
		// Listen for selection changes and enable delete button
        delete.setDisable(true);
        depotsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        } );
	}
}
