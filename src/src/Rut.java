public class Rut implements IdPersona{
    //Cristobal Jeldres
    private int numero;
    private char dv;

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
        String texto = String.valueOf(numero);
        //Y el ValueOf de aca
        //https://beginnersbook.com/2017/10/java-string-valueof-method/

        if(texto.length() == 8){
            texto = texto.substring(0,2) + "." +
                    texto.substring(2,5) + "." +
                    texto.substring(5,8);
        }else{
            texto = texto.substring(0,1) + "." +
                    texto.substring(1,4) + "." +
                    texto.substring(4,7);
        }
        //el subString lo saque de aca
        //https://www.ionos.com/es-us/digitalguide/paginas-web/desarrollo-web/java-string-substring/

        return texto + "-" + dv;
    }

    @Override
    public boolean equals(Object otro) {
        Rut r = (Rut) otro;

        return numero == r.numero &&
                dv == r.dv;
    }

    public static Rut of(String rutConDv) {

        if (rutConDv == null)
            return null;

        char dv = rutConDv.charAt(rutConDv.length() - 1);

        String numeroTexto = rutConDv.substring(0, rutConDv.length() - 2);

        numeroTexto = numeroTexto.substring(0,1) +
                numeroTexto.substring(2,5) +
                numeroTexto.substring(6,9);

        int numero = Integer.parseInt(numeroTexto);
        //El parceInt lo saque  de aqui
        //https://www.freecodecamp.org/espanol/news/cadena-de-java-a-int-como-convertir-una-cadena-en-un-numero-entero/
        return new Rut(numero, dv);
    }

}
