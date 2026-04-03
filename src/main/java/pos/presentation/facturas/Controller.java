package pos.presentation.facturas;
import pos.Application;

import pos.logic.*;
import pos.presentation.facturas.view.View;
import pos.presentation.facturas.view.cantidad.CantidadDialog;
import pos.presentation.facturas.view.descuento.DescuentoDialog;
import pos.presentation.facturas.view.pago.FacturarCobrarDialog;
import pos.presentation.facturas.view.productos.BuscarProductosDialog;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    View view;
    Model model;
    //pos.presentation.productos.Model modelProductos;
   // pos.presentation.productos.Controller controllerProductos;


    public Controller(View view, Model model) {
        //model.init(Service.instance().search(new Factura()));
        try {
            List<Linea> lineas = new ArrayList<>();
            List<Producto> productos = Service.instance().search(new Producto());
            List<Cliente> clientes = Service.instance().search(new Cliente());
            List<Cajero> cajeros = Service.instance().search(new Cajero());
            model.init(lineas, clientes, cajeros,productos);
            this.view = view;
            this.model = model;
            view.setController(this);
            view.setModel(model);
        } catch (Exception var5) {
            Exception e = var5;
            e.printStackTrace();
        }

        //view.setModelProducto(modelProductos);
        //view.setControllerProducto(controllerProductos);
    }

    public void search(Producto filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Factura());
        //model.setLineas(Service.instance().search(model.getFilter()));
    }

    public void save(String codigo, Cliente c) { //para el boton agregar
        try {
            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(view.getPanel(), "Código del producto no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto filter = new Producto();
            filter.setCodigo(codigo);
            List<Producto> productosEncontrados = Service.instance().search(filter);

            if (productosEncontrados.isEmpty()) {
                JOptionPane.showMessageDialog(view.getPanel(), "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto productoEncontrado = productosEncontrados.get(0);
            Linea nuevaLinea = new Linea(productoEncontrado,null,1,c.getDescuento());
            nuevaLinea.setCodigo(Service.instance().generarNumeroLinea());
            model.addLinea(nuevaLinea);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    public void delete(int fila)  { //boton quitar
        try {
            if (model.getLineas() == null) {
                JOptionPane.showMessageDialog(view.getPanel(), "No hay Elementos en la Lista de Compra", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.deleteLinea(fila);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void clear() { //boton cancelar
        try {
            if (model.getLineas() == null) {
                JOptionPane.showMessageDialog(view.getPanel(), "No hay Elementos en la Lista de Compra", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.cancelLineas(model.getLineas());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setCantidad() {
        int row = view.getListaJT().getSelectedRow();
        if (row >= 0) {
            Linea lineaSeleccionada = model.getLineas().get(row);
            CantidadDialog dialog = new CantidadDialog(lineaSeleccionada,this); //view cantidad constructor
            dialog.pack();  // Ajusta el tamaño de la ventana según el contenido
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view.getPanel(), "No se ha seleccionado ningún producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void ActivarViewDescuento(){
        int row = view.getListaJT().getSelectedRow();
        if(row >= 0){
            Linea lineaSeleccionada = model.getLineas().get(row);
            DescuentoDialog dialog = new DescuentoDialog(lineaSeleccionada, this);
            dialog.pack();
            dialog.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(view.getPanel(), "No se ha seleccionado ningún producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setDescuentoLinea(Linea lineaModificada){
        int index = model.getLineas().indexOf(lineaModificada);
        if (index >= 0) {
            model.setDescuento(index, lineaModificada.getDescuento());
        }
    }
    public void refreshLinea(int number, int index) {
        List<Linea> lineas = model.getLineas();
        lineas.get(index).setCantidad(number);
    }

    public void Cobrar() {
        if (model.getLineas().isEmpty()) {
            JOptionPane.showMessageDialog(view.getPanel(), "No hay productos en la factura.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalFactura = model.getTotal();
        FacturarCobrarDialog dialog = new FacturarCobrarDialog(totalFactura);
        dialog.pack();
        dialog.setVisible(true);
        if (!dialog.isPagoExitoso()) {
            JOptionPane.showMessageDialog(view.getPanel(), "El pago no se completó. No se ha creado la factura.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Salir si el pago no fue exitoso
        }
        Factura factura = model.getCurrent();
        factura.setId(Service.instance().generarNumeroFactura()); // Asignar número secuencial
        factura.setFecha(LocalDate.now());
        factura.setLineas(model.getLineas());
        factura.setCliente((Cliente) view.getClienteView().getSelectedItem());
        factura.setCajero((Cajero) view.getCajeroView().getSelectedItem());
        try {
            // Actualizar inventario
            for (Linea linea : model.getLineas()) {
                Producto producto = linea.getProducto();
                int cantidadComprada = linea.getCantidad();
                int nuevaCantidad = producto.getExistencias() - cantidadComprada;

                if (nuevaCantidad < 0) {
                    JOptionPane.showMessageDialog(view.getPanel(), "No hay suficiente stock para el producto: " + producto.getDescripcion(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Detener si no hay suficiente stock
                }

                // Actualizar las existencias del producto
                producto.setExistencias(nuevaCantidad);
                Service.instance().update(producto);  // Guardar el cambio en la base de datos
            }

            // Guardar la factura
            for (Linea linea : model.getLineas()) {
                linea.setFactura(factura);
                Service.instance().create(linea);  // Guardar la línea de la factura
            }

            Service.instance().create(factura);  // Guardar la factura completa
            model.setProductos(new ArrayList<>());
            model.setCurrent(new Factura());
            JOptionPane.showMessageDialog(view.getPanel(), "Factura guardada exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void CambiarCantidadLinea(Linea lineaModificada) {
        int index = model.getLineas().indexOf(lineaModificada);
        if (index >= 0) {
            model.setCantidad(index, lineaModificada.getCantidad());
        }
    }

    public void BuscarProducto() {
        try {
            List<Producto> productosT = Service.instance().search(new Producto());
            BuscarProductosDialog dialog = new BuscarProductosDialog(productosT); // Pasamos la lista y el controlador al diálogo
            dialog.pack();  // Ajusta el tamaño de la ventana según el contenido
            dialog.setVisible(true);
            Producto productoSeleccionado = dialog.getProductoSeleccionado();
            if (productoSeleccionado != null) {
                // Llama al método para agregar el producto a la factura
                save(productoSeleccionado.getCodigo(), (Cliente) view.getClienteView().getSelectedItem());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void shown() {
        model.setCajeros(Service.instance().search(new Cajero()));
        model.setProductos(Service.instance().search(new Producto()));
        model.setClientes(Service.instance().search(new Cliente()));
    }
}




