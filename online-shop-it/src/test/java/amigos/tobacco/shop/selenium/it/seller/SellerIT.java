package amigos.tobacco.shop.selenium.it.seller;

import amigos.tobacco.shop.selenium.it.BaseIT;
import amigos.tobacco.shop.selenium.it.pages.BasePage;
import amigos.tobacco.shop.selenium.it.pages.auth.LoginPage;
import amigos.tobacco.shop.selenium.it.pages.auth.RegisterPage;
import amigos.tobacco.shop.selenium.it.pages.products.AddProductPage;
import amigos.tobacco.shop.selenium.it.pages.products.MyProductsPage;
import amigos.tobacco.shop.selenium.it.pages.products.ProductsPage;
import amigos.tobacco.shop.selenium.it.utils.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Seller integration tests.
 */
class SellerIT extends BaseIT {

    /**
     * Tests that a product is added and appears in the products and my products page.
     */
    @Test
    void testAddProduct() {
        final String productName = "test";

        assertTrue(createTestSellerAndLogin());

        currentPage = new ProductsPage(driver, wait);
        ((ProductsPage) currentPage).clickAddProductButton();

        currentPage = new AddProductPage(driver, wait);
        ((AddProductPage) currentPage).addProductToCard(productName);
        ((AddProductPage) currentPage).clickHomeButton();

        currentPage = new ProductsPage(driver, wait);
        assertTrue(((ProductsPage) currentPage).productExists(productName));

        ((ProductsPage) currentPage).clickMyProductsButton();
        currentPage = new MyProductsPage(driver, wait);
        assertTrue(((MyProductsPage) currentPage).productExists(productName));
    }
}
