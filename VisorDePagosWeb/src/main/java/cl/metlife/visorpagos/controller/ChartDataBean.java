package cl.metlife.visorpagos.controller;

import org.chartistjsf.model.chart.AspectRatio;
import org.chartistjsf.model.chart.Axis;
import org.chartistjsf.model.chart.AxisType;
import org.chartistjsf.model.chart.BarChartModel;
import org.chartistjsf.model.chart.BarChartSeries;
import org.chartistjsf.model.chart.ChartSeries;
import org.primefaces.event.ItemSelectEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.Random;

@ManagedBean
public class ChartDataBean {

	private BarChartModel barChartModel;

	public ChartDataBean() {
		createBarModel();
	}
	
	public void createBarModel() {
		Random random = new Random();
		barChartModel = new BarChartModel();
		barChartModel.setAspectRatio(AspectRatio.GOLDEN_SECTION);

		barChartModel.addLabel("Monday");
		barChartModel.addLabel("Tuesday");
		barChartModel.addLabel("Wednesday");
		barChartModel.addLabel("Thursday");
		barChartModel.addLabel("Friday");
		barChartModel.addLabel("Saturday");
		barChartModel.addLabel("Sunday");

		BarChartSeries series1 = new BarChartSeries();
		series1.setName("Series 1");

		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));

		BarChartSeries series2 = new BarChartSeries();
		series2.setName("Series 2");

		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));

		BarChartSeries series3 = new BarChartSeries();
		series3.setName("Series 3");

		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));

		Axis xAxis = barChartModel.getAxis(AxisType.X);
		xAxis.setShowGrid(false);

		barChartModel.addSeries(series1);
		barChartModel.addSeries(series2);
		barChartModel.addSeries(series3);
		
		barChartModel.setShowTooltip(true);
		barChartModel.setSeriesBarDistance(15);
		barChartModel.setAnimateAdvanced(true);
	}

	/**
	 * @return the barChartModel
	 */
	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	/**
	 * @param barChartModel
	 *            the barChartModel to set
	 */
	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
	}

	public void barItemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
				+ ((ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getData().get(
						event.getItemIndex()) + ", Series name:"
				+ ((ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getName());

		FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
	}
}