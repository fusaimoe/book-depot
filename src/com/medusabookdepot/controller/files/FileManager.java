package com.medusabookdepot.controller.files;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelImpl.StandardBookListWrapper;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FileManager {

	/* Passo tramite costruttore l'oggetto BooksController sulla cui lista la view agisce;
	* però per rendere questa classe utilizzabile non solo coi Book, dovrò realizzare un
	* interfaccia del controller che venga poi implementata da tutti i tipi diversi di
	* controller (tipo MovementsController, LibraryController, DepotController ecc.)
	*/
	private ObservableList<StandardBookImpl> books;
	private File file;
	
	/**
	 * Constructor
	 * It makes the directory "book-depot" in home,
	 * load the file, if it exists and if it's not empty
	 * 
	 * @param filePath
	 */
	public FileManager(ObservableList<StandardBookImpl> books, String filePath) {
		super();
		this.books = books;
		
		file = new File(filePath);	
		// Make the directory "book-depot" in home
		file.getParentFile().mkdirs();
		// Load the file, if it exists and if it's not empty
		if(file.exists() && file.length()!=0){
			loadDataFromFile();
		}
		
	}
	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadDataFromFile() {
	    try {
	        JAXBContext context = JAXBContext.newInstance(StandardBookListWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        StandardBookListWrapper wrapper = (StandardBookListWrapper) um.unmarshal(file);

	        books.clear();
	        books.addAll(wrapper.getBooks());
	        
	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data from file:");
	        alert.setContentText(file.getPath());
	        alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());

	        alert.showAndWait();
	    }
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void saveDataToFile() {
	    try {
	        JAXBContext context = JAXBContext.newInstance(StandardBookListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        StandardBookListWrapper wrapper = new StandardBookListWrapper();
	        wrapper.setBooks(books);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data to file:");
	        alert.setContentText(file.getPath());
	        alert.getDialogPane().getStylesheets().add(getClass().getResource("materialDesign.css").toExternalForm());
	        alert.showAndWait();
	    }
	}
	
}
