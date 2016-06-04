package bodaganj.jbehave.definition_steps;

import bodaganj.steps.SoccerWaySteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;

import java.util.List;

/**
 * Created by bogdan on 09.04.16.
 * Test framework
 */
public class SoccerWayDefinitionSteps {

	@Steps
	private SoccerWaySteps soccerWaySteps;

	@Given("open SoccerWay website")
	public void openSoccerWayWebsite() {
		soccerWaySteps.open_soccerway_website();
	}

	@When("search for Euro cups participant teams in $country top division(s)")
	public void searchForEuroCupsParticipantsInLeague(final List<String> country) {
		for (String countryName : country) {
			soccerWaySteps.click_competition_tab();
			soccerWaySteps.chose_top_division_of_specified_country(countryName.trim());
			soccerWaySteps.get_euro_cup_applicant_teams();
		}
	}
}
