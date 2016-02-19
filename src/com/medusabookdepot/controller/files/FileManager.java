package com.medusabookdepot.controller.files;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import javafx.collections.ObservableList;

public class FileManager<A> {

	/* Passo tramite costruttore l'oggetto BooksController sulla cui lista la view agisce;
	* però per rendere questa classe utilizzabile non solo coi Book, dovrò realizzare un
	* interfaccia del controller che venga poi implementata da tutti i tipi diversi di
	* controller (tipo MovementsController, LibraryController, DepotController ecc.)
	*/
	private List<A> list;
	private File file;
	private QName qName;
	private final Class<A> classType;
	
	/**
	 * Constructor
	 * It creates the directory "book-depot" in home,
	 * load the file, if it exists and if it's not empty
	 * 
	 * @param list
	 * List of elements to manage from and to the file 
	 * @param filePath
	 * Xml file to manage
	 * @param classType
	 * Class of the elements of the file to manage
	 * @param qName
	 * Name for the list of element in the XML file.
	 * IE books (for element book of StandardBookImpl class), customers (for element customer of CustomerImpl class), ecc.
	 * It's needed only for XML file saving as XML loading accepts any element as root
	 */
	public FileManager(ObservableList<A> list, String filePath, Class<A> classType, String qName) {
		super();
		this.list = list;
		this.classType = classType;
		
		this.qName= new QName(qName);
		
		file = new File(filePath);	
		// Make the directory "book-depot" in home if doesn't exist already
		file.getParentFile().mkdirs();
		// Load the file, if it exists and if it's not empty
		if(file.exists() && file.length()!=0){
			loadDataFromFile();
		}
		
	}
	
	/**
	 * Loads element data from the file specified in the constructor. The current element data will be replaced.
	 */
	public void loadDataFromFile() {
	    try {
	        JAXBContext context = JAXBContext.newInstance(Wrapper.class, this.classType);
	        Unmarshaller um = context.createUnmarshaller();
	        
	        // Clearing the list before reading elements
	        list.clear();
	        
	        // Reading XML from the file and unmarshalling
	        list.addAll(unmarshal(um, this.classType, file.getPath()));
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
     * Unmarshal XML to Wrapper and return List value.
     */
	@SuppressWarnings("unchecked")
	private static <A> List<A> unmarshal(Unmarshaller unmarshaller, Class<A> clazz, String xmlLocation) throws JAXBException {
        StreamSource xml = new StreamSource(xmlLocation);
        Wrapper<A> wrapper = (Wrapper<A>) unmarshaller.unmarshal(xml, Wrapper.class).getValue();
        return wrapper.getElements();
    }

	/**
	 * Saves the current person data to the file specified in the constructor.
	 */
	@SuppressWarnings("rawtypes")
	public void saveDataToFile() {
	    try {
	        JAXBContext context = JAXBContext.newInstance(Wrapper.class, this.classType);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our element data
	        Wrapper<A> wrapper = new Wrapper<>();
	        wrapper.setElements(list);
	        
	        // Leverage JAXBElement to supply root element information
	        JAXBElement<Wrapper> jaxbElement = new JAXBElement<Wrapper>(this.qName, Wrapper.class, wrapper);

	        // Marshalling and saving XML to the file
	        m.marshal(jaxbElement, file);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
