package Viktor_Vasileski.exeptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long cardNumber) {
        super("L'utente con numero di tessera " + cardNumber + " non è stato trovato");
    }
    public NotFoundException(UUID id) {
        super("L'elemento con id " + id + " non è stato trovato");
    }
}


