package bodaganj.utils;

import java.util.Locale;

/**
 * Created by bogdan on 22.03.16.
 */
public class OperationSystem {

	// cached result of OS detection
	private static OSType detectedOS;

	private OperationSystem() {
	}

	public static OSType getOperatingSystemType() {
		if (detectedOS == null) {
			String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			if ((os.contains("mac")) || (os.contains("darwin"))) {
				detectedOS = OSType.MacOS;
			} else if (os.contains("win")) {
				detectedOS = OSType.Windows;
			} else if (os.contains("nux")) {
				detectedOS = OSType.Linux;
			} else {
				detectedOS = OSType.Other;
			}
		}
		return detectedOS;
	}

	public enum OSType {
		Windows, MacOS, Linux, Other
	}
}
