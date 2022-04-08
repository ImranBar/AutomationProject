package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;
import util.GenUtils;

public class ContactUsPage extends MenusPage {

	@FindBy(xpath = "//input[@id='email']")
	private WebElement email;

	@FindBy(xpath = "//input[@id='id_order']")
	private WebElement order;

	@FindBy(xpath = "//textarea[@id='message']")
	private WebElement message;

	@FindBy(css = "button[id='submitMessage'] i[class='icon-chevron-right right']")
	private WebElement send;


	public ContactUsPage(MainPageManager pages) {
		super(pages);
	}

	public ContactUsPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[contains(text(),'Customer service - Contact us')]"))));
		return this;
	}

	public ContactUsPage setEmail(String emailAddress) {
		email.sendKeys(emailAddress);
		return this.ensurePageLoaded();
	}

	public ContactUsPage setOrderReference(String userOrder) {
		order.sendKeys(userOrder);
		return this.ensurePageLoaded();
	}

	public ContactUsPage setMessage(String msg) {
		message.sendKeys(msg);
		return this.ensurePageLoaded();
	}

	public ContactUsPage chooseSubject() {
		WebElement testDropDown = driver.findElement(By.id("id_contact"));
		Select dropdown = new Select(testDropDown);
		dropdown.selectByValue("1");
		return this.ensurePageLoaded();
	}

	public MsgSuccessPage clickSend() {
		send.click();
		GenUtils.sleepSeconds(10);
		return pages.msgSuccessPage.ensurePageLoaded();
	}



}