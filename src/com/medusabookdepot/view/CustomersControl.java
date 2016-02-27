/**
 * 'customers.fxml' Control Class
 */

package com.medusabookdepot.view;
import java.io.IOException;
import java.util.Optional;

import com.medusabookdepot.controller.CustomersController;
import com.medusabookdepot.model.modelImpl.CustomerImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class CustomersControl extends ScreenControl{
	
	// Reference to the controller
	private CustomersController customersController = CustomersController.getInstanceOf();
	
	// Aler panel to manage exceptions
	private final AlertTypes alert = new AlertTypesImpl();
	
	public CustomersControl(){
		super();
	}
	
	@FXML
	private TableView<CustomerImpl> customersTable;
	@FXML
	private TableColumn<CustomerImpl, String> nameColumn, addressColumn, phoneColumn, typeColumn;
	
	@FXML
    private TextField nameField, addressField, phoneField;	
    @FXML
	private ChoiceBox<String> typeChoiceBox;
    
    @FXML
	private Button delete;
    @FXML
    private TextField searchField;

    /**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
    private void initialize() {
		
		 // Initialize the table
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneNumberProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getType());
       
        // Add observable list data to the table
        customersTable.setItems(customersController.getCustomers());
        
        // Make the table columns editable by double clicking
        this.edit();
 
        // Listen for selection changes and enable delete button
        this.update();
        
        // Use a 'searchField' to search for books in the tableView
        this.search();
	}

	/**
     * Called when the user selects and double click a cell of the table. 
     * To stop editing the user need to press enter.
     */
	@SuppressWarnings("unchecked")
	private void edit() {

	       //Set all the columns as editable directly from the tableView
        for(TableColumn<CustomerImpl, ?> column: customersTable.getColumns()){
			if(column instanceof TableColumn){
				((TableColumn<CustomerImpl, String>)column).setCellFactory(TextFieldTableCell.forTableColumn());
			}
		}

        // nameColumn
        nameColumn.setOnEditCommit(t -> {
	        try{
	        	customersController.editName(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue()); 
	        }catch(Exception e){
	        	alert.showWarning(e);
	        }
        });
        
        // addressColumn
        addressColumn.setOnEditCommit(t -> {
	        try{
	        	customersController.editAddress(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue()); 
	        }catch(Exception e){
	        	alert.showWarning(e);
	        }
        });
        
        // phoneColumn
        phoneColumn.setOnEditCommit(t -> {
	        try{
	        	customersController.editPhone(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue()); 
	        }catch(Exception e){
	        	alert.showWarning(e);
	        }
        });
	}
	
	/**
     * Called when the user add a new customer
     */
	@FXML
    private void add() {
        try {
           customersController.addCustomer(nameField.getText(), addressField.getText(), phoneField.getText(), typeChoiceBox.getValue());
           this.clear();
        } catch (Exception e) {
        	alert.showWarning(e);
        }
    }
	
	/**
     * On delete button press, opens a confirmation dialog asking if you 
     * really want to delete the element is passed 
     */
    @FXML
    private void delete() {
    	Optional<ButtonType> result = alert.showConfirmation(customersTable.getSelectionModel().getSelectedItem().getName());
        // When the user clicks ok, the selection gets deleted
        if (result.get() == ButtonType.OK) {
            int selectedIndex = customersTable.getSelectionModel().getSelectedIndex();
            customersController.removeCustomer(customersTable.getItems().get(selectedIndex));
        }
    }
    
    /**
     * Called when the user wants to convert the TableView to a PDF file
     */
    @FXML
    private void convert() {
        try {
            customersController.convert();
        } catch (IOException e) {
        	alert.showConvertError(e);
        }
    }
    
    /**
     * Called when the user enter something in the search field
     */
    private void search(){
    	searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (!newValue.isEmpty()){
		        customersTable.setItems(FXCollections.observableArrayList(customersController.searchCustomer(newValue)));
        	}else customersTable.setItems(customersController.getCustomers());
        });
    }
    
    /**
	 * Method to disable/enable the delete button 
	 */
    private void update(){
    	delete.setDisable(true);
        customersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        } );
    }
}
