package acetoys.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import acetoys.pageobjects.Cart;
import acetoys.pageobjects.Category;
import acetoys.pageobjects.Customer;
import acetoys.pageobjects.Product;
import acetoys.pageobjects.StaticPages;
import acetoys.session.UserSession;
import io.gatling.javaapi.core.ChainBuilder;
import java.time.Duration;

public class UserJourney {

    private static final Duration LOW_PAUSE = Duration.ofMillis(1000);
    private static final Duration HIGH_PAUSE = Duration.ofMillis(3000);

    public static ChainBuilder browseStore =
            exec(UserSession.initSession)
                    .exec(StaticPages.homePage)
                    .pause(HIGH_PAUSE)
                    .exec(StaticPages.outStoryPage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(StaticPages.getInTouchPage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .repeat(3).on(
                            exec(Category.productListByCategory)
                                    .pause(LOW_PAUSE, HIGH_PAUSE)
                                    .exec(Category.cyclePagesOfProducts)
                                    .pause(LOW_PAUSE, HIGH_PAUSE)
                                    .exec(Product.loadProductDetailsPage)
                    );

    public static ChainBuilder abandonBasket =
            exec(UserSession.initSession)
                    .exec(StaticPages.homePage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Category.productListByCategory)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Product.loadProductDetailsPage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Product.addProductToCart);

    public static ChainBuilder completePurchase =
            exec(UserSession.initSession)
                    .exec(StaticPages.homePage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Category.productListByCategory)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Product.loadProductDetailsPage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Product.addProductToCart)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Cart.viewCart)
                    .pause(LOW_PAUSE)
                    .exec(Cart.increaseQuantityInCart)
                    .pause(LOW_PAUSE)
                    .exec(Cart.decreaseQuantityInCart)
                    .pause(LOW_PAUSE)
                    .exec(Cart.checkout)
                    .pause(LOW_PAUSE)
                    .exec(Customer.logout);
}

