package org.freedom.infra.util.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * Factory para criação de objeto {@link FreedomLogger} .
 * 
 * @author Anderson Sanchez - 30/06/2008;
 */

public class FreedomLoggerFactory implements LoggerFactory {

	public FreedomLoggerFactory() { 
	}

	public Logger makeNewLoggerInstance(String name) {
		return new FreedomLogger(name);
	}
}
