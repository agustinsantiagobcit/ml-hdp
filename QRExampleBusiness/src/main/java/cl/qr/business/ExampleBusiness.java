package cl.qr.business;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by BluePrints Developer on 23-08-2017.
 */
@Stateless
public class ExampleBusiness {

    private Object urlToRedirect;

    public Object getNothing(){
        return null;
    }

    public String getUrlToRedirect() {
        return "http://13.56.78.181:9080/BotonDePagoWeb/LandingPago?tid=36&v=2d48bd4cf8c6fe11c90efa0df08a51bc";
    }


}
