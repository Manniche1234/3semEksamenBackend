package rest;

import com.nimbusds.jose.shaded.json.JSONObject;
import dtos.DinnerEventDTO;
import entities.Assignment;
import entities.DinnerEvent;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class DinnerEventResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from Assignment").executeUpdate();
            em.createQuery("delete from DinnerEvent ").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createNativeQuery("ALTER TABLE dinner_event AUTO_INCREMENT = 1").executeUpdate();


            DinnerEvent dinnerEvent = new DinnerEvent("gorms", "fish", 150);
            DinnerEvent dinnerEvent1 = new DinnerEvent("Kofoeds", "Kød", 200);
            DinnerEvent dinnerEvent2 = new DinnerEvent("Augusts", "Guld belagt Hummer", 1000);
            DinnerEvent dinnerEvent3 = new DinnerEvent("Hvor? Her", "Vegansk", 175);
            Assignment assignment = new Assignment("Møller", "23415701");
            Assignment assignment1 = new Assignment("Pøller", "321123");
            Assignment assignment2 = new Assignment("Støller", "654456");

            User user = new User("user", "user1", "masnedøgade", "23415701", "hajmeddig@gmail.com", "1993", 50000);
            User user1 = new User("user1", "user11", "asdsad", "23415701", "asdasd@gmail.com", "1992", 50000);
            User admin = new User("admin", "admin1", "Hejeje", "3211232", "yoyoyo@hotmail.com", "1932", 321123);
            User both = new User("user_admin", "user_admin1", "asdasdas", "32112332", "jasda@gmail.com", "2000", 32112);

            user1.addAssignment(assignment1);
            user.addAssignment(assignment);
            user.addAssignment(assignment1);
            user.addAssignment(assignment2);

            dinnerEvent.addAssignment(assignment);

            dinnerEvent1.addAssignment(assignment1);

            dinnerEvent2.addAssignment(assignment2);


            Role userRole = new Role("user");
            Role adminRole = new Role("admin");

            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(dinnerEvent);
            em.persist(dinnerEvent1);
            em.persist(dinnerEvent2);
            em.persist(dinnerEvent3);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user1);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
//                .when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    @Test
    void getAllEvents() {
        login("user", "user1");
        List<DinnerEventDTO> dinnerEventDTOList;
        dinnerEventDTOList = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/event/all")
                .then().statusCode(200)
                .extract().body().jsonPath().getList("", DinnerEventDTO.class);
        assertNotNull(dinnerEventDTOList);
        assertEquals(dinnerEventDTOList.size(), 4);

    }

    @Test
    void createEvent() {
        login("admin", "admin1");

        JSONObject reqestParams = new JSONObject();
        reqestParams.put("location", "Younes");
        reqestParams.put("dish", "fisk");
        reqestParams.put("pricePrPerson", 210);
        DinnerEvent dinnerEvent;
        dinnerEvent = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(reqestParams.toString())
                .when()
                .post("/event/createevent")
                .then().statusCode(200)
                .extract().body().jsonPath().getObject("", DinnerEvent.class);

        assertTrue(dinnerEvent.getId() > 0);
        assertEquals(dinnerEvent.getLocation(), "Younes");
    }

    @Test
    void updateEvent() {
        login("admin", "admin1");

        JSONObject reqestParams = new JSONObject();
        reqestParams.put("id", 1);
        reqestParams.put("date", "Jan 13, 2022, 4:15:53 PM");
        reqestParams.put("location", "Younes");
        reqestParams.put("dish", "fisk");
        reqestParams.put("pricePrPerson", 210);

        DinnerEvent dinnerEvent;
        dinnerEvent = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(reqestParams.toString())
                .when()
                .put("/event/updateevent")
                .then().statusCode(200)
                .extract().body().jsonPath().getObject("", DinnerEvent.class);

        assertEquals(dinnerEvent.getId(), 1);
        assertEquals(dinnerEvent.getLocation(), "Younes");
    }

    @Test
    void deleteEvent() {
        login("admin", "admin1");

        DinnerEventDTO dinnerEventDTO;
        dinnerEventDTO = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .delete("/event/deleteevent/3")
                .then().statusCode(200)
                .extract().body().jsonPath().getObject("", DinnerEventDTO.class);

        assertNotNull(dinnerEventDTO);

        List<DinnerEventDTO> dinnerEventDTOList;
        dinnerEventDTOList = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/event/all")
                .then().statusCode(200)
                .extract().body().jsonPath().getList("", DinnerEventDTO.class);
        assertNotNull(dinnerEventDTOList);
        assertEquals(dinnerEventDTOList.size(), 3);
    }
}