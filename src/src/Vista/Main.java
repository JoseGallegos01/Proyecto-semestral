package Vista;

import Excepciones.SistemaVentaPasajesException;

public class Main {
    public static void main(String[] args) {
        try {
            UISVP.getInstance().menu();
        }catch (SistemaVentaPasajesException e) {
            throw new SistemaVentaPasajesException(e.getMessage());
        }
    }
}