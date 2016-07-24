package bodaganj.jbehave.definitionSteps;

import bodaganj.steps.UtilitySteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.AfterScenario;

/**
 * Created by bogdan on 23.03.15.
 */
public class BeforeAndAfterScenario {

	@Steps
	private UtilitySteps utilitySteps;

	@AfterScenario
	public void afterScenario() {
		utilitySteps.quitWebDriver();
	}
}
