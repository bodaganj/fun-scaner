package bodaganj.engine.dataItems;

import bodaganj.pages.AbstractPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by bogdan on 27.04.16.
 * Test framework
 */
public interface DataItem {

	AbstractPage getDriverDelegate();

	WebDriver getDriver();
}