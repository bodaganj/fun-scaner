package bodaganj.jbehave.defenition_steps;

import bodaganj.steps.IsportBlogSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Then;

/**
 * Created by bogdan on 02.03.15.
 */
public class IsportBlogDefenitionStep {

    @Steps
    IsportBlogSteps isportBlogSteps;

    @Then("I search for interesting posts")
    public void searchForInterestingPosts() {
        isportBlogSteps.search_for_interesting_posts();
    }
}
