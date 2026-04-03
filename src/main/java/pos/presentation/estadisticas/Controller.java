package pos.presentation.estadisticas;

import pos.Application;
import pos.logic.Factura;
import pos.logic.Service;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new Factura()));
        //model.setListCategorias(Service.instance().getCategorias());
        this.view = view;
        this.model = model;
        //view.setController(this);
        //view.setModel(model);
    }

    public void search(Factura filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Factura());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Factura());
    }
}