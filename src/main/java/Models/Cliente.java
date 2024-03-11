package Models;

public class Cliente implements Comparable<Cliente>{
    private Integer idcliente;
    private String nombre, estado;
    private Double credito, deuda;

    public Cliente() {
        this.idcliente = null;
        this.nombre = null;
        this.estado = null;
        this.credito = null;
        this.deuda = null;
    }

    public Cliente(int idcliente, String nombre, String estado, double credito, double deuda) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.estado = estado;
        this.credito = credito;
        this.deuda = deuda;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
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
                "idcliente=" + idcliente +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", credito=" + credito +
                ", deuda=" + deuda +
                '}';
    }

    @Override
    public int compareTo(Cliente other) {
        if(this.idcliente == null) return 1;
        return this.idcliente.compareTo(other.idcliente);
    }
}