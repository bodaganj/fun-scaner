package bodaganj;

import bodaganj.engine.ProjectLogger;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.PropertiesFileLocalPreferences;
import org.apache.commons.lang.StringUtils;
import org.fest.assertions.api.Assertions;
import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Created by bogdan on 28.03.16.
 * Test framework
 */
public final class FunScannerProperties {

	private static final String SERENITY_COMMON_PROPERTY_FILE = "src/test/resources/properties/serenity.properties";
	private static final String PROJECT_PROPERTY_FILE = "src/test/resources/properties/common.properties";
	private static final Logger LOG = ProjectLogger.getLogger(FunScannerProperties.class.getSimpleName());
	private static EnvironmentVariables environmentVariables = Injectors.getInjector().getProvider
			(EnvironmentVariables.class).get();
	private static FunScannerProperties instance = new FunScannerProperties();
	private Properties properties;

	private FunScannerProperties() {
		this.properties = new Properties();
		loadSerenityPropertiesToEnvironment();
		loadPropertiesFromFiles(PROJECT_PROPERTY_FILE);
	}

	public static FunScannerProperties getInstance() {
		return instance;
	}

	private void loadSerenityPropertiesToEnvironment() {
		Properties localPreferences = new Properties();
		try (FileInputStream fileInputStream = new FileInputStream(SERENITY_COMMON_PROPERTY_FILE)) {
			localPreferences.load(fileInputStream);
			Enumeration propertyNames = localPreferences.propertyNames();
			while (propertyNames.hasMoreElements()) {
				String propertyName = (String) propertyNames.nextElement();
				String localPropertyValue = localPreferences.getProperty(propertyName);
				String currentPropertyValue = environmentVariables.getProperty(propertyName);
				if ((currentPropertyValue == null) && (localPropertyValue != null)) {
					environmentVariables.setProperty(propertyName, localPropertyValue);
					LOG.debug("System property {} was successfully added, --> {}", propertyName, localPropertyValue);
				}
			}
			PropertiesFileLocalPreferences propertiesFileLocalPreferences = new PropertiesFileLocalPreferences
					(environmentVariables);
			String absolutePath = propertiesFileLocalPreferences.getHomeDirectory().getAbsolutePath();
			LOG.debug("Absolute path to local properties file --> {}", absolutePath);
		} catch (IOException e) {
			LOG.error("error loading serenity properties", e);
		}
	}

	private void loadPropertiesFromFiles(final String... propertyFileNames) {
		for (String propertyFile : propertyFileNames) {
			propertiesFromFile(propertyFile);
		}
		afterPropertiesSet();
	}

	private void propertiesFromFile(final String fileName) {
		try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
			properties.load(fileInputStream);
			LOG.debug("System properties were successfully loaded, file: {}", fileName);
		} catch (IOException e) {
			LOG.error("error loading properties from file {}", fileName, e);
			Assertions.fail("Error occurs during loading properties!");
		}
	}

	private void afterPropertiesSet() {
		for (Map.Entry props : properties.entrySet()) {
			String key = String.valueOf(props.getKey());
			String value = String.valueOf(props.getValue());
			if (StringUtils.isBlank(System.getProperty(key))) {
				System.setProperty(key, value);
				LOG.debug("System property {} was successfully added: {}", key, value);
			}
		}
	}
}
