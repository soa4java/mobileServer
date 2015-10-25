 

package com.yuchengtech.mobile.server.security.keys;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

public class ObjectAttributes extends Object {

	public Hashtable transAttributes = null;
	public Hashtable attributes = null;
	private String nameSeed = null;
	private boolean loaded = false;
	private int tested = 99;
	private PrintStream output = System.out;
	private static final int HASH_SIZE = 213;

	public ObjectAttributes() {
		init();

	}

	private ObjectAttributes(String n) {
		init(n);
	}

	public static ObjectAttributes createObjectAttributes(Class c) {

		return createObjectAttributes(c.getName());

	}

	public static ObjectAttributes createObjectAttributes(Object o) {

		return createObjectAttributes(o.getClass());

	}

	public static ObjectAttributes createObjectAttributes(String n) {
		ObjectAttributes oa = new ObjectAttributes(n);

		return oa;
	}

	private void init() {
		transAttributes = new Hashtable(HASH_SIZE);
		attributes = new Hashtable(HASH_SIZE);

	}

	private void init(String n) {
		// nameSeed = Utility.shortClassName(n);

		init();

		nameSeed = n;

		loaded = initializeFromProps();

		// load Property bundle - if one exists ...
	}

	protected boolean initializeFromProps() {
		ResourceBundle attributesBundle;
		String init = null;

		//
		try {
			attributesBundle = ResourceBundle.getBundle(nameSeed);
		} catch (MissingResourceException ex) {
			try {

				attributesBundle = ResourceBundle
						.getBundle(shortClassName(nameSeed));
			} catch (MissingResourceException nex) {
				// System.err.println(nex);
				return false;
			}
		}

		Enumeration e = attributesBundle.getKeys();
		while (e.hasMoreElements()) {
			String value;
			String key = (String) e.nextElement();

			value = System.getProperty(key);
			if (empty(value) == true)
				value = attributesBundle.getString(key);

			Object ovalue = convertToObject(value);
			attributes.put(key.toUpperCase(), ovalue);
		}

		return true;
	}

	public static boolean empty(String str) {
		if (str == null || str.length() == 0)
			return true;
		return false;
	}

	public static String shortClassName(String name) {
		return name.substring(name.lastIndexOf(".") + 1);
	}

	public static String shortClassName(Object object) {
		return shortClassName(object.getClass().getName());
	}

	private Object convertToObject(String s) {
		Object o = null;

		try {

			o = Integer.valueOf(s);

		} catch (NumberFormatException ex) {
			;
		}

		if (o == null) {
			try {

				o = Float.valueOf(s);
				// System.out.println("Float " + o);
			} catch (NumberFormatException ex) {
				;
			}
		}

		if (o == null) {

			// System.out.println("o == null / s = : " + s);
			if (s.equalsIgnoreCase("true")) {
				o = new Boolean(true);
			} else if (s.equalsIgnoreCase("false")) {
				o = new Boolean(false);
			} else
				o = s;
		}

		return o;
	}

	// methods to satisfy Attributable interface
	public Object getAttribute(String key) {
		Object o;

		if (attributes.containsKey(key.toUpperCase()) == false) {
			o = null;
		}
		o = attributes.get(key.toUpperCase());

		return o;
	}

	public String getString(String key, Object[] args) {
		return MessageFormat.format((String) getAttribute(key), args);
	}

	public String getString(String key) {
		return (String) getAttribute(key);
	}

	public Object getTransAttribute(String key) {
		Object o;

		if (transAttributes.containsKey(key.toUpperCase()) == false) {
			o = null;
		}
		o = transAttributes.get(key.toUpperCase());

		return o;
	}

	public void setTransAttribute(String key, Object value) {
		String sinit = "";
		if (value == null)
			value = sinit;

		Object ovalue = null;
		if (value instanceof String)
			ovalue = convertToObject((String) value);
		else
			ovalue = value;

		transAttributes.put(key.toUpperCase(), ovalue);
		return;
	}

	public List getKeys() { // list all keys - persistent and otherwise ...
		Enumeration e = attributes.keys();

		List list = new Vector(25);
		while (e.hasMoreElements()) {
			list.add(e.nextElement());
		}

		e = transAttributes.keys();

		while (e.hasMoreElements()) {
			list.add(e.nextElement());
		}

		return list;
	}

	public void loadAttributes() {
		loaded = initializeFromProps();
		return;
	}

	// / end

	public String toString() {

		StringBuffer buffer = new StringBuffer(2048);

		buffer.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		buffer.append("Static Attributes: ");
		buffer.append(attributes.toString() + "\n");
		buffer.append("Transient Attributes: ");
		buffer.append(transAttributes.toString() + "\n");
		buffer.append("***loaded***: " + loaded + "\n");
		buffer.append("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

		return buffer.toString();
	}

	static public void main(String[] args) {

		// create a new based on current class
		ObjectAttributes attr = ObjectAttributes
				.createObjectAttributes(ObjectAttributes.class);
		attr.setTransAttribute("TransTest", new Date());
		System.out.println(attr.toString());

		attr = ObjectAttributes.createObjectAttributes("Test");
		System.out.println(attr.toString());

		System.exit(0);
	}

}
