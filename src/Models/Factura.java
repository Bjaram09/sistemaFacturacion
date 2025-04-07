package Models;

import java.util.ArrayList;

public class Factura {
    private String id;
    private Maestro maestro;
    private ArrayList<Detalle> detalles;
    private double subtotal;
    private final double IVA = 0.13;
    private double precioFinal;

    public Factura() {
        this.id = "";
        this.maestro = new Maestro();
        this.detalles = new ArrayList<>();
        this.subtotal = 0;
        this.precioFinal = 0;
    }

    public Factura(String id, Maestro maestro, ArrayList<Detalle> detalles, double subtotal) {
        this.id = id;
        this.maestro = maestro;
        this.detalles = detalles;
        this.subtotal = subtotal;
        this.precioFinal = this.subtotal + (this.subtotal * this.IVA);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Maestro getMaestro() {
        return maestro;
    }

    public void setMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    public ArrayList<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<Detalle> detalles) {
        this.detalles = detalles;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIVA() {
        return IVA;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Factura {")
                .append("maestro=").append(maestro).append(", ")
                .append("detalles=").append(detalles).append(", ")
                .append("subtotal=").append(subtotal).append(", ")
                .append("IVA=").append(IVA).append(", ")
                .append("precioFinal=").append(precioFinal)
                .append("}");
        return sb.toString();
    }
}
