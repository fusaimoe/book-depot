/**
 * 
 */
package com.medusabookdepot.controller;

import java.util.List;

import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.view.viewImpl.FirstFrameImpl;
import com.medusabookdepot.view.viewinterface.FirstFrameInterface;

/**
 * @author Marcello_Feroce
 *
 */
public class Medusa {

    private final FirstFrameInterface firstframe;
    private final List<Depot> depots;

    public Medusa() {
            this.firstframe=new FirstFrameImpl();
            firstframe.welcome();
            this.depots=firstframe.setInizilizationFrame();
    }
    public static void main(String[] args) {
        new Medusa();

    }

}
