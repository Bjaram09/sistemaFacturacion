package Models;

public class Producto {
    private String id;
    private String descripcion;
    private int minCantidad;
    private int maxCantidad;
    private double precioPorUnidad;

    public Producto() {
        this.id = "";
        this.descripcion = "";
        this.minCantidad = 0;
        this.maxCantidad = 0;
        this.precioPorUnidad = 0.0;
    }

    public Producto(String id, String descripcion, int minCantidad, int maxCantidad, double precioPorUnidad) {
        this.id = id;
        this.descripcion = descripcion;
        this.minCantidad = minCantidad;
        this.maxCantidad = maxCantidad;
        this.precioPorUnidad = precioPorUnidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMin() {
        return minCantidad;
    }

    public void setMin(int min) {
        this.minCantidad = min;
    }

    public int getMax() {
        return maxCantidad;
    }

    public void setMax(int max) {
        this.maxCantidad = max;
    }

    public double getPrecioPorUnidad() {
        return precioPorUnidad;
    }

    public void setPrecioPorUnidad(double precioPorUnidad) {
        this.precioPorUnidad = precioPorUnidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Producto {")
                .append("id='").append(id).append("', ")
                .append("descripcion='").append(descripcion).append("', ")
                .append("min=").append(minCantidad).append(", ")
                .append("max=").append(maxCantidad).append(", ")
                .append("precioPorUnidad=").append(precioPorUnidad)
                .append("}");
        return sb.toString();
    }
}
