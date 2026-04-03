package pos.presentation.historico;

import pos.Application;
import pos.logic.Factura;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener{

    public JPanel getPanel() {
        return panel;
    }

    public View() {

    }

    // MVC
    pos.presentation.historico.Model model;
    pos.presentation.historico.Controller controller;
    private JPanel panel;
    private JPanel Historico;
    private JLabel Cliente;
    private JTextField textFieldCliente;
    private JButton buscarButton;
    private JButton reporteButton;
    private JScrollPane Listado;

    public void setModel(pos.presentation.historico.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(pos.presentation.historico.Controller controller) {

        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case pos.presentation.clientes.Model.LIST:

                break;
            case pos.presentation.clientes.Model.CURRENT:

                break;
            case Model.FILTER:

                break;
        }

        this.panel.revalidate();
    }
}
