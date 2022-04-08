package tests.supers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import applogic.ApplicationManager1;
import util.GenUtils;
import util.ParamsUtils;

public class TestBaseApi extends TestBase {

	public Logger log = Logger.getLogger("SeleniumLog");

	@Override
	@BeforeMethod(alwaysRun = true)
	public void init() {
		try {
			testName = this.getClass().getName().replaceAll(".*\\.", "");
			pkg = this.getClass().getPackage().toString().replaceAll(".*\\.", "");
			String[] pkgArray = this.getClass().getPackage().toString().split("\\.");
			section = pkgArray.length > 1 ? pkgArray[1] : pkgArray[0].substring(8);
			app = new ApplicationManager1(false, testName, pkg);
			initLog4j();
			//initDirPath();
			//app.initResources(false, log);
			startTest();
		} catch (Throwable t) {
			startTest();
			log.fatal("Environment server unavailable, or application run/configuration failure.");
			onTestFailure(t);
		}
	}

	@Override
	protected void initLog4j() {
		log = Logger.getLogger(testName);
		log.removeAllAppenders();

		String ts = GenUtils.getCurrentTimestamp(GenUtils.TIME_FORMAT_SSS);


		String path = app.getParamsUtils().getPropValue(ParamsUtils.WORKSPACE) + File.separator + LOG_DIR + File.separator + pkg + File.separator
				+ testName + ts + ".log";

		URL loggerprops = Thread.currentThread().getContextClassLoader().getResource("log4j.properties");
		PropertyConfigurator.configure(loggerprops);

		try {
			FileAppender appender = new FileAppender(new PatternLayout("%-5p %d{HH:mm:ss} %x (%F:%L) - %m%n"), path, false);
			log.addAppender(appender);
		} catch (IOException ioe) {
			Assert.fail("Failed to open log file: " + path + "\n" + ioe);
		}

		ConsoleAppender ca = new ConsoleAppender();
		ca.setWriter(new OutputStreamWriter(System.out));
		ca.setLayout(new PatternLayout("%-5p %d{HH:mm:ss} %x (%F:%L) - %m%n"));
		log.addAppender(ca);

		log.info("Writing debug log to: " + path);
	}


	@Override
	protected void onTestFailure(Throwable t) {
		log.fatal(t.getMessage());
		t.printStackTrace();
		endTestFailure();
		Assert.fail(t.getMessage());
	}

	@Override
	protected void endTestSuccess() {
		endTime = new Date();
		log.info("*************************************************************************");
		log.info("The '" + testName + "' test has ended successfully.");
		log.info("Start time: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(startTime.getTime())));
		log.info("End time:       " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(endTime.getTime())) + ".");
		log.info("-------------------------------------------------------------------------");
		log.info("Total run time: " + getTestRunTime());
		log.info("*************************************************************************");
	}

	@Override
	protected void endTestFailure() {
		endTime = new Date();
		log.info("*************************************************************************");
		log.info("The '" + testName + "' test has ended with error.");
		log.info("Start time: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(startTime.getTime())));
		log.info("End time:       " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(endTime.getTime())) + ".");
		log.info("-------------------------------------------------------------------------");
		log.info("Total run time: " + getTestRunTime());
		log.info("*************************************************************************");
	}

}
