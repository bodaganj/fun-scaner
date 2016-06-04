package bodaganj.pages.footballUa;

import bodaganj.pages.AbstractPage;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebDriver;

// Created by bogdan on 02.03.15.

@At(".*/isport.ua/")
@DefaultUrl("http://isport.ua/")
public class IsportPage extends AbstractPage {

	public IsportPage(final WebDriver driver) {
		super(driver);
	}
}
