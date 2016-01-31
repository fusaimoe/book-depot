package com.medusabookdepot.model.modelImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.medusabookdepot.model.modelInterface.Reporter;
import com.medusabookdepot.model.modelInterface.Transfer;

public class ReporterImpl implements Reporter {

    private static ReporterImpl single=null;//singleton
    
    private ReporterImpl() {
        //costruttore privato!
    }
    
    public static Reporter getInstanceOfReporter() {
        if(single==null) {
            single=new ReporterImpl();
            return single;
        }
        else {
            return ReporterImpl.single;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public void buildReport(Date dateBegin,Date dateEnd, String fileName) {
        List<Transfer> transes=(List<Transfer>)TransferManagerImpl.getInstanceOfTransferManger().getAllTransfers();
        BufferedWriter bw;
        
        try {
            bw = new BufferedWriter(new FileWriter(System.getProperty("user.home")+System.getProperty("file.separator")+ fileName, false));
            int x=0;
            bw.write("trasferimenti da "+dateBegin.toString()+" a "+dateEnd.toString());
            bw.newLine();
            for(Transfer t:transes) {
                if(t.getLeavingDate().after(dateBegin)&&t.getLeavingDate().before(dateEnd)) {
                    x++;
                    bw.write("trasferimento"+" x:"+"tracking number="+t.getTrackingNumber()+",prezzo totale "+t.getTotalPrice()+",data di partenza"+t.getLeavingDate()+", mittente "+t.getSender().getName()+",destinatario "+t.getReceiver().getName());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
