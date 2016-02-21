package bodaganj.jbehave.definition_steps;

import bodaganj.steps.FootballUaSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;

// Created by bogdan on 02.03.15.

public class FootballUaDefinitionSteps {

    @Steps
    private FootballUaSteps footballUaSteps;

    @Given("open football.ua website")
    public void openFootballUaWebsite() {
        footballUaSteps.open_football_ua_website();
    }

    @Given("go to first isport news")
    public void openFirstIsportLink() {
        footballUaSteps.open_first_isport_news();
    }
}
