package Viktor_Vasileski.dao;

import Viktor_Vasileski.entities.Element;
import Viktor_Vasileski.exeptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class ElementDAO {
    private final EntityManager entityManager;

    public ElementDAO(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    public void save (Element newElement){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newElement);
        transaction.commit();
        System.out.println("L'elemento " + newElement.getTitle() + " con id numero " + newElement.getId() + " è stato aggiunto");
    }

    public Element findById(UUID id){
        Element found = entityManager.find(Element.class, id);
        if(found==null) throw new NotFoundException(id);
        return found;
    }

    public void findAndDelete (UUID id){
        Element found = this.findById(id);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println("L'elemento " + found.getTitle() + " con id numero " + found.getId() + " è stato rimosso");
    }

    public List<Element> findByYear (int year){
        TypedQuery<Element> query = entityManager.createQuery("SELECT e FROM Element e WHERE e.year = :year", Element.class);
        query.setParameter("year", year);
        return query.getResultList();
    }

    public List<Element> findByAuthor (String author){
        TypedQuery<Element> query = entityManager.createQuery("SELECT e FROM Element e WHERE LOWER(e.author) LIKE LOWER(:author)", Element.class);
        query.setParameter("author", author);
        return query.getResultList();
    }

    public List<Element> findByTitle (String title){
        TypedQuery<Element> query = entityManager.createQuery("SELECT e FROM Element e WHERE LOWER(e.title) LIKE LOWER(:name)", Element.class);
        query.setParameter("name", "%" + title + "%");
        return query.getResultList();
    }
}
