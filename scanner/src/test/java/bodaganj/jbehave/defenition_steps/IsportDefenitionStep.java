package bodaganj.jbehave.defenition_steps;

import bodaganj.steps.IsportSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.When;

/**
 * Created by bogdan on 02.03.15.
 */
public class IsportDefenitionStep {

    @Steps
    IsportSteps isportSteps;

    @When("I open blog tab on isport")
    public void openBlogTabOnIsport() {
        isportSteps.open_blog_tab_on_isport();
    }
}
