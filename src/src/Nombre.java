public class Nombre {
    //Vicente Salinas

    private Tratamiento tratamiento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Tratamiento getTratamiento() {
        return tratamiento;
    }
    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    @Override
    public String toString(){
        return tratamiento.toString() + " " + nombres + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.nombres.equals(((Nombre)o).nombres) &&
                this.apellidoPaterno.equals(((Nombre)o).apellidoPaterno) &&
                this.apellidoMaterno.equals(((Nombre)o).apellidoMaterno);
    }
}
