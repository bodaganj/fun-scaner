package bodaganj.panels.soccerwayPanels;

import bodaganj.pages.AbstractPage;
import bodaganj.panels.AbstractPanel;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

/**
 * Created by bogdan on 09.04.16.
 * Test framework
 */
public class CompetitionsPopupPanel extends AbstractPanel {

	@FindBy(xpath = "(.//*[@class='col']//li)[2]")
	private WebElementFacade aplButton;

	@FindBy(xpath = "(.//*[@class='col']//li)[3]")
	private WebElementFacade serieAButton;

	@FindBy(xpath = "(.//*[@class='col']//li)[4]")
	private WebElementFacade primeraButton;

	@FindBy(xpath = "(.//*[@class='col']//li)[5]")
	private WebElementFacade bundesLigaButton;

	@FindBy(xpath = "(.//*[@class='col']//li)[10]")
	private WebElementFacade chempionshipButton;

	public CompetitionsPopupPanel(final WebElementFacade panelBaseLocation, final AbstractPage driverDelegate) {
		super(panelBaseLocation, driverDelegate);
	}

	public void clickApl() {
		aplButton.click();
	}

	public void clickSerieA() {
		serieAButton.click();
	}

	public void clickPrimera() {
		primeraButton.click();
	}

	public void clickBundesLiga() {
		bundesLigaButton.click();
	}

	public void clickChempionship() {
		chempionshipButton.click();
	}
}
