package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)

public class Linea {
    @XmlIDREF
    Producto producto;
    Factura factura;
    @XmlID
    String codigo;
    int cantidad;
    float descuento;



    public Linea () {this(null,"", 0, 0.0f);}

    public Linea (Producto producto,String codigo,  int cantidad, float descuento) {
        this.codigo = codigo;
        this.producto = new Producto();
        this.factura = new Factura();
        this.cantidad = cantidad;
        this.descuento = descuento;
    }
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Factura getFactura() { return factura; }

    public void setFactura(Factura Factura) { this.factura = Factura; }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Linea linea = (Linea) o;
        return Objects.equals(producto.getCodigo(), linea.getProducto().getCodigo());
    }

    public double Neto(){
        return  producto.getPrecioUnitario() ;
    }
    public double Importe(){
        return (Neto() * cantidad) - descuento;
    }

    @Override
    public int hashCode() {return Objects.hash(codigo);}

    @Override
    public String toString() {
        return getProducto().toString();
    }

}
