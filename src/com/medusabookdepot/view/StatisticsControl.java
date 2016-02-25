/**
 * 'statistics.fxml' Controller Class
 */

package com.medusabookdepot.view;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.medusabookdepot.controller.MovementsController;
import com.medusabookdepot.model.modelImpl.TransferImpl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class StatisticsControl extends ScreenControl{

    //ObservableList of months
    private final ObservableList<String> monthNames = FXCollections.observableArrayList();
    
  //ObservableList of years
    private final ObservableList<String> yearNames = FXCollections.observableArrayList();
    
    //ObservableList of movements
    private final MovementsController movementsController = MovementsController.getInstanceOf();
	
	public StatisticsControl(){
		super();
	}
	
	@FXML
	private Button statistics;
	@FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private ChoiceBox<String> yearBox;
    
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Get an array with month names.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
     // Get an array with month names.
        yearNames.add("2015");
        yearNames.add("2016");
        yearBox.setItems(yearNames);
        // Convert it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));
        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
        this.setMovementsData(movementsController.getMovements());

    }
    
    /**
     * Sets the statistics graph according to the number of movements in a specific month.
     * 
     * @param movements
     */
    public void setMovementsData(List<TransferImpl> movements) {
    	
        // Count the number of movements in a specific month.
    	yearBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
    		// Create a monthCounter for each month. Add his values to the series.
    		final int[] monthCounter = new int[12];
    		// Clear the barChart and the monthCounter before changing year	
	        for (int i = 0; i < monthCounter.length; i++) monthCounter[i]=0;
	        barChart.getData().clear(); 
		        
	        for (TransferImpl movement : movements) {
	        	// Convert the util.Date to LocalDate
	        	LocalDate date = movement.getLeavingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        		// Filter movements by year
	        	if(date.getYear()==Integer.parseInt(yearNames.get(newValue.intValue()))){
		            int month = date.getMonthValue() - 1; 
		            monthCounter[month]++; // Increment the month according to the number of movements
	        	}
	        }
		        
		    XYChart.Series<String, Integer> series = new XYChart.Series<>();

	        // Create a XYChart. Data object for each month. Add it to the series.
	        for (int i = 0; i < monthCounter.length; i++) {
	            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
	        }

		    barChart.getData().add(series);		        
    	});
    }
}
