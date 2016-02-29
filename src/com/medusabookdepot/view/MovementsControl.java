/**
 * 'movements.fxml' Control Class
 */

package com.medusabookdepot.view;

import java.time.ZoneId;
import java.util.Optional;

import com.medusabookdepot.controller.MovementsController;
import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class MovementsControl extends ScreenControl{
	
	// Reference to the controller
	private final MovementsController movementsController = MovementsController.getInstanceOf();
	
	// Aler panel to manage exceptions
	private final AlertTypes alert = new AlertTypesImpl();

	@FXML
    private TableView<TransferImpl> movementsTable;
	
    @FXML
    private TableColumn<TransferImpl, String> quantityColumn, isbnColumn, titleColumn, senderColumn, 
		receiverColumn, dateColumn, totalPriceColumn, trackingColumn, typeColumn;
    
    @FXML
    private Button delete;
	@FXML
    private TextField searchField;
	
	public MovementsControl(){
		super();
	}
		
	/**
     * Called after the fxml file has been loaded.
     * Method to initializes the control class. 
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
        totalPriceColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(movementsController.convertPriceToString
        		(cellData.getValue().getTotalPrice())));
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
	
        movementsTable.setItems(movementsController.getMovements()); 
        
        // Listen for selection changes and enable delete button
        this.update();
        
        // Use a 'searchField' to search for books in the tableView
        this.search();
    }
 
    
    /**
     * On delete button press, opens a confirmation dialog asking if you 
     * really want to delete the element is passed 
     */
    @FXML
    private void delete() {
        Optional<ButtonType> result = alert.showConfirmation("Tracking Number: " + movementsTable.getSelectionModel().getSelectedItem().getTrackingNumber()
        		+ "\nBook: " + movementsTable.getSelectionModel().getSelectedItem().getBook().getIsbn());

        // When the user clicks ok, the selection gets deleted
        if (result.get() == ButtonType.OK) {
            int selectedIndex = movementsTable.getSelectionModel().getSelectedIndex();
            movementsController.removeMovement(movementsTable.getItems().get(selectedIndex));
        }
    }
    
    /**
	 * Method to disable/enable the delete button when something has been selected from the user
	 */
	private void update() {
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