package bodaganj.steps;

import bodaganj.engine.ProjectLogger;
import bodaganj.engine.dataItems.items.FootballClubItem;
import bodaganj.jdbc.DbQueriesHelper;
import bodaganj.jdbc.DbStepHelper;
import bodaganj.pages.soccerWay.CompetitionsPage;
import bodaganj.pages.soccerWay.SoccerWayPage;
import bodaganj.utils.LeagueClubStatus;
import bodaganj.utils.session.Session;
import bodaganj.utils.session.SessionKey;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

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
		LOG.info("Get all football clubs in " + Session.getS(SessionKey.COUNTRY));
		List<FootballClubItem> footballClubItems = competitionsPage.getAllFootballClubs();
		List<FootballClubItem> championsLeagueTeams = footballClubItems.stream().filter(footballClubItem ->
				footballClubItem.getLeagueClubStatus().equals(LeagueClubStatus.CHAMPIONS_LEAGUE) || footballClubItem
						.getLeagueClubStatus().equals(LeagueClubStatus.CHAMPIONS_LEAGUE_QUALIFIERS)).
				collect(Collectors.toList());
		List<FootballClubItem> europaLeagueTeams = footballClubItems.stream().filter(footballClubItem ->
				footballClubItem.getLeagueClubStatus().equals(LeagueClubStatus.EUROPA_LEAGUE) || footballClubItem
						.getLeagueClubStatus().equals(LeagueClubStatus.EUROPA_LEAGUE_QUALIFIERS)).
				collect(Collectors.toList());
		assertThat(championsLeagueTeams).as("Champions League list can't be empty!").isNotEmpty();
		assertThat(europaLeagueTeams).as("Europa League list can't be empty!").isNotEmpty();
		LOG.info("Champions League potential participants:");
		writeInfoToDB(championsLeagueTeams);
		LOG.info("Europa League potential participants:");
		writeInfoToDB(europaLeagueTeams);
	}

	private void writeInfoToDB(final List<FootballClubItem> teamsList) {
		for (FootballClubItem championsLeagueTeam : teamsList) {
			String request = DbQueriesHelper.replaceRequestForEuroCupParticipants(championsLeagueTeam);
			DbStepHelper.update(request);
			LOG.info(championsLeagueTeam.getLeaguePosition() + " -> " + championsLeagueTeam.getClubName() +
					" (" + championsLeagueTeam.getLeagueClubStatus() + ")");
		}
	}
}