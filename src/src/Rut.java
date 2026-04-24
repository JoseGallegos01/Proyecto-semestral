public class Rut implements IdPersona{
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

        return false;
    }
}
