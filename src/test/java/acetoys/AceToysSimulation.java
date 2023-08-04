package acetoys;

import acetoys.simulation.TestPopulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static acetoys.RunType.CLOSED_MODEL;
import static acetoys.RunType.COMPLEX_INJECTION;
import static acetoys.RunType.INSTANT_USERS;
import static acetoys.RunType.RAMP_USERS;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AceToysSimulation extends Simulation {

    private static final String TEST_TYPE = System.getProperty("TEST_TYPE", "INSTANT_USERS");

    private static final String DOMAIN = "acetoys.uk";

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://" + DOMAIN)
            .inferHtmlResources(AllowList(),
                    DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico",
                            ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png",
                            ".*detectportal\\.firefox\\.com.*"))
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.9,ru;q=0.8");

    //  private ScenarioBuilder scn = scenario("AceToysSimulation")
//          .exec(UserJourney.browseStore);
//          .exec(UserJourney.abandonBasket);
//          .exec(UserJourney.completePurchase);
    {
//	  setUp(TestScenario.highPurchaseLoadTest
//              .injectOpen(atOnceUsers(10))) //10 Users
//              .protocols(httpProtocol);

//      if (TEST_TYPE == "INSTANT_USERS") {
//            setUp(TestPopulation.instantUsers).protocols(httpProtocol);
//      } else if (TEST_TYPE == "RAMP_USERS") {
//            setUp(TestPopulation.rampUsers).protocols(httpProtocol);
//      } else if (TEST_TYPE == "COMPLEX_INJECTION") {
//            setUp(TestPopulation.complexInjection).protocols(httpProtocol);
//      } else if (TEST_TYPE == "CLOSED_MODEL") {
//            setUp(TestPopulation.closeModel).protocols(httpProtocol);
//      } else {
//            setUp(TestPopulation.instantUsers).protocols(httpProtocol);
//      }

        switch (TEST_TYPE) {
            case "INSTANT_USERS":
                setUp(TestPopulation.instantUsers).protocols(httpProtocol);
                break;
            case "RAMP_USERS":
                setUp(TestPopulation.rampUsers).protocols(httpProtocol);
                break;
            case "COMPLEX_INJECTION":
                setUp(TestPopulation.complexInjection).protocols(httpProtocol);
                break;
            case "CLOSED_MODEL":
                setUp(TestPopulation.closeModel).protocols(httpProtocol);
                break;
            default:
                setUp(TestPopulation.instantUsers).protocols(httpProtocol);
                break;
        }
    }
}
