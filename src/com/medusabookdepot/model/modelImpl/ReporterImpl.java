package com.medusabookdepot.model.modelImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
    //http://www.tutorialspoint.com/java/java_sending_email.htm
    @Override
    public void sendEmail(final String from, final String to) {
     // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        
        try{
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
         }catch (MessagingException mex) {
            mex.printStackTrace();
         }
    }
    //http://karanbalkar.com/2014/01/convert-text-file-to-pdf-document-in-java/ Karan Balkar
    @Override
    public boolean convertTextToPDF(File file) throws Exception  
    {  
 
        FileInputStream fis=null;  
        DataInputStream in=null;  
        InputStreamReader isr=null;  
        BufferedReader br=null;  
 
        try {  
 
            Document pdfDoc = new Document();  
            String output_file =file.getParent()+System.getProperty("file.separator")+"provaPdf.pdf";  
            PdfWriter writer=PdfWriter.getInstance(pdfDoc,new FileOutputStream(output_file));  
            pdfDoc.open();  
            pdfDoc.setMarginMirroring(true);  
            pdfDoc.setMargins(36, 72, 108,180);  
            pdfDoc.topMargin();  
            Font myfont = new Font();  
            Font bold_font = new Font();  
            bold_font.setStyle(Font.BOLD);  
            bold_font.setSize(10);  
            myfont.setStyle(Font.NORMAL);  
            myfont.setSize(10);  
            pdfDoc.add(new Paragraph("\n"));  
 
            if(file.exists()&&!file.isDirectory()){  
 
                fis = new FileInputStream(file);  
                in = new DataInputStream(fis);  
                isr=new InputStreamReader(in);  
                br = new BufferedReader(isr);  
                String strLine;  
 
                while ((strLine = br.readLine()) != null)  {  
 
                    Paragraph para =new Paragraph(strLine+"\n",myfont);  
                    para.setAlignment(Element.ALIGN_JUSTIFIED);  
                    pdfDoc.add(para);  
                }  
            }     
            else {  
 
                System.out.println("no such file exists!");  
                return false;  
            }  
            pdfDoc.close();   
        }  
 
        catch(Exception e) {  
            System.out.println("Exception: " + e.getMessage());  
        }  
        finally {  
 
            if(br!=null)  
            {  
                br.close();  
            }  
            if(fis!=null)  
            {  
                fis.close();  
            }  
            if(in!=null)  
            {  
                in.close();  
            }  
            if(isr!=null)  
            {  
                isr.close();  
            }  
 
        }  
 
        return true;  
    }
    public static void main(String...args) {
        ReporterImpl.getInstanceOfReporter().sendEmail("ferocemarcello@virgilio.it", "ferocemarcello@gmail.com");
    }
}
