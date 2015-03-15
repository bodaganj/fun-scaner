package bodaganj.jbehave.definition_steps;

import bodaganj.steps.BlogSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Then;

/**
 * Created by bogdan on 02.03.15.
 */
public class BlogDefinitionStep {

    @Steps
    BlogSteps blogSteps;

    @Then("I search for interesting posts")
    public void searchForInterestingPosts() {
        blogSteps.search_for_interesting_posts();
    }
}
