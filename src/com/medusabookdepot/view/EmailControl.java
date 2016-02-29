package com.medusabookdepot.view;

import java.io.File;

import javax.mail.MessagingException;

import com.medusabookdepot.controller.files.EmailSender;
import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EmailControl extends ScreenControl{
	
		// Reference to the email sender
		private final EmailSender emailSender = new EmailSender();
		
		// Aler panel to manage exceptions
	    private final AlertTypes alert = new AlertTypesImpl();
	    
		@FXML
		private TextField toField;
		@FXML
		private TextField subjectField;
		@FXML
		private TextField attachedField;
		@FXML
		private TextArea messageArea;
		
		private final ObservableList<String> paths = FXCollections.observableArrayList();
		
		@FXML
		private ListView<String> listView;

		public EmailControl(){
			super();
			this.setPaths();
		}
	    
	    /**
	     * Called after the fxml file has been loaded.
	     * Method to initializes the control class. 
	     */
	    public void initialize() {
	    	attachedField.setEditable(false);
	    	listView.setItems(paths);
	    }
	    
	    /**
	     * Called when the user press the 'addAttachment' button 
	     * Method to set the attachment field
	     */
		@FXML
	    private void addAttachment() {
	        try {
	        	attachedField.setText(listView.getSelectionModel().getSelectedItem());
	        } catch (Exception e) {
	        	alert.showWarning(e);
	        }
	    }
		
		/**
	     * Called when the user press the send button
	     * Method to send the email
	     */
		public void send() {			
			try {
				emailSender.send(toField.getText(), subjectField.getText(), messageArea.getText(), attachedField.getText());
				alert.emailSentSuccessfully();
			} catch (MessagingException e) {
				alert.emailNotSentError();
			} catch (IllegalArgumentException e){
				alert.showError(e);
			}
		}
		
		/**
	     * Method to set the paths list
	     */
		private void setPaths(){
			File directory = new File(FileManager.getDirectoryPath());
			File[] filesArray = directory.listFiles();
			for(int i=0; i<filesArray.length; i++){
				if(filesArray[i].isFile()){
			        paths.add(filesArray[i].getName());
			    }
			}
		}
}
