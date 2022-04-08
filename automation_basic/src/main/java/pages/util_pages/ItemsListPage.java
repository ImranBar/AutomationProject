package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;

public class ItemsListPage extends MenusPage {

	@FindBy(xpath = "//i[@class='icon-th-list']")
	private WebElement listBtn;

	@FindBy(xpath = "//*[@id='center_column']/ul/li[1] //a[@class='product-name']")
	private WebElement productName;

	JavascriptExecutor js = (JavascriptExecutor) driver;

	public ItemsListPage(MainPageManager pages) {
		super(pages);
	}

	public ItemsListPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[contains(@class,'product-listing')]"))));
		return this;
	}

	public ItemsListPage verifySearchResult(String productName) {
		return this.ensurePageLoaded();
	}

	public ItemsListPage clickProductListBtn() {
		Actions act = new Actions(driver);
		act.moveToElement(listBtn).click().perform();
		return this.ensurePageLoaded();
	}

	public ItemsListPage verifyFirstProductName(String expectedProductName) {
		log.info("Verify product name in list.");
		Assert.assertTrue(expectedProductName.equals(productName.getText()),
				"Expected name is " + productName.getText() + " but the actual name is " + productName.getText());
		return this.ensurePageLoaded();
	}
}