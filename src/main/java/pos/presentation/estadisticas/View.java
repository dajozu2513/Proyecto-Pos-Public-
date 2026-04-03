package pos.presentation.estadisticas;

import pos.logic.Categoria;
import pos.presentation.facturas.Model;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class View {
    private JPanel panel;
    private JComboBox anoDesde;
    private JComboBox mesDesde;
    private JComboBox anoHasta;
    private JComboBox mesHasta;
    private JComboBox categoria;
    private JButton addOne;
    private JButton addAll;
    private JTable list;
    private JButton deleteOne;
    private JButton deleteAll;

    public JPanel getPanel() {
        return panel;
    }

    // MVC
    pos.presentation.estadisticas.Model model;
    pos.presentation.estadisticas.Controller controller;

    public void setModel(pos.presentation.estadisticas.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this::propertyChange);
    }

    public void setController(pos.presentation.estadisticas.Controller controller) {
        this.controller = controller;
    }

    //@Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case pos.presentation.estadisticas.Model.LIST:

                break;
            case pos.presentation.estadisticas.Model.CURRENT:

                break;
            case pos.presentation.estadisticas.Model.CATEGORIAS:
                categoria.setModel(new DefaultComboBoxModel<>(model.getCategorias().toArray(new Categoria[0])));
                break;
            case Model.FILTER:

                break;
        }

        this.panel.revalidate();
    }

}
