package com.medusabookdepot.controller.files;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javafx.collections.ObservableList;

public class FileManager<A> {

	private List<A> list;
	private final Class<A> classType;
	private File file;
	private String name;
	private String xmlPath;
	private QName qName;
	
	// Minimum number of character of a full XML file, if the file contains less than MIN_CHARS characters, it is empty
	private static final int MIN_CHARS = 80;
	
	// Fields relative to paths
	private String directoryPath = System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot" + System.getProperty("file.separator");
	
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
	public FileManager(ObservableList<A> list, Class<A> classType, String name) {
		super();
		
		this.list = list;
		this.classType = classType;
		
		this.name = name;
		this.xmlPath = directoryPath + ".xml" + System.getProperty("file.separator") + name + ".xml";
		
		this.qName= new QName(name);
		
		file = new File(xmlPath);	
		// Make the directory "book-depot" in home if doesn't exist already
		file.getParentFile().mkdirs();
		// Load the file, if it exists and if it's not empty
		if(file.exists() && file.length()>MIN_CHARS){
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
	
	public void convertXML2PDF() throws IOException {
		
		String pdfPath = directoryPath + name + new SimpleDateFormat("yyyyMMdd-HHmm-").format(new Date());
		String foPath = directoryPath + ".fo" + System.getProperty("file.separator") + name + ".fo";
		
		if (!new File(foPath).exists()) throw new IOException("FO Template doesn't exist");
		
		try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(pdfPath))){
			// Configure fopFactory and foUserAgent
            final FopFactory fopFactory = FopFactory.newInstance();
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(foPath));

            // Set the value of a <param> in the stylesheet
            transformer.setParameter("versionParam", "2.0");

            // Setup input for XSLT transformation
            Source src = new StreamSource(xmlPath);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
