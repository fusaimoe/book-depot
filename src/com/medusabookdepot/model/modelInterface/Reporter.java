package com.medusabookdepot.model.modelInterface;

import java.util.Date;

public interface Reporter {
    public void buildReport(final Date dateBegin,final Date dateEnd, final String fileName);
}
