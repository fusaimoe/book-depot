package com.medusabookdepot.model.modelInterface;

import java.io.File;

public interface Reporter {
    public void buildReport(int dayBegin,int monthBegin, int yearBegin,int dayEnd,int monthEnd, int yearEnd,String fileName);

    void sendEmail(String from, String to);

    boolean convertTextToPDF(File file) throws Exception;
}
