package bodaganj.pages.soccerWay;

import bodaganj.pages.AbstractPage;
import net.thucydides.core.annotations.findby.By;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by bogdan on 12.04.16.
 * Test framework
 */
public class CompetitionsPage extends AbstractPage {

	@FindBy(xpath = "(.//*[@class='expandable loaded expanded']//li)[1]")
	private WebElementFacade topDivisionOfSelectedCountryButton;

	public CompetitionsPage(final WebDriver driver) {
		super(driver);
	}

	public void clickOnSpecifiedCountry(final String countryName) {
		String countryTemplate = "//div[@class='content competitions index ']//li[contains(.,'%s')]";
		WebElement countryButton = getDriver().findElement(By.xpath(String.format(countryTemplate, countryName)));
		countryButton.click();
	}

	public void clickOnTopDibision() {
		topDivisionOfSelectedCountryButton.click();
	}
}
