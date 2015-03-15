package bodaganj.panels.football_ua_panels;

import bodaganj.panels.AbstractPanel;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.WebElementFacade;

import bodaganj.pages.AbstractPage;

// Created by bogdan on 01.03.15.

public class MainPanel extends AbstractPanel {

    @FindBy(xpath = ".//*[@class='col-right']")
    private WebElementFacade mainNewsListPanelBaseElement;

    @FindBy(xpath = ".//*[@class='col-right']")
    private WebElementFacade threeLastMainNewsPanelBaseElement;

    public MainPanel(final WebElementFacade panelBaseLocation, final AbstractPage driverDelegate) {
        super(panelBaseLocation, driverDelegate);
    }

    public MainNewsListPanel getMainNewsListPanel() {
        mainNewsListPanelBaseElement.waitUntilVisible();
        return new MainNewsListPanel(mainNewsListPanelBaseElement, getDriverDelegate());
    }

    public ThreeLastMainNewsPanel getThreeLastMainNewsPanel() {
        threeLastMainNewsPanelBaseElement.waitUntilVisible();
        return new ThreeLastMainNewsPanel(threeLastMainNewsPanelBaseElement, getDriverDelegate());
    }
}
