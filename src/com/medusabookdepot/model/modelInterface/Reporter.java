package com.medusabookdepot.model.modelInterface;

import java.io.File;
import java.util.Date;

public interface Reporter {
    public void buildReport(final Date dateBegin,final Date dateEnd, final String fileName);

    void sendEmail(String from, String to);

    boolean convertTextToPDF(File file) throws Exception;
}
