/**
 * 'customers.fxml' Controller Class
 */

package com.medusabookdepot.view;
import java.io.IOException;
import java.util.Optional;

import com.medusabookdepot.controller.CustomersController;
import com.medusabookdepot.model.modelImpl.CustomerImpl;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;

public class CustomersControl extends ScreenControl{
	
	// Reference to the controller
	private CustomersController customersController = CustomersController.getInstanceOf();
	
	// Aler panel to manage exceptions
	private final Alert alert = new Alert(AlertType.WARNING);
	
	public CustomersControl(){
		super();
		//Added CSS Style to the alert panel
		alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());
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

	private void edit() {

        customersTable.setEditable(true);

        // nameColumn
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(t -> {
	        try{
	        	customersController.editName(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue()); 
	        }catch(Exception e){
	        	alert.setTitle("Pay Attention");
	        	alert.setHeaderText("Error!");
	        	alert.setContentText(e.getMessage());
	        	alert.showAndWait();
	        }
        });
        
        // addressColumn
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(t -> {
	        try{
	        	customersController.editAddress(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue()); 
	        }catch(Exception e){
	        	alert.setTitle("Pay Attention");
	        	alert.setHeaderText("Error!");
	        	alert.setContentText(e.getMessage());
	        	alert.showAndWait();
	        }
        });
        
        // phoneColumn
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setOnEditCommit(t -> {
	        try{
	        	customersController.editPhone(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue()); 
	        }catch(Exception e){
	        	alert.setTitle("Pay Attention");
	        	alert.setHeaderText("Error!");
	        	alert.setContentText(e.getMessage());
	        	alert.showAndWait();
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
            alert.setTitle("Pay Attention");
            alert.setHeaderText("Error!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
	
	 /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void delete() {

        // On delete button press, opens a confirmation dialog asking if you
        // really want to delete
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you really want to delete the following element?");
        alert.setContentText(customersTable.getSelectionModel().getSelectedItem().getName());
        alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();

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
            alert.setTitle("Template not found");
            alert.setHeaderText("Could not load a conversion template for "
                    + this.getClass().getName().substring(25, new String(this.getClass().getName()).length() - 7));
            alert.setContentText(
                    "If it's not there, make it yourself. It's so time consuming and I have more important things to do atm. Sorry :(");
            alert.showAndWait();
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
	 * 
	 */
    private void update(){
    	delete.setDisable(true);
        customersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        } );
    }
}
