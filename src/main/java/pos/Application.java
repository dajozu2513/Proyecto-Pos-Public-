package pos;

import pos.logic.Service;
import pos.presentation.facturas.view.View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    //Integrantes de Proyecto
    //Jeimy Espinoza Barquero 208500515
    //David Joel Soto Zuñiga 118810832

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception ex) {};

        window = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();
        window.setContentPane(tabbedPane);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.instance().stop();
            }
        });

        pos.presentation.clientes.Model clientesModel= new pos.presentation.clientes.Model();
        pos.presentation.clientes.View clientesView = new pos.presentation.clientes.View();
        clientesController = new pos.presentation.clientes.Controller(clientesView,clientesModel);
        Icon clientesIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/client.png"));

        tabbedPane.addTab("Clientes  ",clientesIcon,clientesView.getPanel());

        pos.presentation.cajeros.Model cajerosModel= new pos.presentation.cajeros.Model();
        pos.presentation.cajeros.View cajerosView = new pos.presentation.cajeros.View();
        cajerosController = new pos.presentation.cajeros.Controller(cajerosView,cajerosModel);
        Icon cajerosIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/cashier.png"));

        tabbedPane.addTab("Cajeros  ",cajerosIcon,cajerosView.getPanel());

        pos.presentation.productos.Model productosModel= new pos.presentation.productos.Model();
        pos.presentation.productos.View  productosView = new pos.presentation.productos.View();
        productosController = new pos.presentation.productos.Controller(productosView, productosModel);
        Icon productosIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/product.png"));

        tabbedPane.addTab("Productos  ",productosIcon,productosView.getPanel());

        pos.presentation.facturas.Model facturasModel= new pos.presentation.facturas.Model();
        View facturasView = new View(); //preguntarle al profe esto
        facturasController = new pos.presentation.facturas.Controller(facturasView, facturasModel);
        Icon facturasIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/bill.png"));

        tabbedPane.addTab("Facturas  ",facturasIcon,facturasView.getPanel());

        pos.presentation.estadisticas.Model estadisticasModel= new pos.presentation.estadisticas.Model();
        pos.presentation.estadisticas.View  estadisticasView = new pos.presentation.estadisticas.View();
        estadisticasController = new pos.presentation.estadisticas.Controller(estadisticasView, estadisticasModel);
        Icon estadisticasIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/graph.png"));

        tabbedPane.addTab("Estadisticas  ",estadisticasIcon,estadisticasView.getPanel());

        pos.presentation.historico.Model historicoModel= new pos.presentation.historico.Model();
        pos.presentation.historico.View  historicoView = new pos.presentation.historico.View();
        historicoController = new pos.presentation.historico.Controller(historicoView, historicoModel);
        Icon historicoIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/history.png"));

        tabbedPane.addTab("Historico  ",historicoIcon,historicoView.getPanel());

        window.setSize(900,450);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Application.class.getResource("presentation/icons/icon.png"))).getImage());
        window.setTitle("POS: Point Of Sale");
        window.setVisible(true);

    }


    public static pos.presentation.clientes.Controller clientesController;
    public static pos.presentation.cajeros.Controller cajerosController;
    public static pos.presentation.productos.Controller productosController;
    public static pos.presentation.facturas.Controller facturasController;
    public static pos.presentation.estadisticas.Controller estadisticasController;
    public static pos.presentation.historico.Controller historicoController;

    public static JFrame window;

    public final static int MODE_CREATE=1;
    public final static int MODE_EDIT=2;

    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);
}