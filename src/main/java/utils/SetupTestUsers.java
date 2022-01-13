package utils;


import entities.DinnerEvent;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;

public class SetupTestUsers {

  public static void main(String[] args) {
    setupUsers();
  }

  public static void setupUsers(){
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "user1","masnedøgade","23415701","hajmeddig@gmail.com","1993",50000);
    User admin = new User("admin", "admin1","Hejeje","3211232","yoyoyo@hotmail.com","1932",321123);
    User both = new User("user_admin", "user_admin1","asdasdas","32112332","jasda@gmail.com","2000",32112);
    DinnerEvent dinnerEvent = new DinnerEvent("gorms","fish",150);
    DinnerEvent dinnerEvent1 = new DinnerEvent("Kofoeds","Kød",200);
    DinnerEvent dinnerEvent2 = new DinnerEvent("Augusts","Guld belagt Hummer",1000);
    DinnerEvent dinnerEvent3 = new DinnerEvent("Hvor? Her","Vegansk",175);
    if(admin.getPassword().equals("test")||user.getPassword().equals("test")||both.getPassword().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
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
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getPassword());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
  }

}
