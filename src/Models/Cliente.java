package Models;

public class Cliente {
    private String id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String genero;
    private String direccion;
    private int edad; 
    private String correoElectronico;
    private String telefonoCasa;
    private String telefonoCelular;

    public Cliente() {
        this.id = "";
        this.nombre = "";
        this.primerApellido = "";
        this.segundoApellido = "";
        this.genero = "";
        this.direccion = "";
        this.edad = 0;
        this.correoElectronico = "";
        this.telefonoCasa = "";
        this.telefonoCelular = "";
    }

    public Cliente(String id, String nombre, String primerApellido, String segundoApellido, String genero, String direccion, int edad, String correoElectronico, String telefonoCasa, String telefonoCelular) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.genero = genero;
        this.direccion = direccion;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.telefonoCasa = telefonoCasa;
        this.telefonoCelular = telefonoCelular;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente {")
                .append("id='").append(id).append("', ")
                .append("nombre='").append(nombre).append("', ")
                .append("primerApellido='").append(primerApellido).append("', ")
                .append("segundoApellido='").append(segundoApellido).append("', ")
                .append("genero='").append(genero).append("', ")
                .append("direccion='").append(direccion).append("', ")
                .append("edad=").append(edad).append(", ")
                .append("correoElectronico='").append(correoElectronico).append("', ")
                .append("telefonoCasa='").append(telefonoCasa).append("', ")
                .append("telefonoCelular='").append(telefonoCelular).append("'")
                .append("}");
        return sb.toString();
    }

}
