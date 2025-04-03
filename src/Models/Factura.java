package Models;

import java.util.ArrayList;

public class Factura {
    private Maestro maestro;
    private ArrayList<Detalle> detalles;
    private double subtotal;
    private double IVA;
    private double precioFinal;

    public Factura() {
        this.maestro = new Maestro();
        this.detalles = new ArrayList<>();
        this.subtotal = 0;
        this.IVA = 0;
        this.precioFinal = 0;
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

    public void setIVA(double IVA) {
        this.IVA = IVA;
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
    
    public Factura(Maestro maestro, ArrayList<Detalle> detalles, double subtotal, double IVA, double precioFinal) {
        this.maestro = maestro;
        this.detalles = detalles;
        this.subtotal = subtotal;
        this.IVA = IVA;
        this.precioFinal = precioFinal;
    }
    
    
}
