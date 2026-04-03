package pos.presentation.productos;

import pos.logic.Producto;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Producto> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Producto> rows) {
        super(cols, rows);
    }

    public static final int CODIGO=0;
    public static final int DESCRIPCION=1;
    public static final int UNIDADMEDIDA=2;
    public static final int PRECIOUNITARIO=3;
    public static final int CATEGORIA=4;
    public static final int EXISTENCIAS = 5;

    @Override
    protected Object getPropetyAt(Producto e, int col) {
        switch (cols[col]){
            case CODIGO: return e.getCodigo();
            case DESCRIPCION: return e.getDescripcion();
            case UNIDADMEDIDA: return e.getUnidadMedida();
            case PRECIOUNITARIO: return e.getPrecioUnitario();
            case CATEGORIA: return e.getCategoria();
            case EXISTENCIAS: return e.getExistencias();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[6];
        colNames[CODIGO]= "Codigo";
        colNames[DESCRIPCION]= "Descripcion";
        colNames[UNIDADMEDIDA]= "UnidadMedida";
        colNames[PRECIOUNITARIO]= "PrecioUnitario";
        colNames[CATEGORIA]= "Categoria";
        colNames[EXISTENCIAS] = "Existencias";
    }

    public void setProductos(List<Producto> productos) {
        this.rows = productos;
        fireTableDataChanged();  // Notifica a la tabla que los datos han cambiado
    }

}

