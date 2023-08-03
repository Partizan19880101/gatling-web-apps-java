package acetoys.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Customer {

    private static Iterator<Map<String, Object>> loginFeeder =
            Stream.generate((Supplier<Map<String, Object>>) () -> {
                Random random = new Random();
                int userId = random.nextInt(3 - 1 + 1) + 1;

                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("userId", "user" + userId);
                hashMap.put("password", "pass");
                return hashMap;
            }).iterator();

    public static ChainBuilder login =
            feed(loginFeeder)
                    .exec(
                    http("Login User")
                            .post("/login")
                            .formParam("_csrf", "#{csrfToken}")
                            .formParam("username", "#{userId}")
                            .formParam("password", "#{password}")
                            .check(css("#_csrf", "content").saveAs("csrfTokenLoggedIn"))
            )
                    .exec(session -> session.set("customerLoggedIn", true));

    public static ChainBuilder logout =
            randomSwitch().on(
                    Choice.withWeight(10, exec(
                            http("Logout")
                                    .post("/logout")
                                    .formParam("_csrf", "#{csrfTokenLoggedIn}")
                                    .check(css("#LoginLink").is("Login"))
                    )));
}
