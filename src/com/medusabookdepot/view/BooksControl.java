/**
 * 'books.fxml' Controller Class
 */

package com.medusabookdepot.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.StandardBook;

import java.io.File;

import com.medusabookdepot.controller.FileManager;
import com.medusabookdepot.controller.BooksController;

public class BooksControl extends ScreenControl{
	
	private BooksController booksController = new BooksController();
	private FileManager fileManager = new FileManager(booksController);
	private File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot" + System.getProperty("file.separator") + "books.xml");	
	
	public BooksControl(){
		super();
		// Make the directory "book-depot" in home
		file.getParentFile().mkdirs();
		// Load the file, if it exists and if it's not empty
		if(file.exists() && file.length()!=0){
			fileManager.loadPersonDataFromFile(file);
		}
	}

	@FXML
	private TableView<StandardBookImpl> stdBooksTable;
	@FXML
	private TableColumn<StandardBookImpl, String> isbnColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> nameColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> yearColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> pagesColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> serieColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> genreColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> authorColumn;
	@FXML
	private TableColumn<StandardBookImpl, String> priceColumn;
	
	@FXML
    private TextField isbnField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField pagesField;
    @FXML
    private TextField serieField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField priceField;
     
	@FXML
	private Button delete;
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
    private void initialize() {

        // Initialize the table
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        //TODO int  yearColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getYear()));
        //TODO int  pagesColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPages()));
        serieColumn.setCellValueFactory(cellData -> cellData.getValue().serieProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        //TODO int  priceColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPrice()));
     
        // Add observable list data to the table
        stdBooksTable.setItems(booksController.getBooks());
        
        // Make the table columns editable by double clicking
        this.edit();
 
        // Listen for selection changes and enable delete button
        delete.setDisable(true);
        stdBooksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        } );
    }
	
	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void delete() {
		// TODO Implement Confirmation Dialog
	    int selectedIndex = stdBooksTable.getSelectionModel().getSelectedIndex();
	    stdBooksTable.getItems().remove(selectedIndex);
	    System.out.println(booksController.getBooks().toString());
	}
		
	/**
	 * Called when the user selects and double click a cell of the table.
	 * To stop editing the user need to press enter.
	 * 
	 * Check https://docs.oracle.com/javafx/2/ui_controls/table-view.htm to add functionality:
	 * - stop editing simply by changing focus
	 * 
	 * Use the setCellFactory method to reimplement the table cell as a text field with
	 * the help of the TextFieldTableCell class. The setOnEditCommit method processes editing
	 * and assigns the updated value to the corresponding table cell.
	 */
	private void edit() {
		
		stdBooksTable.setEditable(true);
		
		//isbnColumn
		isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		isbnColumn.setOnEditCommit( t -> {
			((StandardBook)t.getTableView().getItems().get(t.getTablePosition().getRow())).setIsbn(t.getNewValue());
		});
		
		/** Same piece of code but without the lambda, a bit more understandable maybe **
		 * 
		 * 	isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		 *  isbnColumn.setOnEditCommit(
	     *		new EventHandler<CellEditEvent<StandardBook, String>>() {
		 *			public void handle(CellEditEvent<StandardBook, String> t) {
		 *				((StandardBook) t.getTableView().getItems().get(t.getTablePosition().getRow())).setIsbn(t.getNewValue());
		 *			}
		 *		}
		 *	);
		 *
		 **/
		
		//titleColumn
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setOnEditCommit( t -> {
			((StandardBook)t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(t.getNewValue());
		});
		//serieColumn
		serieColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		serieColumn.setOnEditCommit( t -> {
			((StandardBook)t.getTableView().getItems().get(t.getTablePosition().getRow())).setSerie(t.getNewValue());
		});
		//genreColumn
		genreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		genreColumn.setOnEditCommit( t -> {
			((StandardBook)t.getTableView().getItems().get(t.getTablePosition().getRow())).setGenre(t.getNewValue());
		});
		//authorColumn
		authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		authorColumn.setOnEditCommit( t -> {
			((StandardBook)t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthor(t.getNewValue());
		});
	}
	
	@FXML
	private void add() {
    // TODO   if (isInputValid()) {}
		booksController.addBook(isbnField.getText(), titleField.getText(), Integer.parseInt(yearField.getText()), Integer.parseInt(pagesField.getText()), serieField.getText(), genreField.getText(), authorField.getText(), Integer.parseInt(priceField.getText()));
		fileManager.savePersonDataToFile(file);
	}
	
}