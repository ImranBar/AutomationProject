package pages.super_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pages._pages_mngt.MainPageManager;
import pages.util_pages.ContactUsPage;
import pages.util_pages.ItemsListPage;
import util.GenUtils;

public class MenusPage extends AnyPage {

	@FindBy(xpath = "//input[@id='search_query_top']")
	private WebElement searchField;

	@FindBy(xpath = "//button[@name='submit_search']")
	private WebElement searchBtn;

	@FindBy(xpath = "//a[@title='Contact Us']")
	private WebElement contactUs;

	public MenusPage(MainPageManager pages) {
		super(pages);
	}

	public ItemsListPage search(String proudctName) {
		log.info("Search by product Name: " + proudctName);
		searchField.sendKeys(proudctName);
		searchBtn.click();
		GenUtils.sleepSeconds(20);
		return pages.itemsListPage.ensurePageLoaded();
	}

	public ContactUsPage clickContactUs() {
		log.info("Click contact us");
		contactUs.click();
		GenUtils.sleepSeconds(5);
		return pages.contactUsPage.ensurePageLoaded();
	}


}