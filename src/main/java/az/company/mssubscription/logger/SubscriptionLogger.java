package az.company.mssubscription.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record SubscriptionLogger(Logger logger) {
    public static SubscriptionLogger getLogger(Class<?> clazz) {
        var logger = LoggerFactory.getLogger(clazz);
        return new SubscriptionLogger(logger);
    }

    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    public void trace(String format, Object... args) {
        if (logger.isTraceEnabled()) logger.trace(format, args);
    }

    public void debug(String format, Object... args) {
        if (logger.isDebugEnabled()) logger.debug(format, args);
    }

    public void warn(String format, Object... args) {
        if (logger.isWarnEnabled()) logger.warn(format, args);
    }

    public void error(String format, Object... args) {
        if (logger.isErrorEnabled()) logger.error(format, args);
    }
}