/**
 * 'movements.fxml' Controller Class
 */

package com.medusabookdepot.view;

import java.time.ZoneId;
import java.util.Optional;

import com.medusabookdepot.controller.MovementsController;
import com.medusabookdepot.model.modelImpl.TransferImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class MovementsControl extends ScreenControl{
	
	private final MovementsController movementsController = MovementsController.getInstanceOf();
	
	@FXML
    private TableView<TransferImpl> movementsTable;
    @FXML
    private TableColumn<TransferImpl, String> quantityColumn, isbnColumn, titleColumn, senderColumn, 
		receiverColumn, dateColumn, totalPriceColumn, trackingColumn;
    
    @FXML
    private Button delete;
	@FXML
    private TextField searchField;
	
	public MovementsControl(){
		super();
	}

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
        // Initialize the table
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asString());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());
        senderColumn.setCellValueFactory(cellData -> cellData.getValue().getSender().nameProperty());
        receiverColumn.setCellValueFactory(cellData -> cellData.getValue().getReceiver().nameProperty());
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLeavingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString()));
        trackingColumn.setCellValueFactory(cellData -> cellData.getValue().trackingNumberProperty());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asString());
	
        movementsTable.setItems(movementsController.getMovements()); 
        
        this.update();
        this.search();
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
        alert.setContentText("Tracking Number: " + movementsTable.getSelectionModel().getSelectedItem().getTrackingNumber() 
        		+ "\nBook: " + movementsTable.getSelectionModel().getSelectedItem().getBook().getIsbn());
        alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();

        // When the user clicks ok, the selection gets deleted
        if (result.get() == ButtonType.OK) {
            int selectedIndex = movementsTable.getSelectionModel().getSelectedIndex();
            movementsController.removeMovement(movementsTable.getItems().get(selectedIndex));
        }
    }
    
    /**
	 * Method to disable/enable the delete button
	 * 
	 */
	private void update(){
		// Listen for selection changes and enable delete button
        delete.setDisable(true);
        movementsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        } );
	}
	
	/**
     * Called when the user enter something in the search field
     */
    private void search(){
    	searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (!newValue.isEmpty()){
		        movementsTable.setItems(FXCollections.observableArrayList(movementsController.searchMovements(newValue)));
        	}else movementsTable.setItems(movementsController.getMovements());
        });
    }
}