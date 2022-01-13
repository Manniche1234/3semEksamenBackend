package facades;

import dtos.DinnerEventDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DinnerFacadeTest {

    private static EntityManagerFactory emf;
    private static DinnerFacade facade;
    private static User user;
    private static User user1;
    private static User admin;
    private static DinnerEvent dinnerEvent;
    private static DinnerEvent dinnerEvent1;
    private static DinnerEvent dinnerEvent2;
    private static DinnerEvent dinnerEvent3;
    private static Assignment assignment;
    private static Assignment assignment1;
    private static Assignment assignment2;


    public DinnerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DinnerFacade.getDinnerFacade(emf);
    }
    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

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


            DinnerEvent dinnerEvent = new DinnerEvent("gorms","fish",150);
            DinnerEvent dinnerEvent1 = new DinnerEvent("Kofoeds","Kød",200);
            DinnerEvent dinnerEvent2 = new DinnerEvent("Augusts","Guld belagt Hummer",1000);
            DinnerEvent dinnerEvent3 = new DinnerEvent("Hvor? Her","Vegansk",175);
            Assignment assignment = new Assignment("Møller","23415701");
            Assignment assignment1 = new Assignment("Pøller","321123");
            Assignment assignment2 = new Assignment("Støller","654456");

            User user = new User("user", "user1","masnedøgade","23415701","hajmeddig@gmail.com","1993",50000);
            User user1 = new User("user1", "user11","asdsad","23415701","asdasd@gmail.com","1992",50000);
            User admin = new User("admin", "admin1","Hejeje","3211232","yoyoyo@hotmail.com","1932",321123);
            User both = new User("user_admin", "user_admin1","asdasdas","32112332","jasda@gmail.com","2000",32112);

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

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    void getAllEvents() {
        List<DinnerEventDTO> dinnerEventDTOList = facade.getAllEvents();
        assertEquals(dinnerEventDTOList.size(), 4);

    }

    @Test
    void createNewEvent() {
    DinnerEventDTO dinnerEventDTO = new DinnerEventDTO("Postman Per","Laks",32121);
    DinnerEventDTO dinnerEventDTO1 = facade.createNewEvent(dinnerEventDTO);
    assertEquals(dinnerEventDTO1.getId(),5);

    List<DinnerEventDTO> dinnerEventDTOList = facade.getAllEvents();
    assertEquals(dinnerEventDTOList.size(),5);
    }

    @Test
    void updateEvent() {
        DinnerEventDTO dinnerEventDTO = new DinnerEventDTO(new Long(1),new Date(),"Augusts hus", "Kød",123);
        DinnerEventDTO dinnerEventDTO1 = facade.updateEvent(dinnerEventDTO);

        assertEquals(dinnerEventDTO1.getLocation(), "Augusts hus");
        assertEquals(dinnerEventDTO1.getId(), 1);


    }

    @Test
    void deleteEvent() {
        Long id = new Long(3);
        DinnerEventDTO dinnerEventDTO = facade.deleteEvent(id);

        assertNotNull(dinnerEventDTO);

        List<DinnerEventDTO> dinnerEventDTOList = facade.getAllEvents();

        assertEquals(dinnerEventDTOList.size(), 3);


    }
}