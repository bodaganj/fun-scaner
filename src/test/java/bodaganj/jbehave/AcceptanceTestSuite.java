package bodaganj.jbehave;

import bodaganj.engine.ProjectLogger;
import bodaganj.utils.OperationSystem;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.jbehave.ThucydidesJUnitStories;
import org.slf4j.Logger;

public class AcceptanceTestSuite extends ThucydidesJUnitStories {

	private static final Logger LOG = ProjectLogger.getLogger(AcceptanceTestSuite.class.getSimpleName());
	private static final String X64_ARCH = "amd64";
	private static final String OS_ARCH = "os.arch";
	private static final String CHROME_DRIVER = "webdriver.chrome.driver";
	private static final String PHANTOMJS_DRIVER = "phantomjs.binary.path";
	private static final String IE_DRIVER = "webdriver.ie.driver";
	private static EnvironmentVariables environmentVariables = Injectors.getInjector()
			.getProvider(EnvironmentVariables.class).get();

	public AcceptanceTestSuite() {
		try {
			Class.forName("bodaganj.FunScanerProperties");
		} catch (ClassNotFoundException e) {
			LOG.error("Error instantiating FunScanerProperties", e);
		}
		setDriverAccordingToOS();
	}

	private void setDriverAccordingToOS() {
		OperationSystem.OSType osType = OperationSystem.getOperatingSystemType();
		switch (osType) {
			case Windows:
				setChromeDriverWindows();
				setPhantomJSDriverWindows();
				if (X64_ARCH.equals(System.getProperty(OS_ARCH))) {
					setIeDriverWindows64();
				} else {
					setIeDriverWindows32();
				}
				break;
			case MacOS:
				setChromeDriverOsx();
				setPhantomJSDriverOsx();
				break;
			case Linux:
				if (X64_ARCH.equals(System.getProperty(OS_ARCH))) {
					setChromeDriverLinux64();
//					setPhantomJSDriverLinux64();
				} else {
					setChromeDriverLinux32();
					setPhantomJSDriverLinux32();
				}
				break;
			case Other:
				LOG.error("Can't define OS");
				break;
		}
	}

	public void setChromeDriverLinux64() {
		System.setProperty(CHROME_DRIVER, "drivers/chromedriver.exe");
	}

	//TODO to be added all drivers mentioned below
	public void setChromeDriverLinux32() {
		System.setProperty(CHROME_DRIVER, "drivers/linux/32bit/chromedriver");
	}

	public void setChromeDriverWindows() {
		System.setProperty(CHROME_DRIVER, "drivers/windows/chromedriver.exe");
	}

	public void setChromeDriverOsx() {
		System.setProperty(CHROME_DRIVER, "drivers/osx/chromedriver");
	}

	public void setPhantomJSDriverLinux32() {
		System.setProperty(PHANTOMJS_DRIVER, "drivers/linux/32bit/phantomjs");
	}

	public void setPhantomJSDriverLinux64() {
		System.setProperty(PHANTOMJS_DRIVER, "drivers/linux/64bit/phantomjs");
	}

	public void setPhantomJSDriverWindows() {
		System.setProperty(PHANTOMJS_DRIVER, "drivers/windows/phantomjs.exe");
	}

	public void setPhantomJSDriverOsx() {
		System.setProperty("webdriver.phantomjs.driver", "drivers/osx/phantomjs");
	}

	public void setIeDriverWindows32() {
		System.setProperty(IE_DRIVER, "drivers/windows/32bit/iedriver.exe");
	}

	public void setIeDriverWindows64() {
		System.setProperty(IE_DRIVER, "drivers/windows/64bit/iedriver.exe");
	}
}
