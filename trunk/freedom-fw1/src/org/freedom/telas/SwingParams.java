package org.freedom.telas;

import java.awt.Font;

public class SwingParams {
	
	public static int FONT_SIZE_PAD = 12;
	
	public static int FONT_STYLE_PAD = Font.PLAIN;
	
	public static int FONT_STYLE_BOLD = Font.BOLD;
	
	public static String FONT_PAD = "Arial";
	
	public static Font getFontpad() {
		return new Font(FONT_PAD, FONT_STYLE_PAD, FONT_SIZE_PAD);
	}

	public static Font getFontbold() {
		return new Font(FONT_PAD, FONT_STYLE_BOLD, FONT_SIZE_PAD);
	}


}

