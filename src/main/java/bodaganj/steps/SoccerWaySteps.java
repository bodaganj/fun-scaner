package bodaganj.steps;

import bodaganj.engine.ProjectLogger;
import bodaganj.engine.dataItems.items.FootballClubItem;
import bodaganj.pages.soccerWay.CompetitionsPage;
import bodaganj.pages.soccerWay.SoccerWayPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.slf4j.Logger;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by bogdan on 09.04.16.
 * Test framework
 */
public class SoccerWaySteps extends ScenarioSteps {

	private static final Logger LOG = ProjectLogger.getLogger(FootballClubItem.class.getSimpleName());

	private SoccerWayPage soccerWayPage;
	private CompetitionsPage competitionsPage;

	private String separator = " -> ";
	private String leftParenthesis = " (";
	private String rightParenthesis = ")";

	@Step
	public void open_soccerway_website() {
		soccerWayPage.open();
	}

	@Step
	public void click_competition_tab() {
		soccerWayPage.getNavigationPanel().clickCompetitionsButton();
	}

	@Step
	public void chose_top_division_of_specified_country(final String country) {
		competitionsPage.clickOnSpecifiedCountry(country);
		competitionsPage.clickOnTopDivision();
	}

	//TODO: write to DB teams' info here
	@Step
	public void get_eurocup_applicant_teams() {
		List<FootballClubItem> championsLeagueTeams = competitionsPage.getChampionsLeaguePotentialParticipants();
		List<FootballClubItem> europaLeagueTeams = competitionsPage.getEuropaLeaguePotentialParticipants();
		assertThat(championsLeagueTeams).as("Champions League list can't be empty!").isNotEmpty();
		assertThat(europaLeagueTeams).as("Europa League list can't be empty!").isNotEmpty();
		LOG.info("Champions League potential participants:");
		for (FootballClubItem championsLeagueTeam : championsLeagueTeams) {
			LOG.info(championsLeagueTeam.getLeaguePosition() + separator + championsLeagueTeam.getClubName() +
					leftParenthesis + championsLeagueTeam.getLeagueClubStatus() + rightParenthesis);
		}
		LOG.info("Europa League potential participants:");
		for (FootballClubItem europaLeagueTeam : europaLeagueTeams) {
			LOG.info(europaLeagueTeam.getLeaguePosition() + separator + europaLeagueTeam.getClubName() +
					leftParenthesis + europaLeagueTeam.getLeagueClubStatus() + rightParenthesis);
		}
	}
}