package acetoys.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;
import java.time.Duration;

public class TestScenario {

    private static final Duration TEST_DURATION =
            Duration.ofSeconds(Integer.parseInt(System.getProperty("Duration", "60")));

    public static ScenarioBuilder defaultLoadTest =
            scenario("Default Load Test")
                    .during(TEST_DURATION)
                    .on(
                            randomSwitch().on(
                                    Choice.withWeight(60, exec(UserJourney.browseStore)), //60 of the Users%
                                    Choice.withWeight(30, exec(UserJourney.abandonBasket)), //30 of the Users%
                                    Choice.withWeight(10, exec(UserJourney.completePurchase)) //10 of the Users%
                            )
                    );

    public static ScenarioBuilder highPurchaseLoadTest =
            scenario("High Purchase Load Test")
                    .during(TEST_DURATION)
                    .on(
                            randomSwitch().on(
                                    Choice.withWeight(30, exec(UserJourney.browseStore)), //60 of the Users%
                                    Choice.withWeight(30, exec(UserJourney.abandonBasket)), //30 of the Users%
                                    Choice.withWeight(40, exec(UserJourney.completePurchase)) //10 of the Users%
                            )
                    );
}
