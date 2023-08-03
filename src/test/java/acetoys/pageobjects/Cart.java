package acetoys.pageobjects;

import static acetoys.session.UserSession.decreaseItemsInBasketForSession;
import static acetoys.session.UserSession.decreaseSessionBasketTotal;
import static acetoys.session.UserSession.increaseItemsInBasketForSession;
import static acetoys.session.UserSession.increaseSessionBasketTotal;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;

public class Cart {

    public static ChainBuilder viewCart =
            doIf(session -> !session.getBoolean("customerLoggedIn"))
                    .then(exec(Customer.login))
                    .exec(
                    http("View Cart")
                            .get("/cart/view")
                            .check(css("#CategoryHeader").is("Cart Overview"))
            );

    public static ChainBuilder increaseQuantityInCart =
            exec(increaseItemsInBasketForSession)
            .exec(increaseSessionBasketTotal)
                    .exec(
                    http("Increase Product Quantity In Cart - Product Name: #{name}")
                            .get("/cart/add/#{id}?cartPage=true")
                            .check(css("#grandTotal").isEL("$#{basketTotal}"))
            );

    public static ChainBuilder decreaseQuantityInCart =
            exec(decreaseItemsInBasketForSession)
            .exec(decreaseSessionBasketTotal)
                    .exec(
                    http("Decrease Product Quantity In Cart - Product Name: #{name}")
                            .get("/cart/subtract/#{id}")
                            .check(css("#grandTotal").isEL("$#{basketTotal}"))
            );

    public static ChainBuilder checkout =
            exec(
                    http("Checkout")
                            .get("/cart/checkout")
                            .check(substring("Your products are on their way to you now!!"))
            );
}
