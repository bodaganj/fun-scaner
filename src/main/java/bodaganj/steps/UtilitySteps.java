package bodaganj.steps;

import bodaganj.engine.ProjectLogger;
import bodaganj.jdbc.DbQueriesHelper;
import bodaganj.jdbc.DbStepHelper;
import bodaganj.pages.AnyWebPage;
import bodaganj.utils.LeagueClubStatus;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by bogdan on 05.06.16.
 * Test framework
 */
public class UtilitySteps extends ScenarioSteps {

	private static final Logger LOG = ProjectLogger.getLogger(UtilitySteps.class.getSimpleName());
	private String clubName = "clubName";
	private String separator = " ----> ";
	private AnyWebPage soccerWayPage;

	@Step
	public void get_champions_league_participants(final List<String> countryList) {
		for (String country : countryList) {
			LOG.info("***** Get Champions League participants from " + country);
			String request = DbQueriesHelper.selectEuroCupParticipants(country, LeagueClubStatus.CHAMPIONS_LEAGUE);
			List<Map<String, String>> championsLeagueParticipantsList = DbStepHelper.select(request);
			championsLeagueParticipantsList.forEach(map -> LOG.info(separator + map.get(clubName)));
		}
	}

	@Step
	public void get_europa_league_participants(final List<String> countryList) {
		for (String country : countryList) {
			LOG.info("***** Get Europa League participants from " + country);
			String request = DbQueriesHelper.selectEuroCupParticipants(country, LeagueClubStatus.EUROPA_LEAGUE);
			List<Map<String, String>> europaLeagueParticipantsList = DbStepHelper.select(request);
			europaLeagueParticipantsList.forEach(map -> LOG.info(separator + map.get(clubName)));
		}
	}

	@Step
	public void quitWebDriver() {
		LOG.debug("Closing WebDriver...");
		soccerWayPage.getDriver().quit();
	}
}
