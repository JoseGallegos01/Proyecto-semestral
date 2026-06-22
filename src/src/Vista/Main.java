package Vista;

import Excepciones.SVPException;

public class Main {
    public static void main(String[] args) throws SVPException {
        try {
            UISVP.getInstance().menu();
        }catch (SVPException e) {
            throw e;
        }
    }
}