package bodaganj.utils;

/**
 * Created by bogdan on 09.04.16.
 * Test framework
 */
public enum NavigationOptions {

	FIXTURES_RESULTS("FIXTURES/RESULTS"),
	COMPETITIONS("COMPETITIONS"),
	TEAMS("TEAMS"),
	TRANSFERS("TRANSFERS"),
	MORE("MORE");

	private String navigationOption;

	NavigationOptions(final String navigationOption) {
		this.navigationOption = navigationOption;
	}

	public String getNavigationOption() {
		return navigationOption;
	}
}
