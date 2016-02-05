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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
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
        File f=new File(System.getProperty("user.home")+System.getProperty("file.separator")+"filesMedusa");
        if(!f.exists()&&!f.isDirectory()) {
            f.mkdir();
        }
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
    
    private String getFilePath(String fileName) {
        String filepath = System.getProperty("user.home")+System.getProperty("file.separator")+"filesMedusa"+System.getProperty("file.separator")+fileName;
        return filepath;
    }
    @Override
    public void buildReport(int dayBegin,int monthBegin, int yearBegin,int dayEnd,int monthEnd, int yearEnd,String fileName) {
        List<Transfer> transes=TransferManagerImpl.getInstanceOfTransferManger().getAllTransfers();
        BufferedWriter bw;
        Calendar c1=Calendar.getInstance();c1.set(yearBegin, monthBegin-1, dayBegin, 0, 0, 0);
        Date dateBeg=c1.getTime();
        Calendar c2=Calendar.getInstance();c2.set(yearEnd, monthEnd-1, dayEnd, 23, 59, 59);
        Date dateEnd=c2.getTime();
        try {
            bw = new BufferedWriter(new FileWriter(this.getFilePath(fileName), false));
            int x=0;
            bw.write("trasferimenti da "+dateBeg+" a "+dateEnd);
            bw.newLine();
            for(Transfer t:transes) {
                if((t.getLeavingDate().after(dateBeg)||t.getLeavingDate().equals(dateBeg))&&(t.getLeavingDate().before(dateEnd)||t.getLeavingDate().equals(dateBeg))) {                    
                    x++;
                    bw.write("trasferimento "+x+":");bw.newLine();
                    bw.write("          tracking number="+t.getTrackingNumber());bw.newLine();
                    bw.write("          prezzo totale="+t.getTotalPrice());bw.newLine();
                    bw.write("          data di partenza="+t.getLeavingDate());bw.newLine();
                    bw.write("          mittente="+t.getSender().getName());bw.newLine();
                    bw.write("          destinatario="+t.getReceiver().getName());bw.newLine();
                }
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.convertTextToPDF(new File(this.getFilePath(fileName)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
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
        
        
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            try {
                message.setFrom(new InternetAddress(from));
            } catch (AddressException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Set To: header field of the header.
            try {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            } catch (AddressException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Set Subject: header field
            try {
                message.setSubject("This is the Subject Line!");
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Now set the actual message
            try {
                message.setText("This is actual message");
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Send message
            try {
                Transport.send(message);
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Sent message successfully....");
         
    }
    //http://www.javapractices.com/topic/TopicAction.do?Id=144
    private static Properties fMailServerConfig = new Properties();
    @Override
    public void sendEmail2(String aFromEmailAddr, String aToEmailAddr,String aSubject, String aBody) {
            //Here, no Authenticator argument is used (it is null).
            //Authenticators are used to prompt the user for user
            //name and password.
            Session session = Session.getDefaultInstance(fMailServerConfig, null);
            MimeMessage message = new MimeMessage(session);
            try {
              //the "from" address may be set in code, or set in the
              //config file under "mail.from" ; here, the latter style is used
              //message.setFrom(new InternetAddress(aFromEmailAddr));
              message.addRecipient(
                Message.RecipientType.TO, new InternetAddress(aToEmailAddr)
              );
              message.setSubject(aSubject);
              message.setText(aBody);
              Transport.send(message);
            }
            catch (MessagingException ex){
              System.err.println("Cannot send email. " + ex);
            }
          }

    //http://karanbalkar.com/2014/01/convert-text-file-to-pdf-document-in-java/ Karan Balkar
    private boolean convertTextToPDF(File file) throws Exception  
    {  
 
        FileInputStream fis=null;  
        DataInputStream in=null;  
        InputStreamReader isr=null;  
        BufferedReader br=null;  
 
        try {  
 
            Document pdfDoc = new Document();  
            String output_file =file.getParent()+System.getProperty("file.separator")+file.getName().substring(0, file.getName().length()-3)+"pdf";  
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
        Reporter r=ReporterImpl.getInstanceOfReporter();

        r.buildReport(2,3,2013,2,1,2015,"reso.txt");
        //r.sendEmail("ferocemarcello@gmail.com", "ferocemarcello@virgilio.it");
        r.sendEmail2("ferocemarcello@gmail.com", "ferocemarcello@virgilio.it", "prova", "messagio di prova");
    }
}
