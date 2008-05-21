package org.freedom.infra.model.jpa;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Key implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int COL_KEY = 0;
	public static final int COL_VALUE = 1;
	private Object[][] keys = null;
	private String internalKey = null;
	
	public Key(Object[][] keys) {
		setKeys(keys);
	}

	public Object[][] getKeys() {
		return keys;
	}

	public void setKeys(Object[][] keys) {
		this.keys = keys;
		if (keys!=null) {
			StringBuilder buffer = new StringBuilder();
			for (int i=0; i<keys.length; i++) {
			   Object key = keys[i][COL_KEY];	
			   Object value = keys[i][COL_VALUE];
			   try {
				   Method method = this.getClass().getMethod("set" + key, value.getClass());
				   method.invoke(this, value);
			   } catch (NoSuchMethodException e) {
				   e.printStackTrace(); 
			   } catch (InvocationTargetException e) {
				   e.printStackTrace();
			   } catch (IllegalAccessException e) {
				   e.printStackTrace();
			   }
               buffer.append(value.toString());
               buffer.append(" ");
			}
		    setInternalKey( buffer.toString() );
		}
		
	}

	public int hashCode() {
		int hashCode = 0;
		if ( (keys!=null) && (internalKey!=null) ) {
			hashCode = internalKey.hashCode();
			//hashCode = keys.hashCode();
		}
		return hashCode;
	}

	public String getInternalKey() {
		return internalKey;
	}

	public void setInternalKey(String internalKey) {
		this.internalKey = internalKey;
	}

}
