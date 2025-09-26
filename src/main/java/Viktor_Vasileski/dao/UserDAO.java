package Viktor_Vasileski.dao;

import Viktor_Vasileski.entities.User;
import Viktor_Vasileski.exeptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UserDAO {
    private final EntityManager entityManager;

    public UserDAO (EntityManager entityManager){
        this.entityManager=entityManager;
    }

    public void save(User newUser){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newUser);
        transaction.commit();
        System.out.println("L'utente "+ newUser.getName() + " " + newUser.getSurname() + " è stato aggiunto.");
    }

    public User findById(long cardNumber){
        User found = entityManager.find(User.class, cardNumber);
        if(found==null) throw new NotFoundException(cardNumber);
        return found;
    }

    public void findAndDelete(long cardNumber){
        User found = this.findById(cardNumber);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println("L'utente "+ found.getName() + " " + found.getSurname() + " è stato rimosso.");
    }
}
