package bodaganj.engine.dataItems;

import bodaganj.engine.ProjectLogger;
import bodaganj.engine.WebDriverAdaptor;
import bodaganj.pages.AbstractPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.locators.SmartFieldDecorator;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bogdan on 27.04.16.
 * Test framework
 */
public final class DataItemsFactory {

	private static final Logger LOG = ProjectLogger.getLogger(DataItemsFactory.class.getSimpleName());

	private static DataItemsFactory instance;

	private final StopWatch stopWatch;

	private DataItemsFactory() {
		stopWatch = new StopWatch();
	}

	public static DataItemsFactory getInstance() {
		synchronized (DataItemsFactory.class) {
			if (null == instance) {
				instance = new DataItemsFactory();
			}
		}
		return instance;
	}

	public <T extends DataItem> List<T> getElementsNamed(final Class<T> clazz, final List<WebElementFacade>
			baseElements, final AbstractPage driverDelegate) {
		restartStopWatch();
		final List<T> pageDataList = new ArrayList<>();
		LOG.info("Processing data lines of type {}", clazz.getCanonicalName());
		for (WebElementFacade baseElement : baseElements) {
			try {
				T dataItem = getElementNamed(clazz, baseElement, driverDelegate);
				pageDataList.add(dataItem);
			} catch (ReflectiveOperationException e) {
				LOG.error("Error instantiating data line object. Line for base element {} is not included to result " +
						"list", baseElement.toString(), e);
			}
		}
		stopAndLogMeasuredTime(pageDataList.size());
		return pageDataList;
	}

	private  <T extends DataItem> T getElementNamed(final Class<T> clazz, final WebElementFacade baseElement, final
	AbstractPage driverDelegate) throws ReflectiveOperationException {
		T dataItem = clazz.getDeclaredConstructor(AbstractPage.class).newInstance(driverDelegate);
		WebDriverAdaptor webDriverAdaptor = new WebDriverAdaptor(baseElement, driverDelegate.getDriver());
		ElementLocatorFactory finder = new DefaultElementLocatorFactory(webDriverAdaptor);
		FieldDecorator decorator = new SmartFieldDecorator(finder, webDriverAdaptor, driverDelegate);
		PageFactory.initElements(decorator, dataItem);
		logDataItem(dataItem);
		return dataItem;
	}

	/*
	 * Such a mechanism is provided because invoking toString() on dataItem is usually a quite expensive action (it
	 * causes some web elements in dataItem to be explicitly found) So the goal is to save a lot of time unless
	 * debug is enabled
	 */
	private <T extends DataItem> void logDataItem(final T dataItem) {
		LOG.debug("{}", dataItem);
	}

	private void restartStopWatch() {
		stopWatch.reset();
		stopWatch.start();
	}

	private void stopAndLogMeasuredTime(final int elementsCount) {
		stopWatch.stop();
		LOG.info("found {} elements for the given query, processing took {} ms", elementsCount, stopWatch.getTime());
	}
}
