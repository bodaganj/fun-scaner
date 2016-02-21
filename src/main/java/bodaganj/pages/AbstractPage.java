package bodaganj.pages;

import bodaganj.engine.ProjectLogger;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.slf4j.Logger;

// Created by Bogdan_Ganzha on 3/1/2015.

public abstract class AbstractPage extends PageObject {

    private static final Logger LOG = ProjectLogger.getLogger(AbstractPage.class.getSimpleName());

    public AbstractPage(final WebDriver webDriver) {
        super(webDriver);
    }

    @WhenPageOpens
    public void eventOpened() {
        maximizeBrowser();
    }

    public void maximizeBrowser() {
        final Window browserWindow = getDriver().manage().window();
        browserWindow.maximize();
        LOG.info("Browser got maximized, checking dimensions...");
        Dimension actualDimension = browserWindow.getSize();
        LOG.info("Width: {}, height: {}", actualDimension.getWidth(), actualDimension.getHeight());
    }
}
