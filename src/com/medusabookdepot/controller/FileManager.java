package com.medusabookdepot.controller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.medusabookdepot.model.modelImpl.StandardBookListWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FileManager {

	/* Passo tramite costruttore l'oggetto BooksController sulla cui lista la view agisce;
	* però per rendere questa classe utilizzabile non solo coi Book, dovrò realizzare un
	* interfaccia del controller che venga poi implementata da tutti i tipi diversi di
	* controller (tipo MovementsController, LibraryController, DepotController ecc.)
	*/
	private BooksController controller;
	
	public FileManager(BooksController controller) {
		super();
		this.controller = controller;
	}
	
	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadPersonDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext.newInstance(StandardBookListWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        StandardBookListWrapper wrapper = (StandardBookListWrapper) um.unmarshal(file);

	        controller.getBooks().clear();
	        controller.getBooks().addAll(wrapper.getBooks());
	        
	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void savePersonDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext.newInstance(StandardBookListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        StandardBookListWrapper wrapper = new StandardBookListWrapper();
	        wrapper.setBooks(controller.getBooks());

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}
	
}
