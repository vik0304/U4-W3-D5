package Viktor_Vasileski.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Checkout {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="element_id", nullable = false)
    private Element element;
    @Column(name="loan_start")
    private LocalDate loanStart;
    @Column(name="loan_finish")
    private LocalDate loanFinish;
    @Column(name="returned_on")
    private LocalDate returnedOn;

    public Checkout(){}

    public Checkout(User user, Element element, LocalDate loanStart, LocalDate returnedOn){
        this.user=user;
        this.element=element;
        this.loanStart=loanStart;
        this.loanFinish=loanStart.plusDays(30);
        this.returnedOn=returnedOn;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Element getElement() {
        return element;
    }
}
