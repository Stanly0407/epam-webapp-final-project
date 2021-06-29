package com.epam.web.tags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class DateTimeFormatTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(DateTimeFormatTag.class);

    private String dateTime;

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            if (dateTime != null && dateTime.length() <= 19) {
                String correctDate = dateTime.substring(0, 10);
                String correctTime = dateTime.substring(11, 16);
                pageContext.getOut().write("<p class=\"p-comment-date\">" + correctDate + "   " + correctTime + "</p>");
            } else {
                LOGGER.debug("Date formatting error " + dateTime);
                pageContext.getOut().write("<p class=\"p-comment-date\">" + dateTime + "</p>");
            }

        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
