package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;

public class MsgSuccessPage extends MenusPage {

	public MsgSuccessPage(MainPageManager pages) {
		super(pages);
	}
	
	public MsgSuccessPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(
				By.xpath("//p[@class='alert alert-success' and contains(text(),'Your message has been successfully sent to our team.')]"))));
		return this;
	}
}
