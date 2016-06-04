package bodaganj.utils.session;

/**
 * Created by bogdan on 04.06.16.
 * Test framework
 */
public enum SessionKey {

	COUNTRY("club's country");

	private final String text;

	/**
	 * Provides a string representation of given enumeration.
	 *
	 */

	private SessionKey(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
