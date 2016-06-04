package bodaganj.engine.dataItems.items;

import bodaganj.engine.ProjectLogger;
import bodaganj.engine.dataItems.AbstractDataItem;
import bodaganj.pages.AbstractPage;
import bodaganj.utils.LeagueClubStatus;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.WebElementFacade;
import org.slf4j.Logger;

/**
 * Created by bogdan on 27.04.16.
 * Test framework
 */
public class FootballClubItem extends AbstractDataItem {

	private static final Logger LOG = ProjectLogger.getLogger(FootballClubItem.class.getSimpleName());

	@FindBy(xpath = ".//td[contains(@class,'rank ')]")
	private WebElementFacade leaguePosition;

	@FindBy(xpath = ".//td[@class='text team large-link']/a")
	private WebElementFacade clubName;

	@FindBy(xpath = ".//td[@class='number total mp']")
	private WebElementFacade playedGameNumber;

	@FindBy(xpath = ".//td[@class='number total won total_won']")
	private WebElementFacade wonGameNumber;

	@FindBy(xpath = ".//td[@class='number total drawn total_drawn']")
	private WebElementFacade drawGameNumber;

	@FindBy(xpath = ".//td[@class='number total lost total_lost']")
	private WebElementFacade lostGameNumber;

	@FindBy(xpath = ".//td[@class='number total gf total_gf']")
	private WebElementFacade goalsForNumber;

	@FindBy(xpath = ".//td[@class='number total ga total_ga']")
	private WebElementFacade goalsAgainstNumber;

	@FindBy(xpath = ".//td[@class='number gd']")
	private WebElementFacade goalsDifference;

	@FindBy(xpath = ".//td[@class='number points']")
	private WebElementFacade pointsAmount;

	public FootballClubItem(final AbstractPage driverDelegate) {
		super(driverDelegate);
	}

	public String getLeaguePosition() {
		return leaguePosition.getText();
	}

	public String getClubName() {
		return clubName.getAttribute("title").replace("'", "");
	}

	public String getPlayedGameNumber() {
		return playedGameNumber.getText();
	}

	public String getWonGameNumber() {
		return wonGameNumber.getText();
	}

	public String getDrawGameNumber() {
		return drawGameNumber.getText();
	}

	public String getLostGameNumber() {
		return lostGameNumber.getText();
	}

	public String getGoalsForNumber() {
		return goalsForNumber.getText();
	}

	public String getGoalsAgainstNumber() {
		return goalsAgainstNumber.getText();
	}

	public String getGoalsDifference() {
		return goalsDifference.getText();
	}

	public String getPointsAmount() {
		return pointsAmount.getText();
	}

	public LeagueClubStatus getLeagueClubStatus() {
		String classAttribute = leaguePosition.getAttribute("class");
		switch (classAttribute) {
			case "rank rank-dark-green":
				return LeagueClubStatus.CHAMPIONS_LEAGUE;
			case "rank rank-light-green":
				return LeagueClubStatus.CHAMPIONS_LEAGUE_QUALIFIERS;
			case "rank rank-dark-blue":
				return LeagueClubStatus.EUROPA_LEAGUE;
			case "rank rank-light-blue":
				return LeagueClubStatus.EUROPA_LEAGUE_QUALIFIERS;
			case "rank ":
				return LeagueClubStatus.MIDDLE;
			case "rank rank-red":
				return LeagueClubStatus.RELEGATION;
			case "rank rank-orange":
				return LeagueClubStatus.RELEGATION_QUALIFIERS;
			default:
				LOG.error("Can't parse correctly club's league status!");
				return null;
		}
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer().append("Football club: ").append(getClubName()).append(", " +
				"league position - ").append(getLeaguePosition()).append(", points amount - ").append(getPointsAmount
				());
		return stringBuffer.toString();
	}
}
