package pos.presentation.facturas.view.pago;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FacturarCobrarDialog extends JDialog {
    private JPanel panel;
    private JTextField efectivo, tarjeta, cheque, sinpe;
    private JPanel PagosRecibidos;
    private JPanel Importe;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel totalLabel;
    private double total;
    private boolean cobrado = false;


    public FacturarCobrarDialog(double total) {
        Importe.setPreferredSize(new Dimension(180, 30));
        this.total=total;
        setContentPane(panel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        totalLabel.setText("" + String.format("%.2f", total));

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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
            // Verificar si los campos están vacíos y asignar un valor por defecto de 0.0
            double efectivoPago = efectivo.getText().isEmpty() ? 0.0 : Double.parseDouble(efectivo.getText());
            double tarjetaPago = tarjeta.getText().isEmpty() ? 0.0 : Double.parseDouble(tarjeta.getText());
            double chequePago = cheque.getText().isEmpty() ? 0.0 : Double.parseDouble(cheque.getText());
            double simpePago = sinpe.getText().isEmpty() ? 0.0 : Double.parseDouble(sinpe.getText());

            // Calcular el total pagado
            double totalPagado = efectivoPago + tarjetaPago + chequePago + simpePago;

            // Verificar si el total pagado es suficiente
            if (totalPagado < total) {
                JOptionPane.showMessageDialog(this, "El monto pagado es insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                double cambio = totalPagado - total;
                JOptionPane.showMessageDialog(this, "Pago realizado con éxito. Cambio: " + String.format("%.2f", cambio), "Pago Completado", JOptionPane.INFORMATION_MESSAGE);
                cobrado = true;
                dispose();  // Cerrar el diálogo después del pago

            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean isPagoExitoso() {
        return cobrado;
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}