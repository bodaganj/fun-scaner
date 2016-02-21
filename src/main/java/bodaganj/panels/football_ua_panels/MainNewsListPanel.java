package bodaganj.panels.football_ua_panels;

import bodaganj.engine.ProjectLogger;
import bodaganj.pages.AbstractPage;
import bodaganj.panels.AbstractPanel;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.WebElementFacade;
import org.slf4j.Logger;

import java.util.List;

// Created by bogdan on 15.03.15.

public class MainNewsListPanel extends AbstractPanel {

    private static final Logger LOG = ProjectLogger.getLogger(MainNewsListPanel.class.getSimpleName());

    @FindBy(xpath = ".//li[@class='isportNews']")
    private List<WebElementFacade> isportNewsList;

    public MainNewsListPanel(final WebElementFacade panelBaseLocation, final AbstractPage driverDelegate) {
        super(panelBaseLocation, driverDelegate);
    }

    public void clickOnFirstIsportNews() {
        LOG.info("Click on \"{}\" news in main menu", isportNewsList.get(0).getText());
        isportNewsList.get(0).click();
    }
}
