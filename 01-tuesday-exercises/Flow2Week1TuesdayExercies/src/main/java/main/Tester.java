package main;

import entities.Address;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import entities.Customer;
import facade.CustomerFacade;

public class Tester {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("persistence");
    private static final CustomerFacade CF = new CustomerFacade();
    private static EntityManager em;

    public static void main(String[] args) {
//        testCustomers();
        Persistence.generateSchema("persistence", null);
//        oneToMany();
//        manyToMany();
        testFacade();
    }

    private static void testFacade() {
        Address a1 = new Address("Mafia Street", "Copenhagen");
        Address a2 = new Address("Police Street", "Copenhagen");
        Address a3 = new Address("Public Street", "Odense");

        Customer c1 = new Customer("Jack", "Smith");
        Customer c2 = new Customer("Elizabeth", "Smith");
        Customer c3 = new Customer("Peter", "Jackson");

        c1.addAddress(a1);
        c1.addAddress(a2);
        c2.addAddress(a3);

        CF.addCustomer(c1);
        CF.addCustomer(c2);
        CF.addCustomer(c3);

        System.out.println("We had " + CF.getCustomers().size() + " customers in our database");
        CF.deleteCustomer(c2.getId());
        System.out.println("We now have " + CF.getCustomers().size() + " customers in our database");

        c1 = CF.getCustomer(c1.getId());
        System.out.println("We can get " + c1.getFirstName() + ", living on " + c1.getAddresses().get(0).getStreet());

        String oldName = c1.getFirstName();
        String newName = "Billy";
        c1.setFirstName(newName);
        c1 = CF.editCustomer(c1);
        System.out.println(oldName + " is now " + c1.getFirstName());

    }

    private static void manyToMany() {
        Address a1 = new Address("Mafia Street", "Copenhagen");
        Address a2 = new Address("Police Street", "Copenhagen");
        Address a3 = new Address("Public Street", "Odense");

        Customer c1 = new Customer("Jack", "Smith");
        Customer c2 = new Customer("Elizabeth", "Smith");
        Customer c3 = new Customer("Peter", "Jackson");

        c1.addAddress(a1);
        c1.addAddress(a2);
        c2.addAddress(a3);

        em = EMF.createEntityManager();
        em.getTransaction().begin();
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.getTransaction().commit();
    }

    private static void oneToMany() {
        Address a1 = new Address("Mafia Street", "Copenhagen");
        Address a2 = new Address("Police Street", "Copenhagen");
        Address a3 = new Address("Public Street", "Odense");
        Address a4 = new Address("Hidden Street", "Aarhus");

        Customer c1 = new Customer("Jack", "Smith");
        Customer c2 = new Customer("Elizabeth", "Smith");
        Customer c3 = new Customer("Peter", "Jackson");
        Customer c4 = new Customer("Niels", "Patrick");

        c1.addAddress(a1);
        c1.addAddress(a3);

        c2.addAddress(a2);
        // Here I try to see what happens if another customer tries to 'claim' the address
        c2.addAddress(a3);

        c3.addAddress(a4);

        em = EMF.createEntityManager();

        em.getTransaction().begin();
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.getTransaction().commit();
    }

    private static void testCustomers() {
        //Assignment Collections of basic types, Part 3
        em = EMF.createEntityManager();

        Customer c1 = new Customer("Jack", "Smith");
        c1.addHobby("Fishing");
        c1.addHobby("Chess");
        c1.addHobby("Gaming");
        c1.addPhone("12 34 56 78", "Nokia");

        Customer c2 = new Customer("Elizabeth", "Smith");
        c2.addHobby("Chess");
        c2.addHobby("Movies");

        Customer c3 = new Customer("Peter", "Jackson");
        c3.addHobby("Fighting");
        c3.addPhone("32 65 98 64", "Nokia");
        c3.addPhone("14 25 58 47", "Samsung");

        em.getTransaction().begin();
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.getTransaction().commit();
    }

}
