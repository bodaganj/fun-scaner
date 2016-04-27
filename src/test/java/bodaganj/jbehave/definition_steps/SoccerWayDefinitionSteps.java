package bodaganj.jbehave.definition_steps;

import bodaganj.steps.SoccerWaySteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;

/**
 * Created by bogdan on 09.04.16.
 * Test framework
 */
public class SoccerWayDefinitionSteps {

	@Steps
	private SoccerWaySteps soccerWaySteps;

	@Given("open SoccerWay website and navigate to Competition tab")
	public void openSoccerWayWebsite() {
		soccerWaySteps.open_soccerway_website();
		soccerWaySteps.click_competition_tab();
	}

	@When("search for Euro cups participant teams in $country top division")
	public void searchForEuroCupsParticipantsInLeague(final String country) {
		soccerWaySteps.open_soccerway_website();
		soccerWaySteps.click_competition_tab();
		soccerWaySteps.chose_top_division_of_specified_country(country);
		soccerWaySteps.get_eurocup_aplicant_teams();
	}
}
