package Vista;

import Excepciones.SistemaVentaPasajesException;

public class Main {
    public static void main(String[] args) throws SistemaVentaPasajesException {
        try {
            UISVP.getInstance().menu();
        }catch (SistemaVentaPasajesException e) {
            throw e;
        }
    }
}