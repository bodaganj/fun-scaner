package bodaganj.utils;

import java.util.Locale;

/**
 * Created by bogdan on 22.03.16.
 */
public final class OperationSystem {

	// cached result of OS detection
	private static OSType detectedOS;

	private OperationSystem() {
	}

	public static OSType getOperatingSystemType() {
		if (detectedOS == null) {
			String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			if ((os.contains("mac")) || (os.contains("darwin"))) {
				detectedOS = OSType.MAC_OS;
			} else if (os.contains("win")) {
				detectedOS = OSType.WINDOWS;
			} else if (os.contains("nux")) {
				detectedOS = OSType.LINUX;
			} else {
				detectedOS = OSType.OTHER;
			}
		}
		return detectedOS;
	}

	public enum OSType {
		WINDOWS, MAC_OS, LINUX, OTHER
	}
}
