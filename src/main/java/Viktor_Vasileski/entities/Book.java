package Viktor_Vasileski.entities;

import jakarta.persistence.Entity;

@Entity
public class Book extends Element {
    private String author;
    private String genre;

    public Book() {};

    public Book(String title, int year, int pages, String author, String genre) {
        super(title, year, pages);
        this.author=author;
        this.genre=genre;
    };

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                "} " + super.toString();
    }
}
