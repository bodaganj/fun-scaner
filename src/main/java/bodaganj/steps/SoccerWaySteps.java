package bodaganj.steps;

import bodaganj.engine.ProjectLogger;
import bodaganj.engine.dataItems.items.FootballClubItem;
import bodaganj.jdbc.DbQueriesHelper;
import bodaganj.jdbc.DbStepHelper;
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

	@Step
	public void open_soccerway_website() {
		LOG.debug("Opening Soccer Way website");
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

	@Step
	public void get_euro_cup_applicant_teams() {
		List<FootballClubItem> championsLeagueTeams = competitionsPage.getChampionsLeaguePotentialParticipants();
		List<FootballClubItem> europaLeagueTeams = competitionsPage.getEuropaLeaguePotentialParticipants();
		assertThat(championsLeagueTeams).as("Champions League list can't be empty!").isNotEmpty();
		assertThat(europaLeagueTeams).as("Europa League list can't be empty!").isNotEmpty();
		LOG.info("Champions League potential participants:");
		String separator = " -> ";
		String leftParenthesis = " (";
		String rightParenthesis = ")";
		for (FootballClubItem championsLeagueTeam : championsLeagueTeams) {
			String request = DbQueriesHelper.replaceRequestForEuroCupParticipants(championsLeagueTeam);
			DbStepHelper.update(request);
			LOG.info(championsLeagueTeam.getLeaguePosition() + separator + championsLeagueTeam.getClubName() +
					leftParenthesis + championsLeagueTeam.getLeagueClubStatus() + rightParenthesis);
		}
		LOG.info("Europa League potential participants:");
		for (FootballClubItem europaLeagueTeam : europaLeagueTeams) {
			String request = DbQueriesHelper.replaceRequestForEuroCupParticipants(europaLeagueTeam);
			DbStepHelper.update(request);
			LOG.info(europaLeagueTeam.getLeaguePosition() + separator + europaLeagueTeam.getClubName() +
					leftParenthesis + europaLeagueTeam.getLeagueClubStatus() + rightParenthesis);
		}
	}
}