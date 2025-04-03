package Models;

public class Producto {
    private String id;
    private String descripcion;
    private int min;
    private int max;
    private double precioPorUnidad;

    public Producto() {
        this.id = "";
        this.descripcion = "";
        this.min = 0;
        this.max = 0;
        this.precioPorUnidad = 0.0;
    }

    public Producto(String id, String descripcion, int min, int max, double precioPorUnidad) {
        this.id = id;
        this.descripcion = descripcion;
        this.min = min;
        this.max = max;
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
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
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
                .append("min=").append(min).append(", ")
                .append("max=").append(max).append(", ")
                .append("precioPorUnidad=").append(precioPorUnidad)
                .append("}");
        return sb.toString();
    }
}
