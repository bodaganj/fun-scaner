package bodaganj.panels.soccerwayPanels;

import bodaganj.pages.AbstractPage;
import bodaganj.panels.AbstractPanel;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * Created by bogdan on 09.04.16.
 * Test framework
 */
public class NavigationPanel extends AbstractPanel {

	@FindBy(xpath = ".//a[contains(.,'Fixtures/Results')]")
	private WebElementFacade fixturesResultsButton;

	@FindBy(xpath = ".//a[contains(.,'Competitions')]")
	private WebElementFacade competitionsButton;

	@FindBy(xpath = ".//a[contains(.,'Teams')]")
	private WebElementFacade teamsButton;

	@FindBy(xpath = ".//a[contains(.,'Transfers')]")
	private WebElementFacade transfersButton;

	@FindBy(xpath = ".//a[contains(.,'More')]")
	private WebElementFacade moreButton;

	@FindBy(xpath = ".//*[@class='submenu col_3 clearfix']")
	private WebElementFacade competitionsPopupPanel;

	public NavigationPanel(final WebElementFacade panelBaseLocation, final AbstractPage driverDelegate) {
		super(panelBaseLocation, driverDelegate);
	}

	public void clickFixturesResultsButton() {
		fixturesResultsButton.click();
	}

	public void clickCompetitionsButton() {
		competitionsButton.click();
	}

	public void clickTeamsButton() {
		teamsButton.click();
	}

	public void clickTransfersButton() {
		transfersButton.click();
	}

	public void clickMoreButton() {
		moreButton.click();
	}

	public CompetitionsPopupPanel hoverCompetitionsButton() {
		Actions action = new Actions(getDriver());
		action.moveToElement(competitionsButton).build().perform();
		competitionsPopupPanel.waitUntilVisible();
		return new CompetitionsPopupPanel(competitionsPopupPanel, getDriverDelegate());
	}

	public void hoverTeamsButton() {
		Actions action = new Actions(getDriver());
		action.moveToElement(teamsButton).build().perform();
	}

	public void hoverMoreButton() {
		Actions action = new Actions(getDriver());
		action.moveToElement(moreButton).build().perform();
	}
}
