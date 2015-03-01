package bodaganj.pages;

import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.WebElementFacade;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import bodaganj.engine.ProjectLogger;
import bodaganj.panels.MainPanel;
import bodaganj.panels.MatchListPanel;
import bodaganj.panels.NewsListPanel;

/**
 * Created by bogdan on 01.03.15.
 */
@At(".*/football.ua")
@DefaultUrl("http://football.ua/")
public class FootballUaPage extends AbstractPage {

    private static final Logger LOG = ProjectLogger.getLogger(FootballUaPage.class.getSimpleName());

    @FindBy(xpath = "//*[@class='col-top']")
    private WebElementFacade mainPanelBaseElement;

    @FindBy(xpath = "//*[@class='col-right']")
    private WebElementFacade newsListPanelBaseElement;

    @FindBy(xpath = "//*[@class='col-left']")
    private WebElementFacade matchListPanelBaseElement;

    public FootballUaPage(WebDriver driver) {
        super(driver);
    }

    public MainPanel getMainPanel() {
        mainPanelBaseElement.waitUntilVisible();
        return new MainPanel(mainPanelBaseElement, this);
    }

    public NewsListPanel getNewsListPanel() {
        mainPanelBaseElement.waitUntilVisible();
        return new NewsListPanel(mainPanelBaseElement, this);
    }

    public MatchListPanel getMatchListPanel() {
        mainPanelBaseElement.waitUntilVisible();
        return new MatchListPanel(mainPanelBaseElement, this);
    }
}
