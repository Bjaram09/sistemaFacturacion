package Models;

public class Maestro {
    private String consecutivoDeFactura;
    private String cedulaJuridica;
    private Cliente infoCliente;

    public Maestro() {
        this.consecutivoDeFactura = "";
        this.cedulaJuridica = "";
        this.infoCliente = new Cliente();
    }

    public Maestro(String consecutivoDeFactura, String cedulaJuridica, Cliente infoCliente) {
        this.consecutivoDeFactura = consecutivoDeFactura;
        this.cedulaJuridica = cedulaJuridica;
        this.infoCliente = infoCliente;
    }

    public String getConsecutivoDeFactura() {
        return consecutivoDeFactura;
    }

    public void setConsecutivoDeFactura(String consecutivoDeFactura) {
        this.consecutivoDeFactura = consecutivoDeFactura;
    }

    public String getCedulaJuridica() {
        return cedulaJuridica;
    }

    public void setCedulaJuridica(String cedulaJuridica) {
        this.cedulaJuridica = cedulaJuridica;
    }

    public Cliente getInfoCliente() {
        return infoCliente;
    }

    public void setInfoCliente(Cliente infoCliente) {
        this.infoCliente = infoCliente;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Maestro {")
                .append("consecutivoDeFactura='").append(consecutivoDeFactura).append("', ")
                .append("cedulaJuridica='").append(cedulaJuridica).append("', ")
                .append("infoCliente=").append(infoCliente)
                .append("}");
        return sb.toString();
    }
}
