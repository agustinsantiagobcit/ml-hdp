package cl.metlife.visorpagos.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
@ManagedBean(name = "fusioncharts")
@RequestScoped
public class bean{
   
    public static String createfusioncharts(){
     FusionCharts area2dChart = new FusionCharts(
                    "area2d",// chartType
                    "myFirstChart",// chartId
                    "600","400",// chartWidth, chartHeight
                    "chart",// chartContainer
                    "json",// dataFormat
                    "{\"chart\": {\"caption\": \"Sales of Liquor\",\"subCaption\": \"Last week\", \"xAxisName\": \"Day\",  \"yAxisName\": \"Sales (In USD)\",\"numberPrefix\": \"$\",\"paletteColors\": \"#0075c2\",  \"bgColor\": \"#ffffff\",\"showBorder\": \"0\",\"showCanvasBorder\": \"0\",\"plotBorderAlpha\": \"10\",\"usePlotGradientColor\": \"0\",\"plotFillAlpha\": \"50\",\"showXAxisLine\": \"1\",\"axisLineAlpha\": \"25\",\"divLineAlpha\": \"10\", \"showValues\": \"1\",\"showAlternateHGridColor\": \"0\",    \"captionFontSize\": \" 14\",    \"subcaptionFontSize\": \"14\",    \"subcaptionFontBold\": \"0\",    \"toolTipColor\": \"#ffffff\",    \"toolTipBorderThickness\": \"0\",    \"toolTipBgColor\": \"#000000\",    \" toolTipBgAlpha\": \"80\",    \"toolTipBorderRadius\": \"2\",    \"toolTipPadding\": \"5\" },\"data\":[{\"label\":\"Mon\",\"value\":\"5123\"},{\"label\": \"Tue\",\"value\": \"4633\"}, {\"label\": \"Wed\",\"value\": \"5507\" }, {\"label\": \"Thu\",\"value\": \"4910\"}, {\"label\": \"Fri\",\"value\": \"5529\"}, {\"label\": \"Sat\",\"value\": \"5803\"}, {\"label\": \"Sun\",\"value\": \"6202\" }]}"
                );
     
     String data = area2dChart.render();
       return data;      
    }
}