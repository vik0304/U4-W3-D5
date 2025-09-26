package Viktor_Vasileski.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Magazine extends Element {
    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

    public Magazine(){};

    public Magazine(String title, int year, int pages, Periodicity periodicity){
        super(title, year, pages);
        this.periodicity=periodicity;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "periodicity=" + periodicity +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                "} " + super.toString();
    }
}
