package com.medusabookdepot.view;

import java.util.Optional;

import com.medusabookdepot.controller.DepotsController;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class AddDepotControl extends ScreenControl{
	
	private final DepotsController depotsController = DepotsController.getInstanceOf();
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
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
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
     * Called when the user add a new depot
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
     * Called when the user edit a depot name
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
     * really want to delete the element is passed 
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
    	
        FilteredList<DepotImpl> filteredData = new FilteredList<>(depotsController.getDepots(), p -> true);
 
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(depot -> {
            	
                // If filter text is empty, display all the items.
                if (newValue == null || newValue.isEmpty()) return true;

                // Compare all the items with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (depot.getName().toLowerCase().contains(lowerCaseFilter)) return true; 
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<DepotImpl> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(depotsTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        depotsTable.setItems(sortedData);
    }
    
    /**
	 * Method to disable/enable the delete button
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
