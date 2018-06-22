package cl.metlife.visorpagos.controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import cl.blueprintsit.framework.auth.AuthenticationBean;
import cl.metlife.hdp.domain.BotonBoleta;
import cl.metlife.hdp.domain.BotonLineasDeNegocio;
import cl.metlife.hdp.managers.BotonBoletaManager;
import cl.metlife.hdp.managers.BotonLineasDeNegocioManager;
import cl.metlife.hdp.managers.BotonPagoManager;
import cl.metlife.hdp.managers.LineasDeNegocioManager;
import org.chartistjsf.model.chart.*;
import org.chartistjsf.model.chart.Axis;
import org.chartistjsf.model.chart.AxisType;
import org.chartistjsf.model.chart.BarChartModel;
import org.chartistjsf.model.chart.BarChartSeries;
import org.chartistjsf.model.chart.PieChartModel;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.*;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean
@ViewScoped
public class ChartsBean extends BaseBean {

    /* Form values */
    private String graphType;
    private String businessLine;
    private Date dateFrom;
    private Date dateTo;
    private boolean pagados;
    private boolean rechazados;
    private String ano;

    /* Render booleans */
    private boolean showForm1;
    private boolean showForm2;
    private boolean showGraph1;
    private boolean showGraph2;

    /* FusionCharts Data */
    private String pieFusionChartData;
    private String barFusionChartData;
    private String barCategories;
    private String barSeries;

    /* ChartisJSF */
    private org.chartistjsf.model.chart.BarChartModel barChartModel;
    private org.primefaces.model.chart.PieChartModel pieChartModel;

    /* Highfaces Pie*/
    private List<PieData> pieList;

    @EJB
    private BotonBoletaManager botonBoletaManager;

    @EJB
    private BotonLineasDeNegocioManager botonLineasDeNegocioManager;

    @EJB
    private BotonPagoManager botonPagoManager;

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    @PostConstruct
    public void init() {
        showGraph1 = false;
        showGraph2 = false;
    }

    public void subjectSelectionChanged(final AjaxBehaviorEvent event) {
        if ("1".equals(graphType)) {
            this.pagados = true;
            this.rechazados = true;

            this.dateFrom = null;
            this.dateTo = null;
            this.businessLine = null;

            this.showForm1 = true;
            this.showForm2 = false;

            this.showGraph1 = false;
            this.showGraph2 = false;
        }
        if ("2".equals(graphType)) {
            this.showForm2 = true;
            this.showForm1 = false;

            this.showGraph1 = false;
            this.showGraph2 = false;
        }
    }

    public void getPieModel() {
        // Highfaces pie
        this.pieList = new ArrayList<PieData>();

        List<Object> pagados = new ArrayList<Object>();
        List<Object> rechazados = new ArrayList<Object>();

        BotonLineasDeNegocio botonLineasDeNegocio = null;
        if(this.businessLine != null && !this.businessLine.equals(""))
            botonLineasDeNegocio = botonLineasDeNegocioManager.getByName(businessLine);

        if(this.pagados)
            pagados = botonBoletaManager.countItemsByStatusAndBusinessLine(2, dateFrom, dateTo, botonLineasDeNegocio, authenticationBean.getUserBotonLineasNegocio());

        if(this.rechazados)
            rechazados = botonBoletaManager.countItemsByStatusAndBusinessLine(3, dateFrom, dateTo, botonLineasDeNegocio, authenticationBean.getUserBotonLineasNegocio());

        // deprecated
        pieChartModel = new org.primefaces.model.chart.PieChartModel();

        pieChartModel.setLegendPosition("w");

        List<BotonLineasDeNegocio> userLines = authenticationBean.getUserBotonLineasNegocio();

        String pieDataFusionChart = "";
        int i = 0;
        for (Object o : pagados) {
            for (BotonLineasDeNegocio userLine : userLines){
                Object[] row = (Object[]) pagados.get(i);

                if(row[1].equals(userLine.getNombre())){
                    pieDataFusionChart += "{label: '" + row[1] + " OK', value: " + row[2] + "},";

                    //pieChartModel.addLabel(row[1] + " OK");
                    pieChartModel.set(row[1] + " OK", (Number) row[2]);

                    PieData pieData = new PieData();
                    pieData.setBusinessLine(row[1] + " OK");
                    pieData.setQuantity((Number) row[2]);

                    this.pieList.add(pieData);
                }
            }

            i++;
        }

        int i2 = 0;
        for (Object o : rechazados) {
            for (BotonLineasDeNegocio userLine : userLines){
                Object[] row = (Object[]) rechazados.get(i2);

                if(row[1].equals(userLine.getNombre())){
                    pieDataFusionChart += "{label: '" + row[1] + " NOK', value: " + row[2] + "},";

                    //pieChartModel.addLabel(row[1] + " NOK");
                    pieChartModel.set(row[1] + " NOK", (Number) row[2]);

                    PieData pieData = new PieData();
                    pieData.setBusinessLine(row[1] + " NOK");
                    pieData.setQuantity((Number) row[2]);

                    this.pieList.add(pieData);
                }
            }

            i2++;
        }

        //this.pieFusionChartData = pieDataFusionChart.substring(0, pieDataFusionChart.length() - 1);

        this.showGraph1 = true;
        this.showGraph2 = false;

        if(i == 0 && i2 == 0){
            this.showGraph1 = false;
            this.showGraph2 = false;
        }
    }

