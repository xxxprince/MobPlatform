/*
 * Created on 2006-9-12
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package cn.com.framework.rfc;

import java.util.Properties;
import java.io.*;
import java.util.*;

/**
 * @author Administrator
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OrderedProperties extends Properties {

	/**
	 *
	 */
	ArrayList orderedKeys = new ArrayList(); // store keys

	public OrderedProperties() {
		super();
	}

	/**
	 * @param defaults
	 */
	public OrderedProperties(Properties defaults) {
		super(defaults);
	}

	public synchronized Iterator getKeysIterator() {
		return orderedKeys.iterator();
	}

	public static OrderedProperties load(String name) throws IOException {
		OrderedProperties props = null;
		java.io.InputStream is = OrderedProperties.class.getResourceAsStream(name);
		if (null != is) {
			props = new OrderedProperties();
			props.load(is);
			return props;
		} else {
			if (!name.startsWith("/")) {
				return load("/" + name);
			} else {
				throw new IOException("Properties could not be loaded.");
			}
		}
	}

	public synchronized Object put(Object key, Object value) {
		Object obj = super.put(key, value);
		orderedKeys.add(key);
		return obj;
	}

	public synchronized Object remove(Object key) {
		Object obj = super.remove(key);
		orderedKeys.remove(key);
		return obj;
	}

}
