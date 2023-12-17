package utils;

import com.aventstack.extentreports.ExtentTest;
import org.testng.Reporter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

public class OwnLogger {
    private static BufferedWriter bw = null;
    private static FileWriter fw = null;
    public static StringBuffer stringBuffer = null;
    public static ExtentTest logger = null;

    private OwnLogger() {
    }

    public static synchronized void log(org.apache.log4j.Logger log, String logMessage, OwnLoggerLevel... logLevel) {
        if (0 == logLevel.length) {
            log.info(logMessage);
        } else {
            OwnLoggerLevel[] var1 = logLevel;
            int var2 = logLevel.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                OwnLoggerLevel level = var1[var3];
                switch (level) {
                    case debug:
                        log.info(logMessage);
                        log.debug(logMessage);
                        break;
                    case info:
                        log.info(logMessage);
                        break;
                    case reporter:
                        Reporter.log(logMessage, true);
                        break;
                    case warn:
                        log.warn(logMessage);
                        break;
                    case error:
                        log.error(logMessage);
                        break;
                    default:
                        log.error("Log level '" + level + "' is not supported by the framework.");
                }
            }
        }

    }

    public static synchronized void log(org.apache.log4j.Logger log, String logMessage, Throwable cause, OwnLoggerLevel... logLevel) {
        if (0 == logLevel.length) {
            log.info(logMessage, cause);
        } else {
            OwnLoggerLevel[] var4 = logLevel;
            int var5 = logLevel.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                OwnLoggerLevel level = var4[var6];
                switch (level) {
                    case debug:
                        log.debug(logMessage, cause);
                        break;
                    case info:
                        log.info(logMessage, cause);
                        break;
                    case reporter:
                        log.info(logMessage, cause);
                        StringWriter stackTrace = new StringWriter();
                        stackTrace.append(logMessage);
                        if (null != cause) {
                            stackTrace.append("<br>");
                            cause.printStackTrace(new PrintWriter(stackTrace));
                        }

                        Reporter.log(stackTrace.toString(), true);
                        break;
                    case warn:
                        log.warn(logMessage, cause);
                        break;
                    case error:
                        log.error(logMessage, cause);
                        break;
                    default:
                        log.error("Log level '" + level + "' is not supported by the framework.");
                }
            }
        }

    }

    public static ExtentTest getLogger() {
        return logger;
    }

    public static void setLogger(ExtentTest logger) {
        OwnLogger.logger = logger;
    }

    public static enum OwnLoggerLevel {
        debug,
        info,
        reporter,
        warn,
        error;

        private OwnLoggerLevel() {
        }
    }
}
