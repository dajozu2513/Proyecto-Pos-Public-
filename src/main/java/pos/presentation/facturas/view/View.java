package pos.presentation.facturas.view;

import pos.logic.Cajero;
import pos.logic.Cliente;
import pos.logic.Producto;
import pos.presentation.facturas.Controller;
import pos.presentation.facturas.Model;
import pos.presentation.historico.LineaTableModel;
import pos.presentation.facturas.view.productos.BuscarProductosDialog;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JLabel searchNombreLbl;
    private JTextField searchNombre;
    private JButton add;
    private JButton search;
    private JButton amount;
    private JButton delete;
    private JButton discount;
    private JButton pay;
    private JTable list;
    private JComboBox clientes;
    private JComboBox cajeros;
    private JButton cancel;
    private JScrollPane ScrollProductos;
    private JLabel Articulos;
    private JLabel totalArticulos;
    private JLabel Subtotal;
    private JLabel totalSubtotal;
    private JLabel Descuento;
    private JLabel totalDescuento;
    private JLabel Total;
    private JLabel totalTotal;
    private Producto selectedProduct;

    public JPanel getPanel() {
        return panel;
    }

    public JTable getListaJT() {
        return list;
    }

    public JComboBox<Cliente> getClienteView() {  // Añadido
        return clientes;
    }

    public JComboBox<Cajero> getCajeroView() {  // Añadido
        return cajeros;
    }

    private Model model;
    private Controller controller;

    public View() {

        clientes.setPreferredSize(new Dimension(200, 30));
        cajeros.setPreferredSize(new Dimension(200, 30));
        // Ajustar el tamaño de la JTable
        list.setPreferredSize(new Dimension(800, 300));  // Ajusta las dimensiones según tus necesidades
        //===========BUSCAR UN PRODUCTO===========
        search.addActionListener(e -> {
            if (controller != null) {
                controller.BuscarProducto();
            }
        });
        //===========AGREGAR UN PRODUCTO===========
        add.addActionListener(e -> {
            String codigo = searchNombreLbl.getText();
            if (controller != null) {
                controller.save(codigo, (Cliente) clientes.getSelectedItem());
            }
        });

        //===========CANTIDAD DE UN PRODUCTO===========
        amount.addActionListener(e -> {
            if (controller != null) {
                controller.setCantidad();
            }
        });

        discount.addActionListener(e -> {
            if (controller != null) {
                controller.ActivarViewDescuento();
            }
        });

        //aqui se continua con los demas botones cancell y quit
        delete.addActionListener(e -> {
            int row = list.getSelectedRow();
            if (controller != null) {
                controller.delete(row);
            }
        });

        cancel.addActionListener(e -> {
            if (controller != null) {
                controller.clear();
            }
        });

        // el de pay genera una instancia de la clase faturar cobrar que llama el jdialog de cobros. igual que en el metodo de arriba que se llema search
        pay.addActionListener(e -> {
            if (controller != null) {
                controller.Cobrar();
            }
        });


    }

    public void setModel(pos.presentation.facturas.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this::propertyChange);
    }

    public void setController(pos.presentation.facturas.Controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CLIENTES:
                this.clientes.setModel(new DefaultComboBoxModel((Object[]) model.getClientes().toArray()));
                break;
            case Model.CAJEROS:
                this.cajeros.setModel(new DefaultComboBoxModel(this.model.getCajeros().toArray()));
                break;
            case Model.LINEAS:
                int[] cols = {LineaTableModel.CODIGO, LineaTableModel.ARTICULO, LineaTableModel.CATEGORIA,
                        LineaTableModel.CANTIDAD, LineaTableModel.PRECIO, LineaTableModel.DESCUENTO,
                        LineaTableModel.NETO, LineaTableModel.IMPORTE};

                list.setModel(new LineaTableModel(cols, model.getLineas()) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;  // Deshabilitar la edición de celdas
                    }
                });
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(180);
                columnModel.getColumn(1).setPreferredWidth(180);
                columnModel.getColumn(2).setPreferredWidth(180);
                columnModel.getColumn(3).setPreferredWidth(180);
                columnModel.getColumn(4).setPreferredWidth(180);
                columnModel.getColumn(5).setPreferredWidth(180);
                columnModel.getColumn(6).setPreferredWidth(180);
                columnModel.getColumn(7).setPreferredWidth(180);
                //listaJT.setRowHeight(50); // Ajustar la altura de las filas

                // Ajuste del ancho de las columnas basado en el contenido
                //ajustarAnchoDeColumnas(listaJT);
                updateLabels();
                break;
        }
    }

    // Método para ajustar el ancho de las columnas
    private void ajustarAnchoDeColumnas(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 450; // Ancho mínimo
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 500) {
                width = 500;  // Ancho máximo
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    private void updateLabels() {
        Articulos.setText("" + model.getCantArticulos());
        Subtotal.setText("" + String.format("%.2f", model.getSubtotal()));
        Descuento.setText("" + String.format("%.2f", model.getDescuentoTotal()));
        totalTotal.setText("" + String.format("%.2f", model.getTotal()));
    }
}