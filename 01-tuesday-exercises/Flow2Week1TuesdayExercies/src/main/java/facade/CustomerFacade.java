package facade;

import entities.Address;
import entities.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CustomerFacade {
    
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("persistence");
    private static EntityManager em;
    
    public Customer getCustomer(Long id) {
        em = EMF.createEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Customer> getCustomers() {
        em = EMF.createEntityManager();
        try {
            TypedQuery<Customer> tq = em.createQuery("SELECT c FROM Customer c", Customer.class);
            return tq.getResultList();
        } finally {
            em.close();
        }
        
    }
    
    public Customer addCustomer(Customer cust) {
        em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }
    }
    
    public Customer deleteCustomer(Long id) {
        em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Customer cust = em.find(Customer.class, id);
            em.remove(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }
        
    }
    
    public Customer editCustomer(Customer cust) {
        em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Customer c = em.find(Customer.class, cust.getId());
            c.setFirstName(cust.getFirstName());
            c.setLastName(cust.getLastName());
            em.getTransaction().commit();
            return c;
        } finally {
            em.close();
        }
    }
    
}
