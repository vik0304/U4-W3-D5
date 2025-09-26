package Viktor_Vasileski.dao;

import Viktor_Vasileski.entities.Checkout;
import Viktor_Vasileski.entities.Element;
import Viktor_Vasileski.exeptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class CheckoutDAO {
    private final EntityManager entityManager;

    public CheckoutDAO(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    public void save(Checkout newCheckout){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newCheckout);
        transaction.commit();
        System.out.println("Il checkout con ID " + newCheckout.getId() + " di " + newCheckout.getUser().getName() + " " + newCheckout.getUser().getSurname() + " è stato aggiunto nel sistema");
    }

    public Checkout findById(UUID id){
        Checkout found = entityManager.find(Checkout.class, id);
        if(found==null) throw new NotFoundException(id);
        return found;
    }

    public void findAndRemove(UUID id){
        Checkout found = findById(id);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println("Il checkout con ID " + found.getId() + " di " + found.getUser().getName() + " " + found.getUser().getSurname() + " è stato rimosso dal sistema");
    }

    public List<Element> findLoanedElement(long cardNumber){
        TypedQuery<Element> query = entityManager.createQuery("SELECT c.element FROM Checkout c WHERE c.user.cardNumber = :id AND c.returnedOn IS NULL", Element.class);
        query.setParameter("id", cardNumber);
        return query.getResultList();
    }

    public List<Checkout> findExpiredLoans(LocalDate today){
        TypedQuery<Checkout> query = entityManager.createQuery("SELECT c FROM Checkout c WHERE c.returnedOn IS NULL AND c.loanFinish < :today", Checkout.class);
        query.setParameter("today", today);
        return query.getResultList();
    }
}
