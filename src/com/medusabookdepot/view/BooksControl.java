/**
 * 'books.fxml' Controller Class
 */

package com.medusabookdepot.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import java.io.IOException;
import java.util.Optional;

import com.medusabookdepot.controller.BooksController;

public class BooksControl extends ScreenControl {
	
	// Reference to the controller
    private BooksController booksController = BooksController.getInstanceOf();
    
    // Aler panel to manage exceptions
    private final AlertTypes alert = new AlertTypesImpl();
    
    public BooksControl() {
    	super();
    }

    @FXML
    private TableView<StandardBookImpl> stdBooksTable;
    
    @FXML
    private TableColumn<StandardBookImpl, String> isbnColumn, nameColumn, yearColumn, pagesColumn, 
    	serieColumn, genreColumn, authorColumn, priceColumn;

    @FXML
    private TextField isbnField, titleField, yearField, pagesField, serieField, genreField, authorField, priceField;

    @FXML
    private Button delete;
    @FXML
    private Button convert;
    @FXML
    private TextField searchField;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	
        // Initialize the table
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asString());
        pagesColumn.setCellValueFactory(cellData -> cellData.getValue().pagesProperty().asString());
        serieColumn.setCellValueFactory(cellData -> cellData.getValue().serieProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asString());

        // Add observable list data to the table
        stdBooksTable.setItems(booksController.getBooks());

        // Make the table columns editable by double clicking
        this.edit();

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

        Optional<ButtonType> result = alert.showConfirmation(stdBooksTable.getSelectionModel().getSelectedItem().getTitle());

        // When the user clicks ok, the selection gets deleted
        if (result.get() == ButtonType.OK) {
            int selectedIndex = stdBooksTable.getSelectionModel().getSelectedIndex();
            booksController.removeBook(stdBooksTable.getItems().get(selectedIndex));
        }
    }

    /**
     * Called when the user selects and double click a cell of the table. 
     * To stop editing the user need to press enter.
     */
    @SuppressWarnings("unchecked")
	private void edit() {
    	
        //Set all the columns as editable directly from the tableView
        for(TableColumn<StandardBookImpl, ?> n: stdBooksTable.getColumns()){
			if(n instanceof TableColumn){
				((TableColumn<StandardBookImpl, String>)n).setCellFactory(TextFieldTableCell.forTableColumn());
			}
		}
		
        // isbnColumn
        isbnColumn.setOnEditCommit(t -> {
        	try{
        		booksController.editISBN(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue()); 
        	}catch(Exception e){
                alert.showWarning(e);
            }
        });

        // titleColumn
        nameColumn.setOnEditCommit(t -> {
        	try{
	            booksController.editTitle(t.getTableView().getItems().get(t.getTablePosition().getRow()),
	            		t.getNewValue());
        	}catch(Exception e){
        		 alert.showWarning(e);
            }
        });

        // yearColumn
        yearColumn.setOnEditCommit(t -> {
        	try{
	        	booksController.editYear(t.getTableView().getItems().get(t.getTablePosition().getRow()), 
	        			t.getNewValue());
        	}catch(Exception e){
        		 alert.showWarning(e);
            }
        
        });

        // pagesColumn
        pagesColumn.setOnEditCommit(t -> {
        	try{
	        	booksController.editPages(t.getTableView().getItems().get(t.getTablePosition().getRow()), 
	        			t.getNewValue());
        	}catch(Exception e){
        		 alert.showWarning(e);
            }
            
        });

        // serieColumn
        serieColumn.setOnEditCommit(t -> {
        	try{
        		booksController.editSerie(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue());
        	}catch(Exception e){
        		 alert.showWarning(e);
            }
            
        });

        // genreColumn
        genreColumn.setOnEditCommit(t -> {
        	try{
        		booksController.editGenre(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue());
        	}catch(Exception e){
        		 alert.showWarning(e);
            }
            
        });
        // authorColumn
        authorColumn.setOnEditCommit(t -> {
        	try{
        		booksController.editAuthor(t.getTableView().getItems().get(t.getTablePosition().getRow()), t.getNewValue());
        	}catch(Exception e){
        		 alert.showWarning(e);
            }
            
        });

        // priceColumn
        priceColumn.setOnEditCommit(t -> {
        	try{
	        	booksController.editPrice(t.getTableView().getItems().get(t.getTablePosition().getRow()), 
	        			t.getNewValue());
        	}catch(Exception e){ 
        		alert.showWarning(e);
        	}
            
        });
    }

    /**
     * Called when the user add a new book
     */
    @FXML
    private void add() {
        try {
           booksController.addBook(isbnField.getText(), titleField.getText(), yearField.getText(),
                    pagesField.getText(), serieField.getText(), genreField.getText(),
                    authorField.getText(), priceField.getText());
           this.clear();
        } catch (IndexOutOfBoundsException e){
        	alert.priceError(e);
        } catch (Exception e) {
        	alert.showWarning(e);
        }
    }
    
    /**
     * Called when the user wants to convert the TableView to a PDF file
     */
    @FXML
    private void convert() {
        try {
            booksController.convert();
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
		        stdBooksTable.setItems(FXCollections.observableArrayList(booksController.searchBook(newValue)));
        	}else stdBooksTable.setItems(booksController.getBooks());
        });
    }
    
    /**
	 * Method to disable/enable the delete button 
	 */
    private void update(){
    	// Listen for selection changes and enable delete button
    	delete.setDisable(true);
        stdBooksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            delete.setDisable(false);
        });
    }
}