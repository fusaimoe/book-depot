/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.medusabookdepot.model.modelInterface.Person;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class PersonImpl extends CustomerImpl implements Person {

    public PersonImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
        this.isHuman=true;
        this.isLibrary=false;
    }
    public String toString() {
        return this.name+","+this.address+","+this.telephoneNumber+"CH";
        
    }
    
}
