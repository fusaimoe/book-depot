/**
 * 'statistics.fxml' Control Class
 */

package com.medusabookdepot.view;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.medusabookdepot.controller.MovementsController;
import com.medusabookdepot.model.modelImpl.TransferImpl;

import javafx.collections.*;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class StatisticsControl extends ScreenControl{

    //ObservableList of movements
    private final MovementsController movementsController = MovementsController.getInstanceOf();
    
    //ObservableList of months
    private final ObservableList<String> monthNames = FXCollections.observableArrayList(
    		Arrays.asList(DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths()));
    
    //ObservableList of years
    private final ObservableList<String> yearNames = movementsController.getYearsWithMovements();
    
    // Create a monthCounter for each month. Add his values to the series.
	private final int[] monthCounter = new int[12];

	@FXML
	private Button statistics;
	@FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private ChoiceBox<String> yearBox;    
	
    public StatisticsControl() {
		super();
	}
	
    /**
     * Called after the fxml file has been loaded.
     * Method to initializes the control class. 
     */
    @FXML
    private void initialize() {

        yearBox.setItems(yearNames);
        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
        
        this.setMovementsData();
    }
    
    /**
     * Sets the statistics graph according to the number of movements in a specific month.
     */
    public void setMovementsData() {

        // Count the number of movements in a specific month.
    	yearBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{

    		// Clear the barChart and the monthCounter before changing year	
	        for (int i = 0; i < monthCounter.length; i++) monthCounter[i]=0;
	        barChart.getData().clear(); 
		        
	        for (TransferImpl movement : movementsController.getMovements()) {
	        	// Convert the util.Date to LocalDate
	        	LocalDate date = movement.getLeavingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        		// Filter movements by year
	        	if(date.getYear()==Integer.parseInt(newValue)){
		            int month = date.getMonthValue() - 1; 
		            monthCounter[month]++; // Increment the month according to the number of movements
	        	}
	        }

	    	Series<String, Integer> series = new Series<>();
	        // Create a XYChart. Data object for each month. Add it to the series.
	        for (int i = 0; i < monthCounter.length; i++) {
	            series.getData().add(new Data<>(monthNames.get(i), monthCounter[i]));
	        }

		    barChart.getData().add(series);		        
    	});
    }
}
