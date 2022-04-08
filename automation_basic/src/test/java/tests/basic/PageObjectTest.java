package tests.basic;

import org.testng.annotations.Test;

import tests.supers.TestBase;

public class PageObjectTest extends TestBase {

	private static String ORDER = "Order";
	private static String Message = "Message";
	private static String EMAIL = "123@gmai.com";
	private static String PAGE_URL = "http://automationpractice.com/";
	private static String CART_TITLE = "Product successfully added to your shopping cart";

	private static String[] PRODUCT = new String[] { "Faded Short Sleeve T-shirts", "Printed Chiffon Dress" };

	@Test
	public void test() {
		try {

			openUrlAndVerify();

			log.info("Question 1.");
			String price = getProductPrice();
			clickAddToCartAndVerify();
			verifyProductPriceOnCart(price);

			log.info("Question 2.");
			serachForProductByNameAndVerify();

			log.info("Question 3.");
			contactCustomerServiceAndVerifyMsg();

			endTestSuccess();
		} catch (Throwable t) {
			onTestFailure(t);
		}
	}

	
	private void openUrlAndVerify() {
		app.getDriver().get(PAGE_URL);
		app.pages().homePage.ensurePageLoaded();
	}

	private String getProductPrice() {
		return app.pages().homePage.hoverCart().getProductPrice();
	}

	private void clickAddToCartAndVerify() {
		app.pages().homePage.hoverCart().clickAddToCart().verifyCartTitle(CART_TITLE);
	}

	private void verifyProductPriceOnCart(String price) {
		app.pages().shoppingCartPage.verifyProductPrice(price).verifyTotlaPrice().verifyProdctName(PRODUCT[0]).closeCart();
	}

	private void serachForProductByNameAndVerify() {
		app.pages().menusPage.search(PRODUCT[1]).clickProductListBtn().verifyFirstProductName(PRODUCT[1]);
	}

	private void contactCustomerServiceAndVerifyMsg() {
		app.pages().menusPage.clickContactUs().setEmail(EMAIL).setOrderReference(ORDER).setMessage(Message).chooseSubject().clickSend()
				.ensurePageLoaded();
	}

}
