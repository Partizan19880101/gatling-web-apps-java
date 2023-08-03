package acetoys.pageobjects;

import static acetoys.session.UserSession.increaseItemsInBasketForSession;
import static acetoys.session.UserSession.increaseSessionBasketTotal;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

public class Product {

    private static final FeederBuilder<Object> productFeeder =
            jsonFile("data/productDetails.json").random();

    public static ChainBuilder loadProductDetailsPage =
            feed(productFeeder)
                    .exec(
                    http("Load Product Details Page - Product: #{name}")
                            .get("/product/#{slug}")
                            .check(css("#ProductDescription").isEL("#{description}"))

            );
    public static ChainBuilder addProductToCart =
            exec(increaseItemsInBasketForSession)
                    .exec(
                    http("Add Product To Cart - ProductName: #{name}")
                            .get("/cart/add/#{id}")
                            .check(substring("You have <span>#{itemsInBasket}</span> products in your Basket."))
            ).exec(increaseSessionBasketTotal);
}
