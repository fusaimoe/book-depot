/**
 * 'books.fxml' Controller Class
 */

package com.medusabookdepot.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.StandardBook;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.medusabookdepot.controller.BooksController;
import com.medusabookdepot.controller.files.ConvertXML2PDF;
import com.medusabookdepot.controller.files.FileManager;

public class BooksControl extends ScreenControl {

    private BooksController booksController = new BooksController();
    private String directoryPath = System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot"
            + System.getProperty("file.separator");
    private String xmlPath = directoryPath + ".xml" + System.getProperty("file.separator") + "books.xml";
    private String xslPath = directoryPath + ".xsl" + System.getProperty("file.separator") + "books.xsl";
    // TODO Aggiungere data a nome del file
    private String pdfPath = directoryPath + "books.pdf";
    private FileManager fileManager = new FileManager(booksController, xmlPath);

    public BooksControl() {
        super();
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
    @FXML
    private Button convert;

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
        delete.setDisable(true);
        stdBooksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            delete.setDisable(false);
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
        alert.setContentText(stdBooksTable.getSelectionModel().getSelectedItem().getTitle());
        alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();

        // When the user clicks ok, the selection gets deleted
        if (result.get() == ButtonType.OK) {
            int selectedIndex = stdBooksTable.getSelectionModel().getSelectedIndex();
            stdBooksTable.getItems().remove(selectedIndex);
            fileManager.saveDataToFile();
        }
    }

    /**
     * Called when the user selects and double click a cell of the table. To
     * stop editing the user need to press enter.
     * 
     * Check https://docs.oracle.com/javafx/2/ui_controls/table-view.htm to add
     * functionality: - stop editing simply by changing focus
     * 
     * Use the setCellFactory method to reimplement the table cell as a text
     * field with the help of the TextFieldTableCell class. The setOnEditCommit
     * method processes editing and assigns the updated value to the
     * corresponding table cell.
     */
    private void edit() {

        stdBooksTable.setEditable(true);

        // isbnColumn
        isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        isbnColumn.setOnEditCommit(t -> {
            ((StandardBook) t.getTableView().getItems().get(t.getTablePosition().getRow())).setIsbn(t.getNewValue());
            fileManager.saveDataToFile();
        });

        /**
         * Same piece of code but without the lambda, a bit more understandable
         * maybe **
         * 
         * isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
         * isbnColumn.setOnEditCommit( new
         * EventHandler<CellEditEvent<StandardBook, String>>() { public void
         * handle(CellEditEvent<StandardBook, String> t) { ((StandardBook)
         * t.getTableView().getItems().get(t.getTablePosition().getRow())).
         * setIsbn(t.getNewValue()); } } );
         *
         **/

        // titleColumn
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(t -> {
            ((StandardBook) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(t.getNewValue());
            fileManager.saveDataToFile();
        });

        // yearColumn
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yearColumn.setOnEditCommit(t -> {
            ((StandardBook) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setYear(Integer.parseInt(t.getNewValue()));
            fileManager.saveDataToFile();
        });

        // pagesColumn
        pagesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        pagesColumn.setOnEditCommit(t -> {
            ((StandardBook) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setPages(Integer.parseInt(t.getNewValue()));
            fileManager.saveDataToFile();
        });

        // serieColumn
        serieColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serieColumn.setOnEditCommit(t -> {
            ((StandardBook) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSerie(t.getNewValue());
            fileManager.saveDataToFile();
        });

        // genreColumn
        genreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        genreColumn.setOnEditCommit(t -> {
            ((StandardBook) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGenre(t.getNewValue());
            fileManager.saveDataToFile();
        });
        // authorColumn
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setOnEditCommit(t -> {
            ((StandardBook) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthor(t.getNewValue());
            fileManager.saveDataToFile();
        });

        // priceColumn
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setOnEditCommit(t -> {
            ((StandardBook) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setPrice(Integer.parseInt(t.getNewValue()));
            fileManager.saveDataToFile();
        });
    }

    @FXML
    private void add() throws NumberFormatException {
        try {
            // TODO isInputValid();
            booksController.addBook(isbnField.getText(), titleField.getText(), Integer.parseInt(yearField.getText()),
                    Integer.parseInt(pagesField.getText()), serieField.getText(), genreField.getText(),
                    authorField.getText(), Integer.parseInt(priceField.getText()));
            fileManager.saveDataToFile();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Pay Attention");
            alert.setHeaderText("Error!");
            alert.setContentText("You should enter a number in ");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());
            alert.showAndWait();
        }
    }
    @FXML
    private void convert() {
        try {
            if (!new File(xslPath).exists())
                throw new IOException("XSL Template doesn't exist");
            if (!new File(xmlPath).exists())
                throw new IllegalArgumentException("XML File doesn't exist");

            ConvertXML2PDF converter = new ConvertXML2PDF(xmlPath, xslPath, pdfPath);

            converter.open();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Template not found");
            alert.setHeaderText("Could not load a conversion template for "
                    + this.getClass().getName().substring(25, new String(this.getClass().getName()).length() - 7));
            alert.setContentText(
                    "If it's not there, make it yourself. It's so time consuming and I have more important things to do atm. Sorry :(");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No data to export");
            alert.setHeaderText("Could not load data from xml file");
            alert.setContentText("Probably there is no data to export. Make sure to save before exporting");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());
            alert.showAndWait();
        }

        // TODO if xsl doesn't exist, it's not possible to convert without
        // templates!
    }

}