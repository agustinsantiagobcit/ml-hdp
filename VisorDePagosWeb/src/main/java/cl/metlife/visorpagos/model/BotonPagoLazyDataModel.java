package cl.metlife.visorpagos.model;

import cl.metlife.hdp.managers.BotonPagoManager;
import cl.metlife.hdp.domain.BotonPago;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by Blueprints on 2/18/2016.
 */
public class BotonPagoLazyDataModel extends LazyDataModel<BotonPago> {

    private BotonPagoManager botonPagoDAO;

    public BotonPagoLazyDataModel(BotonPagoManager botonPagoDAO) {
        this.botonPagoDAO = botonPagoDAO;
    }

    @Override
    public List<BotonPago> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        super.setRowCount(botonPagoDAO.countFiltered(filters).intValue());
        return botonPagoDAO.findFiltered(first, pageSize, sortField, SortOrder.ASCENDING.equals(sortOrder), filters);
    }

    @Override
    public BotonPago getRowData(String rowKey) {
        return botonPagoDAO.getById(Long.parseLong(rowKey));
    }

    @Override
    public Object getRowKey(BotonPago object) {
        return object.getId();
    }
}

