package org.freedom.scriptlet;


public class ScriptFunctions {
	public static String getFormatString(String value, String mask) {
		if (value == null)
			return "";
		String text = "";
		int i2 = 0;
		try {
			if ((value.length() > 0) & (mask.length() > 0) && (mask.length() > value.length())) {
				char[] va = value.toCharArray();
				char[] ma = mask.toCharArray();
				for (int i = 0; i < value.length(); i++) {
					if (ma[i2] == '#') {
						text += va[i];
					} else {
						text += ma[i2];
						if (!Character.isDigit(ma[i2])) {
							text += va[i];
							i2++;
						}
					}
					i2++;
				}
			} else
				text = value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

}
