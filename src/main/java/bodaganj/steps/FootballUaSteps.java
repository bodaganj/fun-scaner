package bodaganj.steps;

import bodaganj.pages.FootballUaPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

// Created by bogdan on 02.03.15.

public class FootballUaSteps extends ScenarioSteps {

    private FootballUaPage footballUaPage;

    @Step
    public void open_football_ua_website() {
        footballUaPage.open();
    }

    @Step
    public void open_first_isport_news() {
        footballUaPage.getMainPanel().getMainNewsListPanel().clickOnFirstIsportNews();
    }
}
