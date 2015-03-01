package bodaganj.pages;

import bodaganj.panels.MainPanel;
import bodaganj.panels.MatchListPanel;
import bodaganj.panels.NewsListPanel;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;

/**
 * Created by bogdan on 01.03.15.
 */
@DefaultUrl("http://football.ua/")
public class FootballUaPage extends PageObject {

    @FindBy(xpath = "//*[@class='col-top']")
    private WebElementFacade mainPanelBaseElement;

    @FindBy(xpath = "//*[@class='col-right']")
    private WebElementFacade newsListPanelBaseElement;

    @FindBy(xpath = "//*[@class='col-left']")
    private WebElementFacade matchListPanelBaseElement;

    public MainPanel getMainPanel() {
        mainPanelBaseElement.waitUntilVisible();
        return new MainPanel(getDriver(), mainPanelBaseElement);
    }

    public NewsListPanel getNewsListPanel() {
        mainPanelBaseElement.waitUntilVisible();
        return new NewsListPanel(getDriver(), mainPanelBaseElement);
    }

    public MatchListPanel getMatchListPanel() {
        mainPanelBaseElement.waitUntilVisible();
        return new MatchListPanel(getDriver(), mainPanelBaseElement);
    }
}
