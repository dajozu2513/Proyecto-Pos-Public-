package pos.presentation.historico;

import pos.Application;
import pos.logic.Factura;
import pos.logic.Service;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new Factura()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }



}