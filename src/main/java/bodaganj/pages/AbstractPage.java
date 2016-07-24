package bodaganj.pages;

import bodaganj.engine.ProjectLogger;
import bodaganj.engine.dataItems.DataItemsFactory;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.WhenPageOpens;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.slf4j.Logger;

// Created by Bogdan_Ganzha on 3/1/2015.

public abstract class AbstractPage extends PageObject {

	private static final Logger LOG = ProjectLogger.getLogger(AbstractPage.class.getSimpleName());

	private DataItemsFactory dataItemsFactory;

	public AbstractPage(final WebDriver webDriver) {
		super(webDriver);
		dataItemsFactory = DataItemsFactory.getInstance();
	}

	@WhenPageOpens
	public void eventOpened() {
		maximizeBrowser();
	}

	public DataItemsFactory getDataItemsFactory() {
		return dataItemsFactory;
	}

	public void maximizeBrowser() {
		final Window browserWindow = getDriver().manage().window();
		browserWindow.maximize();
		LOG.info("Browser got maximized, checking dimensions...");
		Dimension actualDimension = browserWindow.getSize();
		LOG.info("Width: {}, height: {}", actualDimension.getWidth(), actualDimension.getHeight());
	}
}
