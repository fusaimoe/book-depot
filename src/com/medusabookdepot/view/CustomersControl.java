/**
 * 'customers.fxml' Controller Class
 */

package com.medusabookdepot.view;
/*
import com.medusabookdepot.controller.CustomersController;
import com.medusabookdepot.controller.FileManager;*/

import com.medusabookdepot.model.modelInterface.Customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomersControl extends ScreenControl{
	
	//private CustomersController customersController = new CustomerController();
	//private final String filePath = System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot" + System.getProperty("file.separator") + "customers.xml";
	//private FileManager fileManager = new FileManager(customersController, filePath);
	
	public CustomersControl(){
		super();
	}
	
	@FXML
	private TableView<Customer> customersTable;
	@FXML
	private TableColumn<Customer, String> nameColumn;
	@FXML
	private TableColumn<Customer, String> addressColumn;
	@FXML
	private TableColumn<Customer, String> phoneColumn;
	
	@FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    
    @FXML
	private Button delete;
	
    
    /**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
    private void initialize() {
	/*	
		 // Initialize the table
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty().asString());
     
        // Add observable list data to the table
        customersTable.setItems(customersController.getBooks());
        
        // Make the table columns editable by double clicking
        this.edit();
 
        // Listen for selection changes and enable delete button
        delete.setDisable(true);
        customersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        } );*/
	}


}