    public void updatePie(){
        getPieModel();
    }

    public void updateBar(){
        getBarModel();
    }

    public String createPieFusionChart(){
        FusionCharts area2dChart = new FusionCharts(
                "pie3d",// chartType
                "piaChart",// chartId
                "600","300",// chartWidth, chartHeight
                "pieChart",// chartContainer
                "json",// dataFormat
                "{\n" +
                        "\"chart\": {\n" +
                        "\"caption\": \"Cantidad de items por Línea de Negocio\",\n" +
                        "\"paletteColors\": \"#0075c2,#1aaf5d,#f2c500,#f45b00,#8e0000\",\n" +
                        "\"bgColor\": \"#ffffff\",\n" +
                        "\"showBorder\": \"0\",\n" +
                        "\"use3DLighting\": \"0\",\n" +
                        "\"showShadow\": \"0\",\n" +
                        "\"enableSmartLabels\": \"0\",\n" +
                        "\"startingAngle\": \"0\",\n" +
                        "\"showPercentValues\": \"1\",\n" +
                        "\"showPercentInTooltip\": \"0\",\n" +
                        "\"decimals\": \"1\",\n" +
                        "\"captionFontSize\": \"14\",\n" +
                        "\"subcaptionFontSize\": \"14\",\n" +
                        "\"subcaptionFontBold\": \"0\",\n" +
                        "\"toolTipColor\": \"#ffffff\",\n" +
                        "\"toolTipBorderThickness\": \"0\",\n" +
                        "\"toolTipBgColor\": \"#000000\",\n" +
                        "\"toolTipBgAlpha\": \"80\",\n" +
                        "\"toolTipBorderRadius\": \"2\",\n" +
                        "\"toolTipPadding\": \"5\",\n" +
                        "\"showHoverEffect\":\"1\",\n" +
                        "\"showLegend\": \"1\",\n" +
                        "\"legendBgColor\": \"#ffffff\",\n" +
                        "\"legendBorderAlpha\": '0',\n" +
                        "\"legendShadow\": '0',\n" +
                        "\"legendItemFontSize\": '10',\n" +
                        "\"legendItemFontColor\": '#666666'\n" +
                        "},\n" +
                        "\"data\": [\n" + this.pieFusionChartData + "]\n" +
                        "}"
        );

        String data = area2dChart.render();
        return data;
    }

