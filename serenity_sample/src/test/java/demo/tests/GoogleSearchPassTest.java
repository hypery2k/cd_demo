package demo.tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Story;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.runners.ThucydidesRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import demo.requirements.Google;
import demo.steps.GoogleSteps;

@RunWith(SerenityRunner.class)
@Story(Google.Search.SearchByKeyword.class)
public class GoogleSearchPassTest {

	@Managed(uniqueSession = true)
	public WebDriver webdriver;

	@ManagedPages(defaultUrl = "https://www.google.com")
	public Pages pages;

	@Steps
	public GoogleSteps googleSteps;

	@Test
	public void googleSearchPassTest() {
		googleSteps.performSearch("42");
		googleSteps.findSearchResult("Stupidedia");
		googleSteps.verifyUrl("http://www.stupidedia.org/stupi/42");

	}
}