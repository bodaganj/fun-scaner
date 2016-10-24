package bodaganj.pages;

import bodaganj.engine.ProjectLogger;
import bodaganj.engine.dataItems.DataItemsFactory;
import com.google.common.base.Predicate;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;

import java.util.Date;
import java.util.concurrent.TimeUnit;

// Created by Bogdan_Ganzha on 3/1/2015.

public abstract class AbstractPage extends PageObject {

	private static final Logger LOG = ProjectLogger.getLogger(AbstractPage.class.getSimpleName());
	private static final String CHECK_AJAX_SCRIPT = "return window.jQuery != undefined && jQuery.active == 0;";
	private static final String DOCUMENT_STATE = "return document.readyState";

	private final EnvironmentVariables environmentVariables;
	private DataItemsFactory dataItemsFactory;

	public AbstractPage(final WebDriver webDriver) {
		super(webDriver);
		environmentVariables = Injectors.getInjector().getProvider(EnvironmentVariables.class).get();
		dataItemsFactory = DataItemsFactory.getInstance();
	}

	@WhenPageOpens
	public void eventOpened() {
		final Window browserWindow = getDriver().manage().window();
		browserWindow.maximize();
		LOG.info("Browser got maximized, checking dimensions...");
		Dimension actualDimension = browserWindow.getSize();
		LOG.info("Width: {}, height: {}", actualDimension.getWidth(), actualDimension.getHeight());
	}

	public DataItemsFactory getDataItemsFactory() {
		return dataItemsFactory;
	}

	public void waitForPageReadyStateComplete(final int timeout) {
		final Date startTime = new Date();
		final FluentWait<WebDriver> wait = new FluentWait<>(getDriver());
		wait.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(timeout, TimeUnit.SECONDS);
		try {
			wait.until((WebDriver driver) -> {
				String state = (String) evaluateJavascript(DOCUMENT_STATE);
				LOG.debug("loading state is {}", state);
				return "complete".equals(state);
			});
			final Date endTime = new Date();
			LOG.debug("{} additional loading time took about {} milliseconds", getClass().getSimpleName(),
					endTime.getTime() - startTime.getTime());
		} catch (org.openqa.selenium.TimeoutException te) {
			LOG.error("Page loading wait exceeded timeout, proceeding with test anyway\nmessage: ", te);
		}
	}

	public void waitForAjaxToComplete(final Integer timeout) {
		final Date startTime = new Date();
		final FluentWait<WebDriver> wait = new FluentWait<>(getDriver());
		wait.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(timeout, TimeUnit.SECONDS);
		try {
			wait.until((WebDriver driver) -> (Boolean) evaluateJavascript(CHECK_AJAX_SCRIPT));
			final Date endTime = new Date();
			LOG.debug("ajax execution on page took about {} milliseconds", endTime.getTime() - startTime.getTime());
		} catch (org.openqa.selenium.TimeoutException te) {
			LOG.error("ajax wait exceeded timeout, proceeding with test anyway\nmessage: ", te);
		}
	}

	/**
	 * Logic: waits for some element to appear without timeoutException.
	 * If found  - wait for this element to disappear.
	 */
	public void waitForProcessing(final WebElementFacade element, final int timeoutBefore, final int timeoutAfter) {
		LOG.debug("looking for some processing indicator element");
		long initial = Long.valueOf(environmentVariables.getPropertyAsInteger("webdriver.timeouts" +
				".implicitlywait", 10000));
		WebDriver.Timeouts timeouts = getDriver().manage().timeouts();
		timeouts.implicitlyWait(200, TimeUnit.MILLISECONDS);
		FluentWait<WebElementFacade> waitBefore = new FluentWait<>(element);
		waitBefore.pollingEvery(250, TimeUnit.MILLISECONDS).withTimeout(timeoutBefore, TimeUnit.SECONDS);
		try {
			waitBefore.until(isVisibleElement(true));
		} catch (TimeoutException e) {
			LOG.debug("Not appeared exception: ", e);
			LOG.debug("not appeared after {} seconds, skipping", timeoutBefore);
			timeouts.implicitlyWait(initial, TimeUnit.MILLISECONDS);
			return;
		}
		LOG.debug("appeared");
		FluentWait<WebElementFacade> waitAfter = new FluentWait<>(element);
		waitAfter.pollingEvery(250, TimeUnit.MILLISECONDS).withTimeout(timeoutAfter, TimeUnit.SECONDS);
		waitAfter.until(isVisibleElement(false));
		LOG.debug("wait complete");
		timeouts.implicitlyWait(initial, TimeUnit.MILLISECONDS);
	}

	private Predicate<WebElementFacade> isVisibleElement(final Boolean shouldBeVisible) {
		return element -> shouldBeVisible.equals(element.isCurrentlyVisible());
	}
}
