package bodaganj.pages;

import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.WebElementFacade;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import bodaganj.engine.ProjectLogger;
import bodaganj.panels.football_ua_panels.MainPanel;
import bodaganj.panels.football_ua_panels.MatchListPanel;
import bodaganj.panels.football_ua_panels.NewsListPanel;

/**
 * Created by bogdan on 01.03.15.
 */
@At(".*/football.ua")
@DefaultUrl("http://football.ua/")
public class FootballUaPage extends AbstractPage {

    private static final Logger LOG = ProjectLogger.getLogger(FootballUaPage.class.getSimpleName());

    @FindBy(xpath = "//*[@class='col-top']")
    private WebElementFacade mainPanelBaseElement;

    @FindBy(xpath = "(//*[@class='col-right'])[2]")
    private WebElementFacade newsListPanelBaseElement;

    @FindBy(xpath = "(//*[@class='col-left'])[2]")
    private WebElementFacade matchListPanelBaseElement;

    public FootballUaPage(final WebDriver driver) {
        super(driver);
    }

    public MainPanel getMainPanel() {
        mainPanelBaseElement.waitUntilVisible();
        return new MainPanel(mainPanelBaseElement, this);
    }

    public NewsListPanel getNewsListPanel() {
        newsListPanelBaseElement.waitUntilVisible();
        return new NewsListPanel(newsListPanelBaseElement, this);
    }

    public MatchListPanel getMatchListPanel() {
        matchListPanelBaseElement.waitUntilVisible();
        return new MatchListPanel(matchListPanelBaseElement, this);
    }
}
