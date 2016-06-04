package bodaganj.jbehave;

import bodaganj.engine.ProjectLogger;
import bodaganj.utils.OperationSystem;
import ch.lambdaj.Lambda;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.jbehave.ThucydidesJUnitStories;
import org.jbehave.core.io.StoryFinder;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class AcceptanceTestSuite extends ThucydidesJUnitStories {

	private static final Logger LOG = ProjectLogger.getLogger(AcceptanceTestSuite.class.getSimpleName());
	private static final String X64_ARCH = "amd64";
	private static final String OS_ARCH = "os.arch";
	private static final String CHROME_DRIVER = "webdriver.chrome.driver";
	private static final String PHANTOMJS_DRIVER = "phantomjs.binary.path";
	private static final String IE_DRIVER = "webdriver.ie.driver";
	private static EnvironmentVariables environmentVariables = Injectors.getInjector()
			.getProvider(EnvironmentVariables.class).get();

	public AcceptanceTestSuite() {
		try {
			Class.forName("bodaganj.FunScannerProperties");
		} catch (ClassNotFoundException e) {
			LOG.error("Error instantiating FunScannerProperties", e);
		}
		setDriverAccordingToOS();
		selectStoryFilesForRunningSuite();
	}

	private void selectStoryFilesForRunningSuite() {
		String storiesPattern = environmentVariables.getProperty("fun.scanner.stories");
		if (storiesPattern == null) {
			LOG.debug("No suite key or pattern was provided, trying to run all stories in parallel");
			parallelAcceptanceTestSuite(storyPaths());
		} else {
			List<String> suiteStoryPaths = getStoryPathsForSuite(storiesPattern);
			if (suiteStoryPaths.isEmpty()) {
				LOG.debug("No suite was found for the given {} key, trying to run as pattern not in parallel",
						storiesPattern);
				findStoriesCalled(storiesPattern);
			} else {
				parallelAcceptanceTestSuite(suiteStoryPaths);
			}
		}
	}

	private List<String> getStoryPathsForSuite(final String runningSuite) {
		File suiteOfStories = findFile(runningSuite, new File(System.getProperty("suites.path")));
		return collectStoryPathsFromSuiteFile(suiteOfStories);
	}

	private File findFile(final String searchedFile, final File searchInDirectory) {
		File[] listOfAllFilesInDirectory = searchInDirectory.listFiles();
		File suiteOfStories;
		if (listOfAllFilesInDirectory != null) {
			for (File singleFileFromDirectory : listOfAllFilesInDirectory) {
				if (singleFileFromDirectory.isDirectory()) {
					suiteOfStories = findFile(searchedFile, singleFileFromDirectory);
					if (suiteOfStories != null) {
						return suiteOfStories;
					}
				} else if (searchedFile.equalsIgnoreCase(singleFileFromDirectory.getName().replaceAll("\\..+$", ""))) {
					return singleFileFromDirectory;
				}
			}
		}
		LOG.debug("There is no suite: {} in directory {}", searchedFile, searchInDirectory);
		return null;
	}

	private List<String> collectStoryPathsFromSuiteFile(final File suiteFile) {
		if (null == suiteFile) {
			return Collections.emptyList();
		}
		List<String> storyPaths;
		try {
			storyPaths = Files.readAllLines(Paths.get(suiteFile.getPath()), Charset.defaultCharset());
		} catch (IOException e) {
			LOG.error("Failed to open suite file, exiting", e);
			throw new RuntimeException(e);
		}
		LOG.info("Got story paths {}", storyPaths);
		return storyPaths;
	}

	// Stories invocation in parallel
	private void parallelAcceptanceTestSuite(final List<String> storyPaths) {
		List<String> stories = new StoryFinder().findPaths(System.getProperty("stories.path"), storyPaths, null);
		Integer agentNumber = environmentVariables.getPropertyAsInteger("parallel.agent.number", 1);
		Integer agentTotal = environmentVariables.getPropertyAsInteger("parallel.agent.total", 1);
		failIfAgentIsNotConfiguredCorrectly(agentNumber, agentTotal);
		failIfThereAreMoreAgentsThanStories(agentTotal, stories.size());

		// The reminder should work out to be either be zero or one.
		int reminder = stories.size() % agentTotal;
		int storiesPerAgent = stories.size() / agentTotal;

		int startPos = storiesPerAgent * (agentNumber - 1);
		int endPos = startPos + storiesPerAgent;
		if (agentNumber.equals(agentTotal)) {
			// In the case of an uneven number the last agent picks up the extra story file.
			endPos += reminder;
		}
		List<String> finalStories = stories.subList(startPos, endPos);

		outputWhichStoriesAreBeingRun(finalStories);
		findStoriesCalled(Lambda.join(finalStories, ";"));
	}

	private void failIfAgentIsNotConfiguredCorrectly(final Integer agentPosition, final Integer agentCount) {
		if (agentPosition == null) {
			throw new RuntimeException("The agent number needs to be specified");
		} else if (agentCount == null) {
			throw new RuntimeException("The agent total needs to be specified");
		} else if (agentPosition < 1) {
			throw new RuntimeException("The agent number is invalid");
		} else if (agentCount < 1) {
			throw new RuntimeException("The agent total is invalid");
		} else if (agentPosition > agentCount) {
			throw new RuntimeException(String.format("There were %d agents in total specified and this agent is " +
					"outside that range (it is specified as %d)", agentPosition, agentCount));
		}
	}

	private void failIfThereAreMoreAgentsThanStories(final Integer agentCount, final int storyCount) {
		if (storyCount < agentCount) {
			throw new RuntimeException("There are more agents then there are stories, this agent isn't necessary");
		}
	}

	private void outputWhichStoriesAreBeingRun(final List<String> stories) {
		LOG.info("Running {} stories: ", stories.size());
		for (String story : stories) {
			LOG.info(" - {}", story);
		}
	}

	private void setDriverAccordingToOS() {
		OperationSystem.OSType osType = OperationSystem.getOperatingSystemType();
		switch (osType) {
			case Windows:
				setChromeDriverWindows();
				setPhantomJSDriverWindows();
				if (X64_ARCH.equals(System.getProperty(OS_ARCH))) {
					setIeDriverWindows64();
				} else {
					setIeDriverWindows32();
				}
				break;
			case MacOS:
				setChromeDriverOsx();
				setPhantomJSDriverOsx();
				break;
			case Linux:
				if (X64_ARCH.equals(System.getProperty(OS_ARCH))) {
					setChromeDriverLinux64();
//					setPhantomJSDriverLinux64();
				} else {
					setChromeDriverLinux32();
					setPhantomJSDriverLinux32();
				}
				break;
			case Other:
				LOG.error("Can't define OS");
				break;
		}
	}

	private void setChromeDriverLinux64() {
		System.setProperty(CHROME_DRIVER, "drivers/chromedriver.exe");
	}

	//TODO to be added all drivers mentioned below
	private void setChromeDriverLinux32() {
		System.setProperty(CHROME_DRIVER, "drivers/linux/32bit/chromedriver");
	}

	private void setChromeDriverWindows() {
		System.setProperty(CHROME_DRIVER, "drivers/windows/chromedriver.exe");
	}

	private void setChromeDriverOsx() {
		System.setProperty(CHROME_DRIVER, "drivers/osx/chromedriver");
	}

	private void setPhantomJSDriverLinux32() {
		System.setProperty(PHANTOMJS_DRIVER, "drivers/linux/32bit/phantomjs");
	}

	private void setPhantomJSDriverLinux64() {
		System.setProperty(PHANTOMJS_DRIVER, "drivers/linux/64bit/phantomjs");
	}

	private void setPhantomJSDriverWindows() {
		System.setProperty(PHANTOMJS_DRIVER, "drivers/windows/phantomjs.exe");
	}

	private void setPhantomJSDriverOsx() {
		System.setProperty("webdriver.phantomjs.driver", "drivers/osx/phantomjs");
	}

	private void setIeDriverWindows32() {
		System.setProperty(IE_DRIVER, "drivers/windows/32bit/iedriver.exe");
	}

	private void setIeDriverWindows64() {
		System.setProperty(IE_DRIVER, "drivers/windows/64bit/iedriver.exe");
	}
}
