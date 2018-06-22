package cl.metlife.visorpagos.model;

import cl.metlife.hdp.managers.BotonBoletaManager;
import cl.metlife.hdp.domain.BotonBoleta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Blueprints on 2/18/2016.
 */
public class BotonBoletaLazyDataModel extends LazyDataModel<BotonBoleta> {

    private BotonBoletaManager botonBoletaManager;
    private List<Long> userBusinessLine;
    private String businessLine;
    private String rut;
    private String producto;
    private Date fecha;
    private String estado;
    private boolean filAS400;
    private boolean checkAS400;

    public BotonBoletaLazyDataModel(BotonBoletaManager botonBoletaManager, List<Long> userBusinessLine, String businessLine, String rut, String producto, Date fecha, String estado, boolean filAS400, boolean checkAS400) {
        this.botonBoletaManager = botonBoletaManager;
        this.userBusinessLine = userBusinessLine;
        this.businessLine = businessLine;
        this.rut = rut;
        this.producto = producto;
        this.fecha = fecha;
        this.estado = estado;
        this.filAS400 = filAS400;
        this.checkAS400 = checkAS400;
    }

    @Override
    public List<BotonBoleta> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        System.out.println("Current filters: " );
        for (String s : filters.keySet()) {
            System.out.println(s);
        }

        if(businessLine != null && !businessLine.equals("")){
            filters.put("botonPagoBean.botonLineasDeNegocio.nombre", businessLine);
        } else {
            filters.put("botonPagoBean.botonLineasDeNegocio.nombre IN", userBusinessLine);
        }
        if(rut != null && !rut.equals(""))
            filters.put("botonPagoBean.clienteBean.rutCliente", rut);
        if(producto != null && !producto.equals(""))
            filters.put("producto", producto);
        if(fecha != null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            filters.put("botonPagoBean.horaEstado", simpleDateFormat.format(fecha));
        }
        if(estado != null && !estado.equals(""))
            filters.put("botonPagoBean.botonEstado.nombre", estado);
        if(filAS400)
            filters.put("checkAS400", checkAS400);

        super.setRowCount(botonBoletaManager.countFiltered(filters).intValue());
        return botonBoletaManager.findFiltered(first, pageSize, sortField, SortOrder.ASCENDING.equals(sortOrder), filters);
    }

    @Override
    public BotonBoleta getRowData(String rowKey) {
        return botonBoletaManager.getById(Long.parseLong(rowKey));
    }

    @Override
    public Object getRowKey(BotonBoleta object) {
        return object.getId();
    }
}

