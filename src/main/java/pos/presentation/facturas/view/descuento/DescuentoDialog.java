package pos.presentation.facturas.view.descuento;

import pos.logic.Linea;
import pos.presentation.facturas.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DescuentoDialog extends JDialog {
    private JLabel Descuento;
    private Linea linea;
    private Controller controller;
    private JTextField textField;
    private JButton OKbutton;
    private JButton cancelButton;
    private JPanel panel;

    public DescuentoDialog(Linea linea, Controller controller) {
        this.linea = linea;
        this.controller = controller;
        panel.setPreferredSize(new Dimension(300, 150));
        setContentPane(panel);
        setModal(true);
        getRootPane().setDefaultButton(OKbutton);

        textField.setText(String.valueOf(linea.getDescuento()));

        OKbutton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        panel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            float desc = Float.parseFloat(textField.getText());
            if (desc >= 0 && desc <= 9) {
                linea.setDescuento(desc);
                if (controller != null) {
                    controller.setDescuentoLinea(linea);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "El descuento debe estar entre 0 y 9", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Descuento inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}