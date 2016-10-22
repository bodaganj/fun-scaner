package bodaganj.engine;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Created by Bogdan_Ganzha on 3/1/2015.

public final class ProjectLogger {

	private static ch.qos.logback.classic.Logger log;

	private ProjectLogger() {
	}

	public static Logger getLogger(final String className) {
		if (log == null) {
			log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(className);
			log.setLevel(Level.toLevel(System.getProperty("logging.level")));
		}
		return log;
	}
}
