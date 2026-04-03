package pos.logic;

import pos.data.Data;
import pos.data.XmlPersister;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;

    public static Service instance(){
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }
    private Data data;

    private Service(){
        try{
            data= XmlPersister.instance().load();
        }
        catch(Exception e){
            data =  new Data();
        }
    }

    public void stop(){
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //Método para obtener el siguiente número de factura
    public synchronized String generarNumeroFactura() {
        try {
            updateFactura(data.getContadorF()+1);
            return String.format( "FAC-"+ "%05d", data.getContadorF());  // Formato con ceros a la izquierda si es necesario
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized String generarNumeroLinea() {
        try {
            updateLinea(data.getContadorL()+1);
            return String.format( "LIN-"+ "%05d", data.getContadorL());  // Formato con ceros a la izquierda si es necesario
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //================= CLIENTES ============

    public void create(Cliente e) throws Exception{
        Cliente result = data.getClientes().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result==null) data.getClientes().add(e);
        else throw new Exception("Cliente ya existe");
    }

    public Cliente read(Cliente e) throws Exception{
        Cliente result = data.getClientes().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Cliente no existe");
    }

    public void update(Cliente e) throws Exception{
        Cliente result;
        try{
            result = this.read(e);
            data.getClientes().remove(result);
            data.getClientes().add(e);
        }catch (Exception ex) {
            throw new Exception("Cliente no existe");
        }
    }

    public void delete(Cliente e) throws Exception{
        data.getClientes().remove(e);
    }

    public List<Cliente> search(Cliente e){
        return data.getClientes().stream()
                .filter(i->i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cliente::getNombre))
                .collect(Collectors.toList());
    }
    public List<Cliente> getClientes(){
        return data.getClientes();
    }

    //================= CAJEROS ============

    public void create(Cajero e) throws Exception{
        Cajero result = data.getCajeros().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result==null) data.getCajeros().add(e);
        else throw new Exception("Cajero ya existe");
    }

    public Cajero read(Cajero e) throws Exception{
        Cajero result = data.getCajeros().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Cajero no existe");
    }

    public void update(Cajero e) throws Exception{
        Cajero result;
        try{
            result = this.read(e);
            data.getCajeros().remove(result);
            data.getCajeros().add(e);
        }catch (Exception ex) {
            throw new Exception("Cajero no existe");
        }
    }

    public void delete(Cajero e) throws Exception{
        data.getCajeros().remove(e);
    }

    public List<Cajero> search(Cajero e){
        return data.getCajeros().stream()
                .filter(i->i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cajero::getNombre))
                .collect(Collectors.toList());
    }
    public List<Cajero> getCajeros(){
        return data.getCajeros();
    }

    //================PRODUCTOS================

    public void create(Producto e) throws Exception{
            Producto result = data.getProductos().stream().filter(i->i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result==null) data.getProductos().add(e);
        else throw new Exception("Producto ya existe");
    }

    public Producto read(Producto e) throws Exception{
        Producto result = data.getProductos().stream().filter(i->i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Producto no existe");
    }

    public void update(Producto e) throws Exception{
        Producto result;
        try{
            result = this.read(e);
            data.getProductos().remove(result);
            data.getProductos().add(e);
        }catch (Exception ex) {
            throw new Exception("Producto no existe");
        }
    }

    public void delete(Producto e) throws Exception{
        data.getProductos().remove(e);
    }

    public List<Producto> search(Producto e){
        return data.getProductos().stream()
                .filter(i->i.getCodigo().contains(e.getCodigo()))
                .sorted(Comparator.comparing(Producto::getCodigo))
                .collect(Collectors.toList());
    }
    public List<Producto> getProductos(){
        return data.getProductos();
    }

    //================CATEGORIA================

    public void create(Categoria e) throws Exception{
        Categoria result = data.getCategorias().stream().filter(i->i.getNombre().equals(e.getNombre())).findFirst().orElse(null);
        if (result==null) data.getCategorias().add(e);
        else throw new Exception("Categoria ya existe");
    }

    public Categoria read(Categoria e) throws Exception{
        Categoria result = data.getCategorias().stream().filter(i->i.getNombre().equals(e.getNombre())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Categoria no existe");
    }

    public List<Categoria> getCategorias(){
        return data.getCategorias();
    }

    //================FACTURA================

    public void create(Factura e) throws Exception{
        Factura result = data.getFacturas().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result==null) data.getFacturas().add(e);
        else throw new Exception("Factura ya existe");
    }

    public Factura read(Factura e) throws Exception{
        Factura result = data.getFacturas().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Factura no existe");
    }

    public void updateFactura(int cont) throws Exception{
        try{
            data.setContadorF(cont);
        }catch(Exception e){
            throw new Exception("Contador no existe");
        }
    }

    public void delete(Factura e) throws Exception{
        data.getFacturas().remove(e);
    }

    public List<Factura> search(Factura e){
        return data.getFacturas().stream()
                .filter(i->i.getId().contains(e.getId()))
                .sorted(Comparator.comparing(Factura::getId))
                .collect(Collectors.toList());
    }

    public List<Factura> getFacturas(){
        return data.getFacturas();
    }

    public void addFactura(Factura factura) {
        // Agregar la factura a la lista de facturas
        data.getFacturas().add(factura);
        // Guardar los cambios
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println("Error al guardar la factura: " + e.getMessage());
        }
    }

    //================LINEAS================


    public List<Linea> getLineas(){
        return data.getLineas();
    }
    public void create(Linea e) throws Exception {
        Linea result = data.getLineas().stream()
                .filter(i -> i.getCodigo().equals(e.getCodigo()))
                .findFirst().orElse(null);
        if (result == null) {
            data.getLineas().add(e);
        } else {
            throw new Exception("Linea ya existe");
        }
    }
    public Linea read(Linea e) throws Exception {
        Linea result = data.getLineas().stream().filter(i -> i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new Exception("Linea no existe");
        }
    }
    public List<Linea> search(Linea filter) {
        return data.getLineas().stream()
                .filter(l -> l.getProducto().getCodigo().contains(filter.getProducto().getCodigo()))
                .sorted(Comparator.comparing(Linea::getCodigo))
                .collect(Collectors.toList());
    }
    public void delete(Linea c) throws Exception {
        // Eliminar la Linea de la lista de Lineas
        data.getLineas().remove(c);
    }

    public void updateLinea(int cont) throws Exception {
        try{
            data.setContadorL(cont);
        }catch(Exception e){
            throw new Exception("Contador no existe");
        }
    }
}



