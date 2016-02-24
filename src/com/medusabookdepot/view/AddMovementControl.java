/**
 * 'movements.fxml' Controller Class
 */

package com.medusabookdepot.view;

import java.util.Optional;

import com.medusabookdepot.controller.MovementsController;
import com.medusabookdepot.model.modelImpl.TransferImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class AddMovementControl extends ScreenControl{
	
	private final MovementsController movementsController = MovementsController.getInstanceOf();
	private final ObservableList<TransferImpl> tempData = FXCollections.observableArrayList();
	
	@FXML
    private TextField searchField;
	
	@FXML
    private TableView<TransferImpl> movementsTable;
    @FXML
    private TableColumn<TransferImpl, String> quantityColumn;
    @FXML
    private TableColumn<TransferImpl, String> isbnColumn;
    @FXML
    private TableColumn<TransferImpl, String> titleColumn;
    @FXML
    private TableColumn<TransferImpl, String> senderColumn;
    @FXML
    private TableColumn<TransferImpl, String> receiverColumn;
    @FXML
    private TableColumn<TransferImpl, String> dateColumn;
    @FXML
    private TableColumn<TransferImpl, String> trackingColumn;
    
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> isbnBox;
    @FXML
    private ComboBox<String> titleBox;
    @FXML
    private ComboBox<String> senderBox;
    @FXML
    private ComboBox<String> receiverBox;
    @FXML
    private DatePicker dateField;
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

    	// test combo boxes
    	final ObservableList<String> isbns = FXCollections.observableArrayList();
    	final ObservableList<String> titles = FXCollections.observableArrayList();
    	isbns.add("1234");
    	isbns.add("9876");
    	titles.add("stanza");
    	titles.add("abbastanza");
    	AutoCompleteComboBoxListener<String> autoComplete;
    	
    	// Initialize the table
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asString());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());
        senderColumn.setCellValueFactory(cellData -> cellData.getValue().getSender().nameProperty());
        receiverColumn.setCellValueFactory(cellData -> cellData.getValue().getReceiver().nameProperty());
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLeavingDate().toString()));
        trackingColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTrackingNumber()));
        
        movementsTable.setItems(tempData); 
        
        titleBox.setItems(titles);
        autoComplete = new AutoCompleteComboBoxListener<String>(titleBox);
        
        isbnBox.setItems(isbns);
        
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
        alert.setContentText("Tracking Number: " + movementsTable.getSelectionModel().getSelectedItem().getNewTrackingNumber() 
        		+ "\nBook: " + movementsTable.getSelectionModel().getSelectedItem().getBook().getIsbn());
        alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();

        // When the user clicks ok, the selection gets deleted
        if (result.get() == ButtonType.OK) {
            int selectedIndex = movementsTable.getSelectionModel().getSelectedIndex();
            movementsController.removeMovement(movementsTable.getItems().get(selectedIndex));
        }
    }
    
}