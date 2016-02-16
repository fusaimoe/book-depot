/**
 * 'statistics.fxml' Controller Class
 */

package com.medusabookdepot.view;

import java.text.DateFormatSymbols;
import java.util.*;
import javafx.collections.*;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;

public class StatisticsControl extends ScreenControl{

    //ObservableList of months
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    
	public StatisticsControl(){
		super();
	}
	
	@FXML
	private Button statistics;
	@FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Get an array with the English month names.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convert it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));
        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
    }
}
