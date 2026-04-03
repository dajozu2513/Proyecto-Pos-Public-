package pos.presentation.facturas;

import pos.Application;
import pos.logic.*;
import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Producto filter;
    Factura current;
    int mode;
    List<Cliente> clientes;
    List<Cajero> cajeros;
    List<Linea> lineas;
    List<Producto> productos;

    //OJO A ESTO, VERLO DESPUES
    Cliente currentCliente;  // Cliente actual
    Cajero currentCajero;


    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LINEAS);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);

        firePropertyChange(CAJEROS);
        firePropertyChange(CLIENTES);
        firePropertyChange(PRODUCTOS);

        firePropertyChange(CURRENTCLIENTE);
        firePropertyChange(CURRENTCAJERO);

    }

    public Model() {}

    public void init(List<Linea> lineas, List<Cliente> clientes, List<Cajero> cajeros,List<Producto> productos){
        this.lineas = lineas;
        this.current = new Factura();
        this.filter = new Producto();

        this.productos = productos;
        this.clientes = clientes;
        this.cajeros = cajeros;

        this.mode= Application.MODE_CREATE;
    }

    //GETTERS
    public List<Linea> getLineas() {
        return lineas;
    }

    public List<Cajero> getCajeros() {
        return cajeros;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public Factura getCurrent() {
        if(current == null){
            current = new Factura();
        }
        return current;
    }
    public Producto getFilter() {
        return filter;
    }
    public int getMode() {
        return mode;
    }

    public Cliente getCurrentCliente() {
        return currentCliente;
    }

    public Cajero getCurrentCajero() {
        return currentCajero;
    }


    //SETTERS

    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
        firePropertyChange(LINEAS);
    }
    public void setCajeros(List<Cajero> cajeros) {
        this.cajeros = cajeros;
        firePropertyChange(CAJEROS);
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        firePropertyChange(CLIENTES);
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        firePropertyChange(PRODUCTOS);
    }

    public void setCurrent(Factura current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }
    public void setFilter(Producto filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    //Metodos de botones
    public void addLinea(Linea linea) {
        if (this.lineas == null) {
            this.lineas = new ArrayList<>();
        }
        this.lineas.add(linea);
        firePropertyChange(LINEAS);
    }
    public void setCantidad(int index, int cantidad) {
        if (lineas != null && index >= 0 && index < lineas.size()) {
            Linea linea = lineas.get(index);
            linea.setCantidad(cantidad);
            firePropertyChange(LINEAS);
        }
    }

    public void setDescuento(int index, float desc){
        if(lineas != null && index >= 0 && index < lineas.size()){
            Linea linea = lineas.get(index);
            linea.setDescuento(desc);
            firePropertyChange(LINEAS);

        }
    }

    public void deleteLinea(int fila){
        this.lineas.remove(fila);
        firePropertyChange(LINEAS);
    }
    public void cancelLineas(List<Linea> linea){
        this.lineas.removeAll(linea);
        firePropertyChange(LINEAS);
    }

    //Metodos de TOTALES

    public int getCantArticulos() {
        return lineas.stream().mapToInt(Linea::getCantidad).sum();
    }

    public double getSubtotal() {
        return lineas.stream().mapToDouble(linea -> linea.getProducto().getPrecioUnitario() * linea.getCantidad()).sum();
    }

    public double getDescuentoTotal() {
        return lineas.stream().mapToDouble(Linea::getDescuento).sum();
    }

    public double getTotal() {
        return getSubtotal() - getDescuentoTotal() *100;
    }


    public static final String LINEAS = "lineas";
    public static final String CURRENT="current";
    public static final String FILTER="filter";
    public static final String CAJEROS = "cajeros";
    public static final String CLIENTES = "clientes";
    public static final String PRODUCTOS = "productos";
    public static final String CURRENTCLIENTE = "currentclientes";
    public static final String CURRENTCAJERO = "currentcajeros";

}
