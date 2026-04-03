package pos.presentation.historico;

import pos.logic.Factura;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Factura> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Factura> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropetyAt(Factura e, int col) {
        return null;
    }

    @Override
    protected void initColNames(){

    }

}