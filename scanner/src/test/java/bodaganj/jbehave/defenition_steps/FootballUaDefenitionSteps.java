package bodaganj.jbehave.defenition_steps;

import bodaganj.steps.FootballUaSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;

/**
 * Created by bogdan on 02.03.15.
 */
public class FootballUaDefenitionSteps {

    @Steps
    FootballUaSteps footballUaSteps;

    @Given("open football.ua website and go to first isport news")
    public void openFootballUaWebsite() {
        footballUaSteps.open_football_ua_website();
    }
}
