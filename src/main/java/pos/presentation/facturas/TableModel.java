/*package pos.presentation.facturas;

import pos.logic.Cliente;
import pos.logic.Linea;
import pos.logic.Producto;
import pos.presentation.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;

public class TableModel extends AbstractTableModel<Linea> implements javax.swing.table.TableModel {

    public static final int CODIGO = 0;
    public static final int DESCRIPCION = 1;
    public static final int PRECIO = 2;
    public static final int CANTIDAD = 3;
    public static final int UNIDAD = 4;
    public static final int DESCUENTO = 5;
    public static final int NETO = 6;
    public static final int IMPORTE = 7;


    public TableModel(int[] cols, List<Linea> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropetyAt(Linea l, int col) {
        switch (cols[col]) {
            case CODIGO:
                return l.getProducto().getCodigo();
            case DESCRIPCION:
                return l.getProducto().getDescripcion();
            case PRECIO:
                return l.getProducto().getPrecioUnitario();
            case CANTIDAD:
                return l.getCantidad();
            case UNIDAD:
                return l.getProducto().getUnidadMedida();
            case DESCUENTO:
                return l.getDescuento();
            case NETO:
                return l.Neto();
            case IMPORTE:
                return l.Importe();

            default:
                return "";
        }
    }

    @Override
    protected void initColNames() {
        colNames = new String[8];
        colNames[CODIGO] = "Código";
        colNames[DESCRIPCION] = "Descripción";
        colNames[PRECIO] = "Precio";
        colNames[CANTIDAD] = "Cantidad";
        colNames[UNIDAD] = "Unidad";
        colNames[DESCUENTO] = "Descuento";
        colNames[NETO] = "Neto";
        colNames[IMPORTE] = "Importe";
    }

    public void addRow(Object[] rowData) {
        Producto producto = new Producto();
        Cliente cliente = new Cliente();
        producto.setCodigo((String) rowData[CODIGO]);
        producto.setDescripcion((String) rowData[DESCRIPCION]);
        producto.setPrecioUnitario((Float) rowData[PRECIO]);
        producto.setUnidadMedida((String) rowData[UNIDAD]);
        cliente.setDescuento((Float) rowData[DESCUENTO]);
        Linea linea = new Linea();
        linea.setProducto(producto);
        linea.setCantidad((Integer) rowData[CANTIDAD]);
        linea.setDescuento((Float) rowData[DESCUENTO]);

        rows.add(linea);

        int lastRowIndex = rows.size() - 1;
        fireTableRowsInserted(lastRowIndex, lastRowIndex);
    }
}

//    // Este método es para actualizar las filas
//    public void setRows(List<Linea> rows) {
//        this.rows = (rows == null) ? new ArrayList<>() : rows; // Evitar que 'rows' sea null
//        fireTableDataChanged(); // Notificar a la tabla que los datos han cambiado
//    }
//
//    @Override
//    protected Object getPropetyAt(Linea e, int col) {
//        switch (cols[col]) {
//            case CODIGO:
//                return e.getProducto().getCodigo(); // Cambié de e.getId() a obtener el código del producto
//            case ARTICULO:
//                return e.getProducto().getDescripcion();
//            case CATEGORIA:
//                return e.getProducto().getCategoria();
//            case CANTIDAD:
//                return e.getCantidad();
//            case PRECIO:
//                return e.getProducto().getPrecioUnitario();
//            case DESCUENTO:
//                return e.getDescuento();
//            case NETO:
//                return e.getProducto().getPrecioUnitario() - e.getDescuento();
//            case IMPORTE:
//                return (e.getProducto().getPrecioUnitario() * (1 - (e.getDescuento() / 100))) * e.getCantidad();
//            default:
//                return "";
//        }
//    }
//
//    @Override
//    protected void initColNames() {
//        colNames = new String[8];
//        colNames[CODIGO] = "Codigo";
//        colNames[ARTICULO] = "Articulo";
//        colNames[CATEGORIA] = "Categoria";
//        colNames[CANTIDAD] = "Cantidad";
//        colNames[PRECIO] = "Precio";
//        colNames[DESCUENTO] = "Descuento";
//        colNames[NETO] = "Neto";
//        colNames[IMPORTE] = "Importe";
//    }
//
//    // Implementación del método para obtener una línea en una fila específica
//    public Linea getLineaAt(int rowIndex) {
//        if (rowIndex >= 0 && rowIndex < rows.size()) {
//            return rows.get(rowIndex); // Devuelve la línea si el índice es válido
//        }
//        return null; // Devuelve null si el índice no es válido
//    }
//    public void addRow(Object[] rowData) {
//        Producto producto = new Producto();
//        Cliente cliente = new Cliente();
//       // Categoria categoria = new Categoria();
//        producto.setCodigo((String) rowData[CODIGO]);
//        producto.setDescripcion((String) rowData[ARTICULO]);
//        producto.setPrecioUnitario((Float) rowData[PRECIO]);
//       // producto.setCategoria((categoria) rowData[CATEGORIA]);
//        cliente.setDescuento((int) rowData[DESCUENTO]);
//        Linea linea = new Linea();
//        linea.setProducto(producto);
//        linea.setCantidad((Integer) rowData[CANTIDAD]);
//        linea.setDescuento((int) rowData[DESCUENTO]);
//
//        rows.add(linea);
//
//        int lastRowIndex = rows.size() - 1;
//        fireTableRowsInserted(lastRowIndex, lastRowIndex);
//    }
*/