package com.medusabookdepot.view;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import com.medusabookdepot.controller.files.EmailSender;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class EmailControl {
	
		// Reference to the email sender
		private final EmailSender emailController = new EmailSender();
		
		// Aler panel to manage exceptions
	    private final AlertTypes alert = new AlertTypesImpl();
	    
		public EmailControl(){
			super();
			this.setPaths();
		}
		
		@FXML
		private TextField toField;
		@FXML
		private TextField subjectField;
		@FXML
		private TextArea messageArea;
		
		private final ObservableList<String> paths = FXCollections.observableArrayList();
		
		@FXML
		private final ListView<String> listView;
		
		private String attachmentPath;

	    
	    /**
	     * Called after the fxml file has been loaded.
	     * Method to initializes the control class. 
	     */
	    public void initialize() {
	    	
	    	listView.setItems(paths);
	    }
	    
	    /**
	     * Called when the user press the 'addAttachment' button 
	     * Method to set the attachment field
	     */
		@FXML
	    private void addAttachment() {
	        try {
	           attachmentPath = listView.getSelectionModel().getSelectedItem();
	        } catch (Exception e) {
	            alert.showWarning(e);
	        }
	    }
		
		/**
	     * Called when the user press the send button
	     * Method to send the email
	     */
		private void send() {			
	       emailSender.send(toField.getText(), subjectField.getText(), messageArea.getText(), attachmentPath);
		}
		
		/**
	     * Method to set the paths list
	     */
		private void setPaths(){
			Files.walk(Paths.get("/home/you/Desktop")).forEach(filePath -> {
	    	    if (Files.isRegularFile(filePath)) {
	    	        paths.add(filePath.toString());
	    	    }
	    	});
		}
}
