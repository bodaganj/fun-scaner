package bodaganj.jbehave.definition_steps;

import bodaganj.steps.UtilitySteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Then;

import java.util.List;

/**
 * Created by bogdan on 25.04.16.
 * Test framework
 */
public class UtilsDefinitionSteps {

	@Steps
	private UtilitySteps utilitySteps;

	@Then("show current Champions League participants from $country")
	public void showChampionsLeagueParticipants(final List<String> country) {
		utilitySteps.get_champions_league_participants(country);
	}

	@Then("show current Europa League participants from $country")
	public void showEuropaLeagueParticipants(final List<String> country) {
		utilitySteps.get_europa_league_participants(country);
	}
}
