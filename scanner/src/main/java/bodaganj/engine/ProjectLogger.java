package bodaganj.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;

/**
 * Created by Bogdan_Ganzha on 3/1/2015.
 */
public final class ProjectLogger {

    private ProjectLogger() {
    }

    public static Logger getLogger(final String className) {
        ch.qos.logback.classic.Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(className);
        log.setLevel(Level.toLevel(System.getProperty("logging.level")));
        return log;
    }
}
