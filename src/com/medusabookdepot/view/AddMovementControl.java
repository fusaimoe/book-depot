/**
 * 'movements.fxml' Controller Class
 */

package com.medusabookdepot.view;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import com.medusabookdepot.controller.MovementsController;
import com.medusabookdepot.model.modelImpl.TransferImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class AddMovementControl extends ScreenControl{
	
	private final MovementsController movementsController = MovementsController.getInstanceOf();
	@SuppressWarnings("unused") //TODO Try something else
	private AutoCompleteComboBoxListener<String> autoCompleteFactory;
	
	// Aler panel to manage exceptions
    private final Alert alert = new Alert(AlertType.WARNING);
	
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
    
    @FXML
    private HBox hBoxFields;
    
    public AddMovementControl(){
		super();
		
		// CSS style added to the alert panel
        alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());
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
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLeavingDate().toString()));
        trackingColumn.setCellValueFactory(cellData -> cellData.getValue().trackingNumberProperty());
        
        movementsTable.setItems(movementsController.getTempData());
        
        titleBox.setItems(FXCollections.observableArrayList(movementsController.getTitlesString()));
        autoCompleteFactory = new AutoCompleteComboBoxListener<String>(titleBox);
        senderBox.setItems(FXCollections.observableArrayList(movementsController.getCanSendTransferrersString()));
        autoCompleteFactory = new AutoCompleteComboBoxListener<String>(senderBox);
        receiverBox.setItems(FXCollections.observableArrayList(movementsController.getCustomersAndDepotsString()));
        autoCompleteFactory = new AutoCompleteComboBoxListener<String>(receiverBox);
        
        this.update();
    }
    
    /**
     * Called when the user add a new book
     */
    @FXML
    private void add(){
    	try {
    		Instant instant = Instant.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()));
    		Date date = Date.from(instant);
    		movementsController.addMovement(senderBox.getValue(), receiverBox.getValue(), date, isbnBox.getValue(), quantityField.getText(), trackingField.getText());
    		this.clear();
         } catch (Exception e) {
             alert.setTitle("Pay Attention");
             alert.setHeaderText("Error!");
             alert.setContentText(e.getMessage());
             alert.showAndWait();
         }
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
	 */
	private void update(){
		// Listen for selection changes of the table and enable delete button
        delete.setDisable(true);
        movementsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        });
        
        // Listen for selection changes of titleBox and enable and filter, isbnBox 
        isbnBox.setDisable(true);
        titleBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	// Enable button if the title ComboBox has a newValue
        	isbnBox.setDisable(false);
        	// Set the items of the isbn ComboBox from a list of all the values possible from the selected title
        	isbnBox.setItems(FXCollections.observableArrayList(movementsController.getAllIsbnFromTitle(newValue)));
        	// If there is only one isbn possible value, select it
        	if(movementsController.getAllIsbnFromTitle(newValue).size()==1){
        		isbnBox.getSelectionModel().select(0);
	        }
        	// If the list of all possible values is empty, or the title ComboBox is still empty, disable the isbn ComboBox 
            if(titleBox.getSelectionModel().isEmpty() || movementsController.getAllIsbnFromTitle(newValue).isEmpty()){
            	isbnBox.setDisable(true);     	
            }
        });
        
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	/**
	 * Method to clear only the Quantity, Title and ISBN fields.
	 * Overrides clear method from ScreenControl because when the user is adding movements, most of time he's keeping the same sender, receiver and tracking number but just changing book and quantity 
	 */
	public void clear(){
    	for(Node node: hBoxFields.getChildren()){
    		if (node instanceof TextField) {
    	        ((TextField)node).clear();
    	    }
    		if (node instanceof ComboBox) {
    	        ((ComboBox)node).getSelectionModel().clearSelection();
    	    }
    	}
    }
}