package pos.presentation.productos;

import pos.Application;
import pos.logic.Categoria;
import pos.logic.Producto;
import pos.data.Data;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JLabel codigoLbl;
    private JTextField codigo;
    private JLabel descripcionLbl;
    private JTextField descripcion;
    private JLabel unidadMedidaLbl;
    private JTextField unidadMedida;
    private JLabel precioUnitarioLbl;
    private JTextField precioUnitario;
    private JLabel categoriaLbl;

    private JButton save;
    private JButton delete;
    private JButton clear;
    private JTable list;
    private JLabel searchNombreLbl;
    private JTextField searchNombre;
    private JButton report;
    private JButton search;
    private JComboBox categoria;
    private JTextField existencias;
    private JLabel existenciasLbl;
    private Data data;

    public JPanel getPanel() {
        return panel;
    }

    public View() {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Producto filter = new Producto();
                    filter.setDescripcion(searchNombre.getText());
                    controller.search(filter); //error en esta linea
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Producto p = take(); //error en esta linea
                    try {
                        controller.save(p); //error en esta linea
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                controller.edit(row);
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.delete();
                    JOptionPane.showMessageDialog(panel, "REGISTRO BORRADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

        categoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //categoria.addItem(Model.CATEGORIAS);
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        if (codigo.getText().isEmpty()) {
            valid = false;
            codigoLbl.setBorder(Application.BORDER_ERROR);
            codigoLbl.setToolTipText("Codigo requerido");
        } else {
            codigoLbl.setBorder(null);
            codigoLbl.setToolTipText(null);
        }

        if (descripcion.getText().isEmpty()) {
            valid = false;
            descripcionLbl.setBorder(Application.BORDER_ERROR);
            descripcionLbl.setToolTipText("Descripción requerida");
        } else {
            descripcionLbl.setBorder(null);
            descripcionLbl.setToolTipText(null);
        }

        if (unidadMedida.getText().isEmpty()) {
            valid = false;
            unidadMedidaLbl.setBorder(Application.BORDER_ERROR);
            unidadMedidaLbl.setToolTipText("Unidad requerida");
        } else {
            unidadMedidaLbl.setBorder(null);
            unidadMedidaLbl.setToolTipText(null);
        }

        try {
            Float.parseFloat(precioUnitario.getText());
            precioUnitarioLbl.setBorder(null);
            precioUnitarioLbl.setToolTipText(null);
        } catch (Exception e) {
            valid = false;
            precioUnitarioLbl.setBorder(Application.BORDER_ERROR);
            precioUnitarioLbl.setToolTipText("Precio invalido");
        }

        if (categoria.getSelectedItem() == null) {
            valid = false;
            categoriaLbl.setBorder(Application.BORDER_ERROR);
            categoriaLbl.setToolTipText("Categoría requerida");
        } else {
            categoriaLbl.setBorder(null);
            categoriaLbl.setToolTipText(null);
        }

        try {
            Integer.parseInt(existencias.getText());
            existenciasLbl.setBorder(null);
            existenciasLbl.setToolTipText(null);
        } catch (Exception e) {
            valid = false;
            existenciasLbl.setBorder(Application.BORDER_ERROR);
            existenciasLbl.setToolTipText("Existencias inválidas");
        }

        return valid;
    }

    public Producto take() {
        Producto e = new Producto();
        e.setCodigo(codigo.getText());
        e.setDescripcion(descripcion.getText());
        e.setUnidadMedida(unidadMedida.getText());
        e.setPrecioUnitario(Float.parseFloat(precioUnitario.getText()));
        e.setCategoria((Categoria) categoria.getSelectedItem());
        e.setExistencias(Integer.parseInt(existencias.getText()));
        return e;
    }

    // MVC
    pos.presentation.productos.Model model;
    pos.presentation.productos.Controller controller;

    public void setModel(pos.presentation.productos.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this::propertyChange);//error en esta lista
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    //@Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.CODIGO, TableModel.DESCRIPCION, TableModel.UNIDADMEDIDA, TableModel.PRECIOUNITARIO, TableModel.CATEGORIA, TableModel.EXISTENCIAS};
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
            case Model.CURRENT:
                codigo.setText(model.getCurrent().getCodigo());
                descripcion.setText(model.getCurrent().getDescripcion());
                unidadMedida.setText(model.getCurrent().getUnidadMedida());
                precioUnitario.setText("" + model.getCurrent().getPrecioUnitario());
                categoria.setSelectedItem(model.getCurrent().getCategoria());
                existencias.setText("" + model.getCurrent().getExistencias());

                if (model.getMode() == Application.MODE_EDIT) {
                    codigo.setEnabled(false);
                    delete.setEnabled(true);
                } else {
                    codigo.setEnabled(true);
                    delete.setEnabled(false);
                }

                codigoLbl.setBorder(null);
                codigoLbl.setToolTipText(null);
                descripcionLbl.setBorder(null);
                descripcionLbl.setToolTipText(null);
                unidadMedidaLbl.setBorder(null);
                unidadMedida.setToolTipText(null);
                precioUnitario.setBorder(null);
                precioUnitario.setToolTipText(null);
                existenciasLbl.setBorder(null);
                existenciasLbl.setToolTipText(null);
                break;
            case Model.CATEGORIAS:
                categoria.setModel(new DefaultComboBoxModel<>(model.getCategorias().toArray(new Categoria[0])));

                break;
            case Model.FILTER:
                searchNombre.setText(model.getFilter().getDescripcion());
                break;
        }

        this.panel.revalidate();
    }
}
