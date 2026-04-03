package pos.presentation.estadisticas;

import pos.logic.Factura;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Factura> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Factura> rows) {
        super(cols, rows);
    }

    public static final int CATEGORIA=0;
    public static final int FECHADESDE=1;
    public static final int FECHAMEDIA=2;
    public static final int FECHAHASTA=3;

    @Override
    protected Object getPropetyAt(Factura e, int col) {
        switch (cols[col]){
            /*
            Aqui me faltan atributos
            case CATEGORIA: return e.getCategoria();
             */
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[4];
        colNames[CATEGORIA]= "Categoria";
        colNames[FECHADESDE]= "FechaDesde";
        colNames[FECHAMEDIA]= "FechaMedia";
        colNames[FECHAHASTA]= "FechaHasta";
    }

}