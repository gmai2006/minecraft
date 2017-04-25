package com.tomcat.hosting.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.stringtemplate.v4.AttributeRenderer;

public class DateRenderer implements AttributeRenderer {
    public String toString(Object o) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
        return f.format(((Calendar)o).getTime());
    }

	@Override
	public String toString(Object o, String formatString, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}
}
