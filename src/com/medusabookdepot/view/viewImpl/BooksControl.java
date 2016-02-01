/**
 * 'books.fxml' Controller Class
 */

package com.medusabookdepot.view.viewImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.controller.MedusaStandardBook;

public class BooksControl extends ScreenControl{
	
	private MedusaStandardBook booksController = new MedusaStandardBook();
	
	public BooksControl(){
		super();
		booksController.addBook("9788767547823", "Harry Potter e il prigioniero di Azkaban", 1998, 250, "HP", "Avventura", "JK Rowling", 15);
		booksController.addBook("9788767547833", "Harry Potter e la camera dei segreti", 1997, 320, "HP", "Avventura", "JK Rowling", 10);
		booksController.addBook("9788767547823", "Harry Potter e la pietra filosofale", 1995, 350, "HP", "Avventura", "JK Rowling", 10);
	}

	// TODO TEMPORARY TEST LIST - Creating Observable List
	//private ObservableList<StandardBook> tempBooksList = FXCollections.observableArrayList();
	
	@FXML
	private TableView<StandardBook> stdBooksTable;
	@FXML
	private TableColumn<StandardBook, String> isbnColumn;
	@FXML
	private TableColumn<StandardBook, String> nameColumn;
	@FXML
	private TableColumn<StandardBook, String> yearColumn;
	@FXML
	private TableColumn<StandardBook, String> pagesColumn;
	@FXML
	private TableColumn<StandardBook, String> serieColumn;
	@FXML
	private TableColumn<StandardBook, String> genreColumn;
	@FXML
	private TableColumn<StandardBook, String> authorColumn;
	@FXML
	private TableColumn<StandardBook, String> priceColumn;
	
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
        isbnColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getIsbn()));
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        //int  yearColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getYear()));
        //int  pagesColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPages()));
        serieColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSerie()));
        genreColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getGenre()));
        authorColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAuthor()));
        //int  priceColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPrice()));
     
        // Add observable list data to the table
        stdBooksTable.setItems(booksController.getAllBooks());
        
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
	    System.out.println(booksController.getAllBooks().toString());
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
		
		/** Same code without lambda **
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
			((StandardBook)t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
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
    //   if (isInputValid()) {}
		booksController.addBook(isbnField.getText(), titleField.getText(), Integer.parseInt(yearField.getText()), Integer.parseInt(pagesField.getText()), serieField.getText(), genreField.getText(), authorField.getText(), Integer.parseInt(priceField.getText())); 
	}
	
}