    public String createBarFusionChart(){
        FusionCharts area2dChart = new FusionCharts(
                "mscolumn2d",// chartType
                "barChar",// chartId
                "500","300",// chartWidth, chartHeight
                "barChart",// chartContainer
                "json",// dataFormat
                "{\n" +
                "            \"chart\": {\n" +
                "                \"caption\": \"Montos vendidos por mes\",\n" +
                "                \"subCaption\": \"Harry's SuperMart\",\n" +
                "                \"xAxisname\": \"Meses\",\n" +
                "                \"yAxisName\": \"Montos (CLP)\",\n" +
                "                \"theme\": \"fint\"\n" +
                "            },\n" +
                "            \"categories\": [{\n" +
                "                \"category\": [{label:'Enero'}, {label:'Febrero'}, {label:'Marzo'}, {label:'Abril'}, {label:'Mayo'}, {label:'Junio'}, {label:'Julio'}, {label:'Agosto'}, {label:'Septiembre'}, {label:'Octubre'}, {label:'Noviembre'}, {label:'Diciembre'}]\n" +
                "            }],\n" +
                "            \"dataset\": [{\n" +
                "                \"seriesname\": \"Previous Year\",\n" +
                "                \n" +
                "                \"initiallyhidden\": \"1\",\n" +
                "                \n" +
                "                \"data\": [{\n" +
                "                    \"value\": \"10000\"\n" +
                "                }, {\n" +
                "                    \"value\": \"11500\"\n" +
                "                }, {\n" +
                "                    \"value\": \"12500\"\n" +
                "                }, {\n" +
                "                    \"value\": \"15000\"\n" +
                "                }]\n" +
                "            }, {\n" +
                "                \"seriesname\": \"Current Year\",\n" +
                "                \"data\": [{\n" +
                "                    \"value\": \"25400\"\n" +
                "                }, {\n" +
                "                    \"value\": \"29800\"\n" +
                "                }, {\n" +
                "                    \"value\": \"21800\"\n" +
                "                }, {\n" +
                "                    \"value\": \"26800\"\n" +
                "                }]\n" +
                "            }]\n" +
                "        }"
        );

        String data = area2dChart.render();
        return data;
    }

    public List<String> getAllAnos(){
        return botonPagoManager.findAllAnos();
    }

    public void getBarModel() {
        if(this.ano == null || this.ano.equals("")) {
            showError("Error", "Debe seleccionar año a consultar");
            return;
        }

        Random random = new Random();
        barChartModel = new BarChartModel();
        barChartModel.setAspectRatio(AspectRatio.GOLDEN_SECTION);

        barChartModel.addLabel("Enero");
        barChartModel.addLabel("Febrero");
        barChartModel.addLabel("Marzo");
        barChartModel.addLabel("Abril");
        barChartModel.addLabel("Mayo");
        barChartModel.addLabel("Junio");
        barChartModel.addLabel("Julio");
        barChartModel.addLabel("Agosto");
        barChartModel.addLabel("Septiembre");
        barChartModel.addLabel("Octubre");
        barChartModel.addLabel("Noviembre");
        barChartModel.addLabel("Diciembre");

        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setShowGrid(false);


        barChartModel.setShowTooltip(true);
        barChartModel.setSeriesBarDistance(15);
        barChartModel.setAnimateAdvanced(true);
        //yAxis.setMin(0);

        int max = 0;
        this.barCategories = "";
        if(this.businessLine.equals("all")){
            for (BotonLineasDeNegocio l : authenticationBean.getUserBotonLineasNegocio()) {
                List<Object> list = botonBoletaManager.countBoletasByAnoAndBusinessLine(ano, l.getId());

                BarChartSeries series = new BarChartSeries();
                series.setName(l.getNombre());

                int i = 0;
                for (Object o : list) {
                    Object[] row = (Object[]) list.get(i);

                    series.set(((BigDecimal)row[1]).intValue());

                    if(((BigDecimal)row[1]).intValue() > max)
                        max = ((BigDecimal)row[1]).intValue();

                    this.barCategories += "{label:'" + getMonthByNumber(row[0]) + "'},";
                    i++;
                }

                barChartModel.addSeries(series);
            }
        } else {
            BotonLineasDeNegocio botonLineasDeNegocio = botonLineasDeNegocioManager.getByName(businessLine);

            List<Object> list = botonBoletaManager.countBoletasByAnoAndBusinessLine(ano, botonLineasDeNegocio.getId());

            BarChartSeries series = new BarChartSeries();
            series.setName(botonLineasDeNegocio.getNombre());

            int i = 0;
            for (Object o : list) {
                Object[] row = (Object[]) list.get(i);

                series.set(((BigDecimal)row[1]).intValue());

                if(((BigDecimal)row[1]).intValue() > max)
                    max = ((BigDecimal)row[1]).intValue();

                i++;
            }

            barChartModel.addSeries(series);
        }

        //yAxis.setMax(max);

        this.showGraph1 = false;
        this.showGraph2 = true;
    }

