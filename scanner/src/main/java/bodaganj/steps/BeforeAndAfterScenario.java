package bodaganj.steps;

import bodaganj.pages.AbstractPage;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;

/**
 * Created by bogdan on 23.03.15.
 */
public class BeforeAndAfterScenario {

    @Steps
    private AbstractPage abstractPage;

    @BeforeScenario
    public void beforeScenario() {
        abstractPage.maximizeBrowser();
    }

    @AfterScenario
    public void afterScenario() {
        abstractPage.getDriver().close();
    }
}
