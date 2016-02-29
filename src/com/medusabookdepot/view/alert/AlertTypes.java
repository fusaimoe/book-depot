package com.medusabookdepot.view.alert;

import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.ButtonType;

public interface AlertTypes {

	public Optional<ButtonType> showConfirmation(String element);
	
	public Optional<ButtonType> showOpenPDFConfirmation();
	
	public void showError(Exception e);
	
	public void showWarning(Exception e);
	
	public void showConvertError(IOException e);
	
	public void priceError(IndexOutOfBoundsException e);
	
	public void emailNotSentError();
	
	public void emailSentSuccessfully();	
}
