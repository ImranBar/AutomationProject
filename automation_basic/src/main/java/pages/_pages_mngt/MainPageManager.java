package pages._pages_mngt;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import applogic.ApplicationManager1;
import pages._pages_mngt.page_factory.DisplayedElementLocatorFactory;
import pages.super_pages.MenusPage;
import pages.super_pages.Page;
import pages.util_pages.ContactUsPage;
import pages.util_pages.HomePage;
import pages.util_pages.ItemsListPage;
import pages.util_pages.MsgSuccessPage;
import pages.util_pages.ShoppingCartPage;
import util.ParamsUtils;
import util.SelUtils;

public class MainPageManager {

	private WebDriver driver;
	public SelUtils su;
	private Logger log;
	private ParamsUtils sessionParams;

	public MenusPage menusPage;
	public HomePage homePage;
	public ItemsListPage itemsListPage;
	public ShoppingCartPage shoppingCartPage;
	public MsgSuccessPage msgSuccessPage;
	public ContactUsPage contactUsPage;

	public MainPageManager(ApplicationManager1 app) {
		driver = app.getDriver();
		su = app.su;
		log = app.getLogger();
		sessionParams = app.getParamsUtils();

		menusPage = initElements(new MenusPage(this));
		homePage = initElements(new HomePage(this));
		itemsListPage = initElements(new ItemsListPage(this));
		shoppingCartPage = initElements(new ShoppingCartPage(this));
		msgSuccessPage = initElements(new MsgSuccessPage(this));
		contactUsPage = initElements(new ContactUsPage(this));
	}

	public <T extends Page> T initElements(T page) {
		PageFactory.initElements(new DisplayedElementLocatorFactory(driver, 10), page);
		return page;
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	public Logger gerLogger() {
		return log;
	}

	public ParamsUtils getSessionParams() {
		return sessionParams;
	}
}
