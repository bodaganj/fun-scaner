package bodaganj.pages.soccerWay;

import bodaganj.engine.dataItems.items.FootballClubItem;
import bodaganj.pages.AbstractPage;
import bodaganj.utils.LeagueClubStatus;
import net.thucydides.core.annotations.findby.By;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bogdan on 12.04.16.
 * Test framework
 */
public class CompetitionsPage extends AbstractPage {

	@FindBy(xpath = "(.//*[@class='expandable loaded expanded']//li)[1]")
	private WebElementFacade topDivisionOfSelectedCountryButton;

	@FindBy(xpath = "//div[@class='block_competition_tables real-content clearfix ']")
	private WebElementFacade leagueTableBase;

	public CompetitionsPage(final WebDriver driver) {
		super(driver);
	}

	public void clickOnSpecifiedCountry(final String countryName) {
		String countryTemplate = "//div[@class='content competitions index ']//li[contains(.,'%s')]";
		WebElement countryButton = getDriver().findElement(By.xpath(String.format(countryTemplate, countryName)));
		countryButton.click();
	}

	public void clickOnTopDivision() {
		topDivisionOfSelectedCountryButton.click();
	}

	public List<FootballClubItem> getChampionsLeaguePotentialParticipants() {
		return getCertainClubs(LeagueClubStatus.CHAMPIONS_LEAGUE, LeagueClubStatus.CHAMPIONS_LEAGUE_QUALIFIERS);
	}

	public List<FootballClubItem> getEuropaLeaguePotentialParticipents() {
		return getCertainClubs(LeagueClubStatus.EUROPA_LEAGUE_QUALIFIERS, LeagueClubStatus.EUROPA_LEAGUE);
	}

	public List<FootballClubItem> getRelegationPotentialParticipents() {
		return getCertainClubs(LeagueClubStatus.RELEGATION, LeagueClubStatus.RELEGATION_QUALIFIERS);
	}

	private List<FootballClubItem> getAllFootballClubs() {
		return getDataItemsFactory().getElementsNamed(FootballClubItem.class,
				leagueTableBase.thenFindAll(".//tr[contains(@class,'team_rank')]"), this);
	}

	private List<FootballClubItem> getCertainClubs(final LeagueClubStatus... status) {
		List<LeagueClubStatus> statusList = Arrays.asList(status);
		List<FootballClubItem> certainClubs = new ArrayList<>();
		for (FootballClubItem club : getAllFootballClubs()) {
			if (statusList.contains(club.getLeagueClubStatus())) {
				certainClubs.add(club);
			}
		}
		return certainClubs;
	}
}
