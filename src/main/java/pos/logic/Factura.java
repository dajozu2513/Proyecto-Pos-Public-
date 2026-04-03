package pos.logic;

import jakarta.xml.bind.annotation.*;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

import java.util.Objects;
import java.time.LocalDate;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Factura {
    @XmlID
    String id;
    LocalDate fecha;
    double total;
    @XmlIDREF
    Cliente cliente;
    @XmlIDREF
    Cajero cajero;

   @XmlIDREF
   @XmlElementWrapper(name = "lineas")
   @XmlElement(name = "linea")
   List<Linea> lineas;

    public Factura() {}

    public Factura(String id, Cliente cliente, Cajero cajero, List<Linea> lineas, double total, LocalDate fecha) {
        this.id = id;
        this.cliente = cliente;
        this.cajero = cajero;
        this.lineas = lineas;
        this.total = total;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return Objects.equals(id, factura.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}

