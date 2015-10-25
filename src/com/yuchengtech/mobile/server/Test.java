package com.yuchengtech.mobile.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Test {

	public static void main(String[] args) {
		Properties p = new Properties();
		InputStream in = Test.class.getResourceAsStream("/a.properties");
		try {
			p.load(in);
			Enumeration<Object> elements = p.elements();
			while (elements.hasMoreElements()) {
				Object object = (Object) elements.nextElement();
				System.out.println(object);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
