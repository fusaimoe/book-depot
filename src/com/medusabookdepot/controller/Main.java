package com.medusabookdepot.controller;

import com.medusabookdepot.test.Test;
import com.medusabookdepot.view.*;

public class Main {

    private final Menu firstFrame;

    public Main() {
            this.firstFrame = new Menu();
            this.firstFrame.mainGui(new String[]{""});
    }
    
    public static void main(String[] args) throws Exception {
        
        Test prova=new Test();
        prova.test();
    	System.out.println("JUnit test result: " + (prova.getResult()?"SUCCESS!":"FAIL!"));
    	System.out.println("Loading UI ..");
        new Main();
    }

}
