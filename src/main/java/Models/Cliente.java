package Models;

public class Cliente {
    private Integer idCliente;
    private String nombre, estado;
    private Double credito, deuda;

    public Cliente() {
        this.idCliente = null;
        this.nombre = null;
        this.estado = null;
        this.credito = null;
        this.deuda = null;
    }

    public Cliente(int idCliente, String nombre, String estado, double credito, double deuda) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.estado = estado;
        this.credito = credito;
        this.deuda = deuda;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    public Double getDeuda() {
        return deuda;
    }

    public void setDeuda(Double deuda) {
        this.deuda = deuda;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", credito=" + credito +
                ", deuda=" + deuda +
                '}';
    }
}
