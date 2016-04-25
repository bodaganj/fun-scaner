package bodaganj.steps;

import bodaganj.pages.soccerWay.CompetitionsPage;
import bodaganj.pages.soccerWay.SoccerWayPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * Created by bogdan on 09.04.16.
 * Test framework
 */
public class SoccerWaySteps extends ScenarioSteps {

	private SoccerWayPage soccerWayPage;
	private CompetitionsPage competitionsPage;

	@Step
	public void open_soccerway_website() {
		soccerWayPage.open();
	}

	@Step
	public void click_competition_tab() {
		soccerWayPage.getNavigationPanel().clickCompetitionsButton();
	}

	@Step
	public void chose_top_division_of_specified_country(final String country) {
		competitionsPage.clickOnSpecifiedCountry(country);
		competitionsPage.clickOnTopDibision();
	}
}
