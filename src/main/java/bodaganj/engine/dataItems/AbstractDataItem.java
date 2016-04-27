package bodaganj.engine.dataItems;

import bodaganj.pages.AbstractPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by bogdan on 27.04.16.
 * Test framework
 */
public abstract class AbstractDataItem implements DataItem {

	private AbstractPage driverDelegate;

	public AbstractDataItem(final AbstractPage driverDelegate) {
		this.driverDelegate = driverDelegate;
	}

	@Override
	public AbstractPage getDriverDelegate() {
		return driverDelegate;
	}

	@Override
	public WebDriver getDriver() {
		return driverDelegate.getDriver();
	}
}
