package com.medusabookdepot.controller.files;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

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

public class ConvertXML2PDF {
	
	 public ConvertXML2PDF(String xmlFile, String xslFile, String pdfFile) {
		
		try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile))){
			// Configure fopFactory and foUserAgent
            final FopFactory fopFactory = FopFactory.newInstance();
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslFile));

            // Set the value of a <param> in the stylesheet
            transformer.setParameter("versionParam", "2.0");

            // Setup input for XSLT transformation
            Source src = new StreamSource(xmlFile);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        } catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	 
	 public void openFile(){
		 //TODO do it 
	 };

}
