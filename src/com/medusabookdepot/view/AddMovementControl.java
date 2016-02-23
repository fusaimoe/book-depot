/**
 * 'movements.fxml' Controller Class
 */

package com.medusabookdepot.view;

import java.util.Optional;
import java.util.Map.Entry;

import com.medusabookdepot.controller.MovementsController;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelImpl.TransferImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class AddMovementControl extends ScreenControl{
	
	private final MovementsController movementsController = new MovementsController();
	
	@FXML
    private TextField searchField;
	
	@FXML
    private TableView<Entry<StandardBookImpl,Integer>> movementsTable;
    @FXML
    private TableColumn<Entry<StandardBookImpl,Integer>, String> quantityColumn;
    @FXML
    private TableColumn<Entry<StandardBookImpl, Integer>, String> isbnColumn;
    @FXML
    private TableColumn<Entry<StandardBookImpl, Integer>, String> titleColumn;
    @FXML
    private TableColumn<Entry<StandardBookImpl,Integer>, String> senderColumn;
    @FXML
    private TableColumn<Entry<StandardBookImpl,Integer>, String> receiverColumn;
    @FXML
    private TableColumn<Entry<StandardBookImpl,Integer>, String> dateColumn;
    @FXML
    private TableColumn<Entry<StandardBookImpl,Integer>, String> trackingColumn;
    
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> isbnField;
    @FXML
    private ComboBox<String> titleField;
    @FXML
    private TextField senderField;
    @FXML
    private TextField receiverField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField trackingField;
    
    @FXML
    private Button delete;
    
    public AddMovementControl(){
		super();
	}

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    	final ObservableList<Entry<StandardBookImpl,Integer>> data = FXCollections.observableArrayList();
    	// test combo boxes
    	final ObservableList<String> isbns = FXCollections.observableArrayList();
    	final ObservableList<String> titles = FXCollections.observableArrayList();
    	isbns.add("1234");
    	isbns.add("9876");
    	titles.add("prova");
    	titles.add("test");
    	
        // Initialize the table
		quantityColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().titleProperty());
        
        movementsTable.setItems(data); 
        
        titleField.setItems(titles);
        isbnField.setItems(isbns);
        
        // Listen for selection changes and enable delete button
        delete.setDisable(true);
        movementsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        } );
        
    }
    
    /**
     * Called when the user add a new book
     */
    @FXML
    private void add(){
    	
    }
    
    /**
     * Called when the user add a new book
     */
    @FXML
    private void delete(){
    	 // On delete button press, opens a confirmation dialog asking if you
        // really want to delete
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you really want to delete the following element?");
        alert.setContentText("Tracking Number: " + movementsController.getTransferFromBook(movementsTable.getSelectionModel().getSelectedItem().getKey()).getTrackingNumber() 
        		+ "\nBook: " + movementsTable.getSelectionModel().getSelectedItem().getKey().getTitle());
        alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();

        // When the user clicks ok, the selection gets deleted
        if (result.get() == ButtonType.OK) {
            int selectedIndex = movementsTable.getSelectionModel().getSelectedIndex();
            movementsController.removeMovement(movementsController.getTransferFromBook(movementsTable.getItems().get(selectedIndex).getKey()));
            movementsTable.getItems().remove(selectedIndex);
        }
    }
    
}