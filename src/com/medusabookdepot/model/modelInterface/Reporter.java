package com.medusabookdepot.model.modelInterface;

public interface Reporter {
    public void buildReport(int dayBegin,int monthBegin, int yearBegin,int dayEnd,int monthEnd, int yearEnd,String fileName);

    void sendEmail(String from, String to);


    void sendEmail2(String aFromEmailAddr, String aToEmailAddr, String aSubject, String aBody);
}
