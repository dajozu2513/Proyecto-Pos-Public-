package pos.presentation.facturas.view.productos;

import pos.logic.Producto;
import pos.presentation.productos.TableModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class BuscarProductosDialog extends JDialog {
    private JTextField searchField;
    private JTable table;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel panel;
    private List<Producto> productos;
    private Producto selectedProducto;
    private TableModel tableModelProducto;

    public BuscarProductosDialog(List<Producto> productos) {
        this.productos = productos;
        panel = new JPanel();
        //setTitle("Facturar - Buscar");
        searchField = new JTextField(20);
        table = new JTable();

        int[] cols = new int[] {
                TableModel.CODIGO,
                TableModel.DESCRIPCION,
                TableModel.UNIDADMEDIDA,
                TableModel.PRECIOUNITARIO,
                TableModel.EXISTENCIAS,
                TableModel.CATEGORIA
        };


        // Botones OK y Cancel
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancelar");
        panel.add(okButton);
        panel.add(cancelButton);

        // Panel de búsqueda
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel searchLabel = new JLabel("Descripción");
        panel.add(searchLabel);
        JScrollPane scrollPane = new JScrollPane(table); // Agregar la tabla en un JScrollPane
        panel.add(new JScrollPane(table));

        // Establecer el panel principal
        setContentPane(panel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);


        // Cargar productos en la tabla
        cargarProductosEnTabla(productos);


        // Listeners
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        panel.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                onBuscar();  // Llamar al método de búsqueda cuando se inserta texto
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                onBuscar();  // Llamar al método de búsqueda cuando se elimina texto
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                onBuscar();  // Llamar al método de búsqueda cuando cambia el texto
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el diálogo sin seleccionar nada
            }
        });

    }

    private void cargarProductosEnTabla(List<Producto> productos) {
        String[] columnNames = {"Código", "Descripción", "Unidad", "Precio", "Categoría"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Producto p : productos) {
            Object[] row = {p.getCodigo(), p.getDescripcion(), p.getUnidadMedida(), p.getPrecioUnitario(), p.getCategoria()};
            model.addRow(row);
        }
        table.setModel(model);
    }

    private void filtrarProductos() {
        String filterText = searchField.getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpiar la tabla
        for (Producto p : productos) {
            if (p.getDescripcion().toLowerCase().contains(filterText)) {
                Object[] row = {p.getCodigo(), p.getDescripcion(), p.getUnidadMedida(), p.getPrecioUnitario(), p.getCategoria()};
                model.addRow(row);
            }
        }
    }

    public Producto getSelectedProducto() {
        return selectedProducto;
    }

    // Acción al presionar Agregar
    private void onOK() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String codigo = table.getValueAt(selectedRow, 0).toString();
            for (Producto p : productos) {
                if (p.getCodigo().equals(codigo)) {
                    selectedProducto = p;
                    break;
                }
            }
            dispose();  // Cerrar el diálogo
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void onBuscar() {
        String codigoBuscar = searchField.getText().trim();  // Obtén el texto del campo de búsqueda
        List<Producto> productosFiltrados = productos.stream()
                .filter(p -> p.getDescripcion().contains(codigoBuscar))
                .collect(Collectors.toList());  // Filtra los productos por el código

        // Actualiza el modelo de la tabla con los productos filtrados
        tableModelProducto.setProductos(productosFiltrados);
    }
    // Acción al presionar Cancelar
    private void onCancel() {
        selectedProducto = null;
        dispose();  // Cerrar el diálogo
    }

    public Producto getProductoSeleccionado() {
        return selectedProducto;
    }
}