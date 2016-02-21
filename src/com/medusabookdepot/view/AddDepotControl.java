package com.medusabookdepot.view;

import java.util.Optional;

import com.medusabookdepot.controller.DepotsController;
import com.medusabookdepot.model.modelImpl.DepotImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;

public class AddDepotControl extends ScreenControl{
	
	private final DepotsController depotsController = new DepotsController();
    private final Alert alert = new Alert(AlertType.WARNING);
    
	public AddDepotControl(){
		super();
		alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());
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
        
        this.edit();
        this.search();
    }
    
    /**
     * Called when the user add a new depot
     */
	@FXML
    private void add() {
        try {
           depotsController.addDepot(nameField.getText());
        } catch (Exception e) {
            alert.setTitle("Pay Attention");
            alert.setHeaderText("Error!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
	
	/**
     * Called when the user edit a depot name
     */
	private void edit() {

        depotsTable.setEditable(true);

        // nameColumn
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(t -> {
	        try{
	        	depotsController.editName(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue()); 
	        }catch(Exception e){
	        	alert.setTitle("Pay Attention");
	        	alert.setHeaderText("Error!");
	        	alert.setContentText(e.getMessage());
	        	alert.showAndWait();
	        }
        });
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
        alert.setContentText(depotsTable.getSelectionModel().getSelectedItem().getName());
        alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();

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
}
