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

import javafx.collections.*;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;

public class StatisticsControl extends ScreenControl{

    //ObservableList of months
    private final ObservableList<String> monthNames = FXCollections.observableArrayList();
    
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
    
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Get an array with month names.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convert it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));
        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
        
        this.setMovementsData(movementsController.getMovements());
    }
    
    /**
     * Sets the Statistics graph with the number of movements in a specific month.
     * 
     * @param movements
     */
    public void setMovementsData(List<TransferImpl> movements) {
        // Count the number of movements in a specific month.
        int[] monthCounter = new int[12];
        for (TransferImpl movement : movements) {
        	LocalDate date = movement.getLeavingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month = date.getMonthValue() - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart. Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);
    }
}
