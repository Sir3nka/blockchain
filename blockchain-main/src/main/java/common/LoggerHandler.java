package common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LoggerHandler {
    public static Logger logger = LogManager.getLogger(Logger.class);
}
