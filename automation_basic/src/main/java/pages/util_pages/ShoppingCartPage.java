package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;

public class ShoppingCartPage extends MenusPage {

	public ShoppingCartPage(MainPageManager pages) {
		super(pages);

	}

	private String CartTitleTxt = "//span[@title='Close window']/following-sibling::h2";
	private String totalPrice = "//span[@class='ajax_block_cart_total']";
	private String totalShipping = "//span[@class='ajax_cart_shipping_cost']";
	private String prodctsTotal = "//span[@class='ajax_block_products_total']";
	private String productName = "//span[@id='layer_cart_product_title']";
	private String clsoeCart = "//span[@title='Close window']";

	public ShoppingCartPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("layer_cart"))));
		return this;
	}

	public ShoppingCartPage verifyCartTitle(String expectedCartTitle) {
		String cartTitle = driver.findElement(By.xpath(CartTitleTxt)).getText();
		Assert.assertTrue(expectedCartTitle.equals(cartTitle),
				"Expected cart title is " + expectedCartTitle + " but the actual cart title  in the cart is " + cartTitle);
		return this.ensurePageLoaded();
	}

	public ShoppingCartPage verifyProductPrice(String expectedPrice) {
		log.info("Verify cart price.");
		String currentPrice = driver.findElement(By.xpath(prodctsTotal)).getText();
		Assert.assertTrue(expectedPrice.equals(currentPrice), "Expected price is " + expectedPrice + " but the actual price is " + currentPrice);
		return this.ensurePageLoaded();
	}

	public ShoppingCartPage verifyTotlaPrice() {
		log.info("Verify total price with Shipping.");
		float proudctPriceWithShipping = Float.parseFloat(driver.findElement(By.xpath(totalPrice)).getText().replaceAll("[^\\d.]+|\\.(?!\\d)", ""));
		float shippingPrice = Float.parseFloat(driver.findElement(By.xpath(totalShipping)).getText().replaceAll("[^\\d.]+|\\.(?!\\d)", ""));
		float currentTotalPrice = Float.parseFloat(driver.findElement(By.xpath(prodctsTotal)).getText().replaceAll("[^\\d.]+|\\.(?!\\d)", ""));
		float expectedTotalPrice = currentTotalPrice + shippingPrice;
		Assert.assertTrue(proudctPriceWithShipping == expectedTotalPrice);
		return this.ensurePageLoaded();
	}

	public ShoppingCartPage verifyProdctName(String expectedProductName) {
		log.info("Assert the correct product was added.");
		String currentProductName = driver.findElement(By.xpath(productName)).getText();
		Assert.assertTrue(expectedProductName.equals(currentProductName),
				"Expected product name is " + expectedProductName + " but the actual product name is " + currentProductName);
		return this.ensurePageLoaded();
	}

	public HomePage closeCart() {
		log.info("Assert the correct product was added.");
		driver.findElement(By.xpath(clsoeCart)).click();
		return pages.homePage.ensurePageLoaded();
	}

}