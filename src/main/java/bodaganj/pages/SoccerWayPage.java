package bodaganj.pages;

import bodaganj.panels.soccerway_panels.FooterPanel;
import bodaganj.panels.soccerway_panels.HeaderPanel;
import bodaganj.panels.soccerway_panels.MainPanel;
import bodaganj.panels.soccerway_panels.NavigationPanel;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;

/**
 * Created by bogdan on 09.04.16.
 * Test framework
 */
@DefaultUrl("http://int.soccerway.com/")
@At(".*/int.soccerway.com")
public class SoccerWayPage extends AbstractPage {

	@FindBy(xpath = ".//*[@id='site-header']")
	private WebElementFacade headerPanelBaseElement;

	@FindBy(xpath = ".//*[@id='main']/ul")
	private WebElementFacade navigationPanelBaseElement;

	@FindBy(xpath = ".//*[@id='navbar-left']")
	private WebElementFacade mainPanelBaseElement;

	@FindBy(xpath = ".//*[@id='ft']")
	private WebElementFacade footerPanelBaseElement;

	public SoccerWayPage(final WebDriver driver) {
		super(driver);
	}

	public HeaderPanel getHeaderPanel() {
		headerPanelBaseElement.waitUntilVisible();
		return new HeaderPanel(headerPanelBaseElement, this);
	}

	public NavigationPanel getNavigationPanel() {
		navigationPanelBaseElement.waitUntilVisible();
		return new NavigationPanel(navigationPanelBaseElement, this);
	}

	public MainPanel getMainPanel() {
		headerPanelBaseElement.waitUntilVisible();
		return new MainPanel(headerPanelBaseElement, this);
	}

	public FooterPanel getFooterPanel() {
		headerPanelBaseElement.waitUntilVisible();
		return new FooterPanel(headerPanelBaseElement, this);
	}
}
