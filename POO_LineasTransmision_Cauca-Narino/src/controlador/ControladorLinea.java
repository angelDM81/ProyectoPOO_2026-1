package controlador;

import java.util.ArrayList;
import modelo.LineaTransmision;
import modelo.SistemaElectrico;

public class ControladorLinea {
    
    private SistemaElectrico sistemaElectrico = new SistemaElectrico();
    
    public ArrayList<LineaTransmision> obtenerLineaTransmision(){
        return sistemaElectrico.getLineasActivas();
    }
    
    public int obtenerNumeroDeLineas(){
        return sistemaElectrico.getLineasActivas().size();
    }
    
    public double calcularCapacidadTotal(){
        
        double capacidadTotal=0;
        
        for(LineaTransmision linea: sistemaElectrico.getLineasActivas()){
            capacidadTotal+=linea.getCapacidad().calcularCapacidadMW();
        }
        
        return capacidadTotal;
    }
    
    public double calcularLongitudTotal(){
        
        double longitudTotal=0;
        
        for(LineaTransmision linea: sistemaElectrico.getLineasActivas()){
            longitudTotal+=linea.getCapacidad().getLongitudTotal();
        }
        
        return longitudTotal;
    }
}
