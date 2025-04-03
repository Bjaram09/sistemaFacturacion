package Models;

public class Detalle {
    private String id;
    private Producto infoProducto;
    private int cantidad;

    public Detalle() {
        this.id = "";
        this.infoProducto = new Producto();
        this.cantidad = 0;
    }

    public Detalle(String id, Producto infoProducto, int cantidad) {
        this.id = id;
        this.infoProducto = infoProducto;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Producto getInfoProducto() {
        return infoProducto;
    }

    public void setInfoProducto(Producto infoProducto) {
        this.infoProducto = infoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Detalle {")
                .append("id='").append(id).append("', ")
                .append("infoProducto=").append(infoProducto).append(", ")
                .append("cantidad=").append(cantidad)
                .append("}");
        return sb.toString();
    }
}
