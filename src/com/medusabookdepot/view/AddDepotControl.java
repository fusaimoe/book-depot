/**
 * 'addDepot.fxml' Control Class
 */

package com.medusabookdepot.view;

import java.util.Optional;

import com.medusabookdepot.controller.DepotsController;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class AddDepotControl extends ScreenControl {
	
	// Reference to the controller
	private final DepotsController depotsController = DepotsController.getInstanceOf();
	
	// Aler panel to manage exceptions
    private final AlertTypes alert = new AlertTypesImpl();
    
	public AddDepotControl(){
		super();
	}
	
	@FXML
	private TableView<DepotImpl> depotsTable;
	
	@FXML
	private TableColumn<DepotImpl, String> nameColumn;
	
	@FXML
	private TextField nameField;
	@FXML
	private Button delete;
    @FXML
    private TextField searchField;
    
    /**
     * Called after the fxml file has been loaded.
     * Method to initializes the control class. 
     */
    public void initialize() {
    	
    	// Initialize the table
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        
        // Add observable list data to the table
        depotsTable.setItems(depotsController.getDepots());
        
        // Make the table columns editable by double clicking
        this.edit();
        
        // Use a 'searchField' to search for books in the tableView
        this.search();
        
        // Listen for selection changes and enable delete button
        this.update();
    }
    
    /**
     * Called when the user press the 'add' button 
     * Method to add a new depot to the controller ObservableList of depots
     */
	@FXML
    private void add() {
        try {
           depotsController.addDepot(nameField.getText());
           this.clear();
        } catch (Exception e) {
            alert.showWarning(e);
        }
    }
	
	/**
     * Called when the user edit a depot name directly from the tableColumn
     * Method to edit the selected field in the observableList of depots
     */
	private void edit() {
		
        // nameColumn
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(t -> {
	        try{
	        	depotsController.editName(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue()); 
	        }catch(Exception e){
	        	alert.showWarning(e);
	        }
        });
	}
	
	/**
     * On delete button press, opens a confirmation dialog asking if you 
     * really want to delete the element
     * Method to delete the selected element from the observableList
     */
    @FXML
    private void delete() {
        Optional<ButtonType> result = alert.showConfirmation(depotsTable.getSelectionModel().getSelectedItem().getName());

        // When the user clicks ok, the selection gets deleted
        if (result.get() == ButtonType.OK) {
            int selectedIndex = depotsTable.getSelectionModel().getSelectedIndex();
            depotsController.removeDepot(depotsTable.getItems().get(selectedIndex));
        }
    }
    
    /**
     * Called when the user enter something in the search field
     */
    private void search(){
    	searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (!newValue.isEmpty()){
		        depotsTable.setItems(FXCollections.observableArrayList(depotsController.searchDepot(newValue)));
        	}else depotsTable.setItems(depotsController.getDepots());
        });
    }
    
    /**
	 * Method to disable/enable the delete button when something has been selected from the user
	 */
	private void update(){
		// Listen for selection changes and enable delete button
        delete.setDisable(true);
        depotsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        } );
	}
}
