package bodaganj.pages.footballUa;

import bodaganj.pages.AbstractPage;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebDriver;

// Created by bogdan on 02.03.15.

@At(".*/blog.isport.ua/")
@DefaultUrl("http://blog.isport.ua/")
public class BlogPage extends AbstractPage {

	public BlogPage(final WebDriver webDriver) {
		super(webDriver);
	}
}
