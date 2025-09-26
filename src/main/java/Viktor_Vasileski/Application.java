package Viktor_Vasileski;

import Viktor_Vasileski.dao.CheckoutDAO;
import Viktor_Vasileski.dao.ElementDAO;
import Viktor_Vasileski.dao.UserDAO;
import Viktor_Vasileski.entities.*;
import Viktor_Vasileski.exeptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class Application {
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory("u4w3d5");
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
    EntityManager em=emf.createEntityManager();
    UserDAO ud = new UserDAO(em);
    ElementDAO ed = new ElementDAO(em);
    CheckoutDAO cd = new CheckoutDAO(em);
    System.out.println("Database caricato.");

    mainMenu(ud, ed, cd);

    s.close();
    em.close();
    emf.close();
    }

    public static void mainMenu(ElementDAO ed, CheckoutDAO cd){
        int option;
        boolean isWorking= true;
        System.out.println("Benvenuto scegli opzione desideri: (seleziona inserendo numero e premendo invio) ");
        while(isWorking){
            System.out.println("1- Aggiungi un elemento al catalogo");
            System.out.println("2- Rimuovi un elemento del catalogo dato un id");
            System.out.println("3- Ricerca elemento per ID");
            System.out.println("4- Ricerca per anno pubblicazione");
            System.out.println("5- Ricerca per autore");
            System.out.println("6- Ricerca per titolo o parte di esso");
            System.out.println("7- Ricerca elementi attualmente in prestito tramite id utente");
            System.out.println("8- Ricerca tutti i prestiti scaduti e ancora non restituiti");
            System.out.println("0- exit ");
            try{
                option = Integer.parseInt(s.nextLine());
                switch (option) {
                    case 0:
                        isWorking = false;
                        break;
                    case 1:
                        addElement(ed);
                        break;
                    case 2:
                        removeById(ed);
                        break;
                    case 3:
                        findById(ed);
                        break;
                    case 4:
                        findByYear(ed);
                        break;
                    case 5:
                        findByAuthor(ed);
                        break;
                    case 6:
                        findByTitle(ed);
                        break;
                    case 7:
                        findLoansById(cd);
                        break;
                    case 8:
                        findExpiredLoans(cd);
                        break;
                    default:
                        isWorking = true;
                        break;
                }
            }catch(NumberFormatException e){
                System.out.println("Errore: devi inserire un numero intero positivo.");
            }
        }
    }

    public static void addElement(ElementDAO ed){
        int selected;
        System.out.println("Desideri inserire un libro o una rivista? (inserisci un numero e premi invio) ");
        System.out.println("1- Libro");
        System.out.println("2- Rivista");
        try{
            selected = Integer.parseInt(s.nextLine());
            if (selected==1 || selected==2){
                System.out.println("Inserisci il titolo:");
                String title = s.nextLine();
                System.out.println("Inserisci l'anno di pubblicazione");
                int year = Integer.parseInt(s.nextLine());
                System.out.println("Inserisci il numero di pagine");
                int pages = Integer.parseInt(s.nextLine());
                if (selected == 1) {
                    System.out.println("Inserisci il nome dell'autore");
                    String author = s.nextLine();
                    System.out.println("Inserisci il genere");
                    String genre = s.nextLine();
                    Book newBook = new Book(title, year, pages, author, genre);
                    ed.save(newBook);
                } else if (selected == 2) {
                    System.out.println("La rivista che desideri inserire è? (inserisci un numero e premi invio) ");
                    System.out.println("1- Settimanale");
                    System.out.println("2- Mensile");
                    System.out.println("3- Semestrale");
                    System.out.println("0- Annullare creazione");
                    try{
                        int selection = Integer.parseInt(s.nextLine());
                        switch (selection) {
                            case 0:
                                System.out.println("Creazione annullata, verrai portato nel menù principale");
                                break;
                            case 1:
                                Periodicity periodicty1 = Periodicity.SETTIMANALE;
                                Magazine newMagazine1= new Magazine(title, year, pages, periodicty1);
                                ed.save(newMagazine1);
                                break;
                            case 2:
                                Periodicity periodicty2 = Periodicity.MENSILE;
                                Magazine newMagazine2= new Magazine(title, year, pages, periodicty2);
                                ed.save(newMagazine2);
                                break;
                            case 3:
                                Periodicity periodicty3 = Periodicity.SEMESTRALE;
                                Magazine newMagazine3= new Magazine(title, year, pages, periodicty3);
                                ed.save(newMagazine3);
                                break;
                            default:
                                System.out.println("Creazione annullata, verrai portato nel menù principale");
                                break;
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Errore: devi inserire un numero intero positivo.");
                    }
                }
            }else{
                System.out.println("Hai selezionato un numero non valido, verrai riportato la menu principale");
            }
        }catch(NumberFormatException e){
            System.out.println("Errore: devi inserire un numero intero positivo.");
        }
    }

    public static void removeById(ElementDAO ed){
        System.out.println("Inserisci l'id del libro che vuoi rimuovere");
        try{
            UUID id = UUID.fromString(s.nextLine());
            try {
                ed.findAndDelete(id);
                System.out.println("Elemento rimosso correttamente, verrai riportato al menù principale");
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } catch (IllegalArgumentException e){
            System.out.println("Valore UUID non valido.");
        }
    }

    public static void findById(ElementDAO ed){
        System.out.println("Inserisci l'id del libro che vuoi visualizzare");
        try{
            UUID id = UUID.fromString(s.nextLine());
            try{
                Element found = ed.findById(id);
                System.out.println("Ecco l'elemento richiesto:");
                System.out.println(found);
            }catch(NotFoundException e){
                System.out.println(e.getMessage());
            }
        } catch (IllegalArgumentException e){
            System.out.println("Valore UUID non valido.");
        }
    }

    public static void findByYear(ElementDAO ed){
        System.out.println("Inserisci l'anno di pubblicazione che si vuole osservare");
        int year = Integer.parseInt(s.nextLine());
        List<Element> results = ed.findByYear(year);
        System.out.println("Le pubblicazioni del " + year + " sono:");
        results.forEach(x-> System.out.println("Titolo: " + x.getTitle()));
    }

    public static void findByAuthor(ElementDAO ed){
        System.out.println("Inserisci l'autore che si vuole cercare");
        String author = s.nextLine();
        List<Element> results = ed.findByAuthor(author);
        System.out.println("Le pubblicazioni di " + author + " sono:");
        results.forEach(x-> System.out.println("Titolo: " + x.getTitle() + ". ID: " + x.getId()));
    }

    public static void findByTitle(ElementDAO ed){
        System.out.println("Inserisci il titolo o parte di titolo che si vuole cercare");
        String title = s.nextLine();
        List<Element> results = ed.findByTitle(title);
        System.out.println("Risultati della ricerca fatta tramite: " + title + "; sono:");
        results.forEach(x-> System.out.println("Titolo: " + x.getTitle()));
    }

    public static void findLoansById(CheckoutDAO cd){
        System.out.println("Inserisci il numero di tessera dell'utente di cui si vogliono verificare le prenotazioni");
        long cardNumber = Long.parseLong(s.nextLine());
        List<Element> results = cd.findLoanedElement(cardNumber);
        System.out.println("I prestiti ancora attivi per l'utente con id " + cardNumber + " sono:");
        results.forEach(x-> System.out.println("ID: " + x.getId() + " e titolo: " + x.getTitle()));
    }

    public static void findExpiredLoans(CheckoutDAO cd){
        LocalDate today = LocalDate.now();
        List<Checkout> expired = cd.findExpiredLoans(today);
        expired.forEach(x -> System.out.println("Prestito scaduto: " + x.getElement().getTitle() + " per utente " + x.getUser().getName() + " " + x.getUser().getSurname()));
    }
}

// istanze create con IA per verificare il corretto funzionamento del database e dei metodi
//
//Book b1 = new Book("Il nome della rosa", 1980, 512, "Umberto Eco", "Romanzo storico");
//Book b2 = new Book("Clean Code", 2008, 464, "Robert C. Martin", "Informatica");
//Book b3 = new Book("Harry Potter e la pietra filosofale", 1997, 223, "J.K. Rowling", "Fantasy");
//Book b4 = new Book("Il Signore degli Anelli", 1954, 1178, "J.R.R. Tolkien", "Fantasy");
//Book b5 = new Book("La coscienza di Zeno", 1923, 400, "Italo Svevo", "Romanzo psicologico");
//Magazine m1 = new Magazine("National Geographic", 2023, 120, Periodicity.MENSILE);
//Magazine m2 = new Magazine("Focus", 2024, 90, Periodicity.SETTIMANALE);
//Magazine m3 = new Magazine("Time", 2022, 75, Periodicity.SETTIMANALE);
//Magazine m4 = new Magazine("La Settimana Enigmistica", 2025, 60, Periodicity.SETTIMANALE);
//Magazine m5 = new Magazine("Science", 2024, 110, Periodicity.SEMESTRALE);
//User u1 = new User("Marco", "Rossi", LocalDate.of(1995, 1, 1));
//User u2 = new User("Giulia", "Bianchi", LocalDate.of(2000, 5, 12));
//User u3 = new User("Luca", "Verdi", LocalDate.of(1988, 7, 23));
//User u4 = new User("Sara", "Neri", LocalDate.of(1992, 11, 3));
//User u5 = new User("Paolo", "Gialli", LocalDate.of(1975, 3, 30));
// prestito fatto oggi
//Checkout c1 = new Checkout(u1, b1, LocalDate.now(), null);
// prestito già scaduto (iniziato 2 mesi fa e non restituito)
//Checkout c2 = new Checkout(u2, m1, LocalDate.now().minusDays(60), null);
// prestito in corso ma ancora valido (iniziato 10 giorni fa)
//Checkout c3 = new Checkout(u3, b3, LocalDate.now().minusDays(10), null);
// prestito scaduto ma restituito ieri
//Checkout c4 = new Checkout(u4, m4, LocalDate.now().minusDays(40), LocalDate.now().minusDays(1));
// prestito in corso iniziato 25 giorni fa (scade tra 5 giorni)
//Checkout c5 = new Checkout(u5, b5, LocalDate.now().minusDays(25), null);
//
//        ed.save(b1);
//        ed.save(b2);
//        ed.save(b3);
//        ed.save(b4);
//        ed.save(b5);
//        ed.save(m1);
//        ed.save(m2);
//        ed.save(m3);
//        ed.save(m4);
//        ed.save(m5);
//        ud.save(u1);
//        ud.save(u2);
//        ud.save(u3);
//        ud.save(u4);
//        ud.save(u5);
//        cd.save(c1);
//        cd.save(c2);
//        cd.save(c3);
//        cd.save(c4);
//        cd.save(c5);

//  User u6 = new User("Giulia", "Fumagalli", LocalDate.of(2001, 6, 17));
//  Magazine m6 = new Magazine("National Geographic Plus", 2023, 150, Periodicity.MENSILE);
//  Checkout c2 = new Checkout(u6, m6, LocalDate.now().minusDays(47), null);
//  Book b6 = new Book("Paloma", 1980, 120, "Italo Calvino", "Romanzo storico");
//  Book b7 = new Book("Il pendolo di Foucault", 1988, 312, "Umberto Eco", "Romanzo storico");

//        ud.save(u6);
//        ed.save(m6);
//        cd.save(c2);
//        ed.save(b6);