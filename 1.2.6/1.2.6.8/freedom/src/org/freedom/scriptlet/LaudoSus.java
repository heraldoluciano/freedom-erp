package org.freedom.scriptlet;

import net.sf.jasperreports.engine.JRDefaultScriptlet;

public class LaudoSus extends JRDefaultScriptlet {
	public String getFormatString(String value, String mask) {
		return ScriptFunctions.getFormatString( value, mask );
	}
}
