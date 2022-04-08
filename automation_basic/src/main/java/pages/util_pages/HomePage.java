package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;

public class HomePage extends MenusPage {

	public HomePage(MainPageManager pages) {
		super(pages);
	}

	Actions actions = new Actions(driver);
	JavascriptExecutor js = (JavascriptExecutor) driver;

	private String productImg = "//ul[@id='homefeatured']//img[@alt='Faded Short Sleeve T-shirts']";
	private String productPrice = "//*[@id=\"homefeatured\"]/li[1]/div/div[2]/div[1]/span";
	private String cartBtn = "//ul[@id='homefeatured']//a[@title='Faded Short Sleeve T-shirts']/../following-sibling::div[@class='button-container']/a[@title='Add to cart']";


	public HomePage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#homepage-slider")));
		return this;
	}

	public HomePage hoverCart() {
		log.info("Hover on image in order to click button.");
		WebElement img = driver.findElement(By.xpath(productImg));
		js.executeScript("arguments[0].scrollIntoView(true);", img);
		actions.moveToElement(img);
		return this.ensurePageLoaded();
	}

	public String getProductPrice() {
		return driver.findElement(By.xpath(productPrice)).getText();
	}

	public ShoppingCartPage clickAddToCart() {
		log.info("Click add to cart and go to cart page.");
		WebElement addToCartBtn = driver.findElement(By.xpath(cartBtn));
		actions.moveToElement(addToCartBtn);
		actions.click().build().perform();
		return pages.shoppingCartPage.ensurePageLoaded();
	}
}
