/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.sql.Date;

import com.medusabookdepot.model.modelInterface.Library;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class LibraryImpl extends CustomerImpl implements Library{

    public LibraryImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }
    
    public String toString() {
        return this.name+","+this.address+","+this.telephoneNumber;
        
    }
}
