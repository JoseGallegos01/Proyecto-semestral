public class Rut implements IdPersona{
    //Cristobal Jeldres
    private int numero;
    private char dv;
    //falta metodo of y equals por terminar
    public Rut(int numero, char dv) {
        this.numero = numero;
        this.dv = dv;
    }

    public int getNumero() {
        return numero;
    }

    public char getDv() {
        return dv;
    }

    @Override
    public String toString() {
        return numero + "-" + dv;
    }

    @Override
    public boolean equals(Object otro) {
        Rut r = (Rut) otro;

        return numero == r.numero &&
                dv == r.dv;
    }
}
