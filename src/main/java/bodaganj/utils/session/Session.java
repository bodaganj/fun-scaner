package bodaganj.utils.session;

import bodaganj.engine.ProjectLogger;
import net.thucydides.core.SessionMap;
import net.thucydides.core.Thucydides;
import org.slf4j.Logger;

import java.util.Map;

import static java.lang.String.format;

/**
 * Created by bogdan on 04.06.16.
 * Test framework
 */
public final class Session {

	private static final Logger LOG = ProjectLogger.getLogger(Session.class.getSimpleName());

	private Session() {
	}

	/** Method will warn if multiple keys equal by ignoreCase exist in Session */
	public static Object get(final SessionKey key) {
		String byKey = key.toString();
		SessionMap<String, Object> session = Thucydides.getCurrentSession();
		Object exact = session.get(byKey);
		String warnMsg;
		if (exact == null) {
			warnMsg = format("Object was not matched by exact key [%s], and", byKey);
		} else {
			warnMsg = format("Object was matched by exact key [%s], but", byKey);
		}
		String anotherKey;
		for (Map.Entry<String, Object> mapEntry : session.entrySet()) {
			anotherKey = mapEntry.getKey();
			if (anotherKey.equalsIgnoreCase(byKey) && !anotherKey.equals(byKey)) {
				LOG.warn(warnMsg + " similar key exist in Session [{}] \nObject by this key: {}", mapEntry.getKey(),
						mapEntry.getValue());
			}
		}
		LOG.info(String.format("Get object by key: %s, object: %s", byKey, exact));
		return exact;
	}

	/** Get String value */
	public static String getS(final SessionKey key) {
		Object value = get(key);
		return value == null ? null : value.toString();
	}

	/** Get int value */
	public static Integer getI(final SessionKey key) {
		Object value = get(key);
		return value == null ? null : Integer.valueOf(value.toString());
	}

	/** Get double value */
	public static Double getD(final SessionKey key) {
		Object value = get(key);
		return value == null ? null : Double.valueOf(value.toString());
	}

	/** Get boolean value */
	public static Boolean getB(final SessionKey key) {
		Object value = get(key);
		return value == null ? null : Boolean.valueOf(value.toString());
	}

	/** Get value of type Class */
	public static <T extends Object> T get(final SessionKey key, final Class<T> type) {
		Object object = get(key);
		Class<?> objectClass = object.getClass();
		if (!objectClass.equals(type)) {
			LOG.warn("Class type of object in session (" + objectClass + ") is not as expected (" + type + ")!");
			return null;
		} else {
			return (T) object;
		}
	}


	public static void put(final SessionKey key, final Object value) {
		LOG.info(String.format("Put object by key: %s, object: %s", key, value));
		Thucydides.getCurrentSession().put(key.toString(), value);
	}

	/** get() will return null for this key again */
	public static void remove(final SessionKey key) {
		Thucydides.getCurrentSession().remove(key.toString());
	}

	public static void printSessionDump() {
		LOG.info("\n\n************************ session dump ************************\n");
		for (Map.Entry<String, Object> sessionEntry : Thucydides.getCurrentSession().entrySet()) {
			LOG.info(String.format("[%s]    [%s]", sessionEntry.getKey(), sessionEntry.getValue()));
		}
		LOG.info("\n************************ end session dump ************************\n");
	}

	public static boolean containsKey(final SessionKey key) {
		return Thucydides.getCurrentSession().containsKey(key.toString());
	}

	public static void clear() {
		LOG.debug("Print Session DUMP before clear");
		printSessionDump();
		Thucydides.getCurrentSession().clear();
		LOG.info("Current session data have been cleared");
	}
}
