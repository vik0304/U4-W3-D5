package Viktor_Vasileski.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="library_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_number")
    private long cardNumber;
    private String name;
    private String surname;
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;
    @OneToMany(mappedBy ="user")
    private List<Checkout> loansList = new ArrayList<>();

    public User(){};

    public User(String name, String surname, LocalDate dateOfBirth){
        this.name=name;
        this.surname=surname;
        this.dateOfBirth=dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
