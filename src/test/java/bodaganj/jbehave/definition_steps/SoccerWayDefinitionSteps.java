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

	@When("search for a top six team in $country top division")
	public void searchForTopSixTeamInLeague(final String country) {
		soccerWaySteps.open_soccerway_website();
		soccerWaySteps.click_competition_tab();
		soccerWaySteps.chose_top_division_of_specified_country(country);
	}
}
