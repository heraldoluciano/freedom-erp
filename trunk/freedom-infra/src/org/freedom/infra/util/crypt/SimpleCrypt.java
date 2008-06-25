package org.freedom.infra.util.crypt;

public class SimpleCrypt {
	
	public static String crypt(String text) {
		StringBuilder buffer = new StringBuilder();
		char character = (char) 0;
		if (text!=null) {
			for (int i=0; i<text.length(); i++) {
				character = (char) ( ( (int) text.charAt(i)) * 2 - i );
				buffer.append(character);
			}
		}
		return buffer.toString();
	}
	
	public static String decrypt(String text) {
		StringBuilder buffer = new StringBuilder();
		char character = (char) 0;
		if (text!=null) {
			for (int i=0; i<text.length(); i++) {
				character = (char) ( ( ( (int) text.charAt(i) ) + i) / 2 );
				buffer.append(character);
			}
		}
		return buffer.toString();
	}
}
