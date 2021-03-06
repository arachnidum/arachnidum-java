package web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.util.configuration.Configuration;

import org.openqa.selenium.Platform;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.github.arachnidium.core.HowToGetPage;
import com.github.arachnidium.web.google.AnyPage;
import com.github.arachnidium.web.google.Google;

public class HelloWorldGoogleTest2 {

	private enum HowToGetANewWindow {
		BYPARTIALTITLE {
			@Override
			HowToGetPage get() {
				HowToGetPage h = super.get();
				h.setExpected("^*[?[Hello]\\?[world]]");
				return h;
			}
		},
		BYPARTIALURL {
			@Override
			HowToGetPage get() {
				HowToGetPage h = super.get();
				h.setExpected(new ArrayList<String>(){
					private static final long serialVersionUID = 1L;
					{
						add("wikipedia.org");
					}
					
				});
				return h;
			}
		},
		FULL {
			@Override
			HowToGetPage get() {
				HowToGetPage h = super.get();
				h.setExpected(new ArrayList<String>(){
					private static final long serialVersionUID = 1L;
					{
						add("wikipedia.org");
					}
					
				});
				h.setExpected("^*[?[Hello]\\?[world]]");
				return h;
			}
		};

		HowToGetPage get() {
			HowToGetPage h = new HowToGetPage();
			h.setExpected(1);
			return h;
		}
	}

	// settings according to current OS
	private final HashMap<Platform, List<String>> settings = new HashMap<Platform, List<String>>();

	private void test(Google google, HowToGetANewWindow howToGet,
			boolean toClickOnALinkWhichWasFound, long timeOut) throws Exception {
		Thread.sleep(2000);
		if (!toClickOnALinkWhichWasFound) {
			google.openLinkByIndex(1);
		} else {
			google.clickOnLinkByIndex(1);
		}
		AnyPage anyPage = google.getPart(AnyPage.class, howToGet.get());	
		anyPage.content.switchToMe();
		anyPage.close();
	}

	@Test(description = "This is just a test of basic functionality. It gets a new object by its partial title and url")
	@Parameters(value = { "path", "toClick", "configList",
			"howToGetANewWindow", "timeOut" })
	public void typeHelloWorldAndOpenTheFirstLink(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("false") String toClick, @Optional("chrome.json,firefox.json") String configList,
			@Optional("BYPARTIALURL") String howToGetANewWindow,
			@Optional("10") String timeOut) throws Exception {

		List<String> configs = getConfigsByCurrentPlatform();
		String[] configNames = configList.split(",");

		for (String config : configNames) {
			if (!configs.contains(config)) {
				continue;
			}
			Configuration configuration = Configuration.get(path + config);
			Google google =new WebFactory(configuration).launch(Google.class, "http://www.google.com/");
			try {
				String[] howToVars = howToGetANewWindow.split(",");
				google.performSearch("Hello world Wikipedia");
				for (String howTo : howToVars) {
					test(google, HowToGetANewWindow.valueOf(howTo),
							new Boolean(toClick), new Long(timeOut));
				}
			} finally {
				google.quit();
			}
		}
	}

	@BeforeTest
	public void beforeTest() {
		// for Windows
		settings.put(Platform.WINDOWS, new ArrayList<String>() {
			private static final long serialVersionUID = -1718278594717074313L;
			{
				add("chrome_remote.json");
				add("chrome.json");

				add("firefox_remote.json");
				add("firefox.json");

				add("internetexplorer_remote.json");
				add("internetexplorer.json");

				add("phantomjs_remote.json");
				add("phantomjs.json");

				add("android_emulator_chrome.json");
				add("android_emulator_chrome_remoteWebDriver.json");
				
				add("android_emulator_browser.json");
				add("android_emulator_browser_remoteWebDriver.json");
			}

		});
		// for MAC
		settings.put(Platform.MAC, new ArrayList<String>() {
			private static final long serialVersionUID = -1718278594717074313L;
			{
				add("chrome_remote.json");
				add("chrome.json");

				add("firefox_remote.json");
				add("firefox.json");

				add("safari_remote.json");
				add("safari.json");

				add("phantomjs_remote.json");
				add("phantomjs.json");

				add("iOS_emulator_safari.json");
				add("iOS_emulator_safari_remoteWebDriver.json");
			}

		});

	}

	List<String> getConfigsByCurrentPlatform() {
		Set<Entry<Platform, List<String>>> entries = settings.entrySet();
		for (Entry<Platform, List<String>> entry : entries) {
			if (Platform.getCurrent().is(entry.getKey())) {
				return entry.getValue();
			}
		}

		return new ArrayList<String>();
	}
}
