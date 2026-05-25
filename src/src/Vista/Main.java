package Vista;

import Excepciones.SistemaVentaPasajesException;

public class Main {
    public static void main(String[] args) throws RuntimeException {
        try {
            UISVP.getInstance().menu();
        }catch (RuntimeException e) {
            throw e;
        }
    }
}