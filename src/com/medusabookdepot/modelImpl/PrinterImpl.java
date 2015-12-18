/**
 * 
 */
package com.medusabookdepot.modelImpl;

import com.medusabookdepot.modelInterface.Printer;

/**
 * @author Marcello_Feroce
 *
 */
public class PrinterImpl extends CustomerImpl implements Printer {

    public PrinterImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }

}