    private String getMonthByNumber(Object o) {
        String month = o.toString();

        if(month.equals("01")){
            return "Enero";
        } else if(month.equals("02")){
            return "Febrero";
        } else if(month.equals("03")){
            return "Marzo";
        } else if(month.equals("04")){
            return "Abril";
        } else if(month.equals("05")){
            return "Mayo";
        } else if(month.equals("06")){
            return "Junio";
        } else if(month.equals("07")){
            return "Julio";
        } else if(month.equals("08")){
            return "Agosto";
        } else if(month.equals("09")){
            return "Septiembre";
        } else if(month.equals("10")){
            return "Octubre";
        } else if(month.equals("11")){
            return "Noviembre";
        } else if(month.equals("12")){
            return "Diciembre";
        }

        return "ERROR";
    }

    public void addMessage() {
        //String summary = pagados ? "Checked" : "Unchecked";
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public boolean isShowGraph1() {
        return showGraph1;
    }

    public void setShowGraph1(boolean showGraph1) {
        this.showGraph1 = showGraph1;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isPagados() {
        return pagados;
    }

    public void setPagados(boolean pagados) {
        this.pagados = pagados;
    }

    public boolean isRechazados() {
        return rechazados;
    }

    public void setRechazados(boolean rechazados) {
        this.rechazados = rechazados;
    }

    public boolean isShowGraph2() {
        return showGraph2;
    }

    public void setShowGraph2(boolean showGraph2) {
        this.showGraph2 = showGraph2;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public boolean isShowForm1() {
        return showForm1;
    }

    public void setShowForm1(boolean showForm1) {
        this.showForm1 = showForm1;
    }

    public boolean isShowForm2() {
        return showForm2;
    }

    public void setShowForm2(boolean showForm2) {
        this.showForm2 = showForm2;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public String getPieFusionChartData() {
        return pieFusionChartData;
    }

    public void setPieFusionChartData(String pieFusionChartData) {
        this.pieFusionChartData = pieFusionChartData;
    }

    public String getBarFusionChartData() {
        return barFusionChartData;
    }

    public void setBarFusionChartData(String barFusionChartData) {
        this.barFusionChartData = barFusionChartData;
    }

    public String getBarCategories() {
        return barCategories;
    }

    public void setBarCategories(String barCategories) {
        this.barCategories = barCategories;
    }

    public String getBarSeries() {
        return barSeries;
    }

    public void setBarSeries(String barSeries) {
        this.barSeries = barSeries;
    }

    public BarChartModel getBarChartModel() {
        return barChartModel;
    }

    public void setBarChartModel(BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

    public org.primefaces.model.chart.PieChartModel getPieChartModel() {
        return pieChartModel;
    }

    public void setPieChartModel(org.primefaces.model.chart.PieChartModel pieChartModel) {
        this.pieChartModel = pieChartModel;
    }

    public void barItemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
                + ((org.chartistjsf.model.chart.ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getData().get(
                event.getItemIndex()) + ", Series name:"
                + ((org.chartistjsf.model.chart.ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getName());

        FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
    }

    public void pieItemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
                + pieChartModel.getData().get(event.getItemIndex()));

        FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
    }

    public List<PieData> getPieList() {
        return pieList;
    }

    public void setPieList(List<PieData> pieList) {
        this.pieList = pieList;
    }
}