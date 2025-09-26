package Viktor_Vasileski.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Element {
    @Id
    @GeneratedValue
    protected UUID id;
    protected String title;
    protected int year;
    protected int pages;
    @OneToMany(mappedBy = "element")
    protected List<Checkout> loansList = new ArrayList<>();

    public Element(){}

    public Element(String title, int year, int pages){
        this.title=title;
        this.year=year;
        this.pages=pages;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
