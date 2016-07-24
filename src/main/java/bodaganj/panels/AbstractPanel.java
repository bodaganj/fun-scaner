package bodaganj.panels;

import bodaganj.engine.WebDriverAdaptor;
import bodaganj.pages.AbstractPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.locators.SmartFieldDecorator;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

// Created by Bogdan_Ganzha on 3/1/2015.

public abstract class AbstractPanel {

	private final EnvironmentVariables environmentVariables;
	private AbstractPage driverDelegate;
	private WebDriverAdaptor panelToWebDriver;
	private long timeoutInMilliseconds;

	@FindBy(xpath = ".")
	private WebElementFacade panelBase;

	public AbstractPanel(final WebElementFacade panelBaseLocation, final AbstractPage driverDelegate) {
		environmentVariables = Injectors.getInjector().getProvider(EnvironmentVariables.class).get();
		timeoutInMilliseconds = Long.valueOf(environmentVariables.getPropertyAsInteger(
				"webdriver.timeouts.implicitlywait", 10000));
		initPanel(panelBaseLocation, driverDelegate);
	}

	private void initPanel(final WebElementFacade panelBaseLocation, final AbstractPage driverDelegate) {
		this.driverDelegate = driverDelegate;
		this.timeoutInMilliseconds = driverDelegate.waitForTimeoutInMilliseconds();
		this.panelToWebDriver = new WebDriverAdaptor(panelBaseLocation, getDriver());
		ElementLocatorFactory finder = new DefaultElementLocatorFactory(panelToWebDriver);
		FieldDecorator decorator = new SmartFieldDecorator(finder, getDriver(), driverDelegate);
		PageFactory.initElements(decorator, this);
	}

	private long waitForTimeoutInSeconds() {
		return (timeoutInMilliseconds < 1000) ? 1 : (timeoutInMilliseconds / 1000);
	}

	public WebDriver getDriver() {
		return driverDelegate.getDriver();
	}

	public AbstractPage getDriverDelegate() {
		return driverDelegate;
	}

	protected WebElementFacade getPanelBaseElement() {
		return panelBase;
	}
}
